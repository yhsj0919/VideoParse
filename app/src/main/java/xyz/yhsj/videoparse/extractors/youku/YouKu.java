package xyz.yhsj.videoparse.extractors.youku;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import xyz.yhsj.videoparse.extractors.youku.entity.EpEntity;
import xyz.yhsj.videoparse.extractors.youku.entity.SecurityEntity;
import xyz.yhsj.videoparse.extractors.youku.entity.StreamEntity;
import xyz.yhsj.videoparse.extractors.youku.entity.StreamTypeEntity;
import xyz.yhsj.videoparse.utils.Base64Utils;

/**
 * 优酷解析工具
 * Created by LOVE on 2016/2/3.
 */
public class YouKu {

    /**
     * 1、 根据url获取视频id
     *
     * @param url 视频网页地址
     * @return
     */
    public static String getVideoId(String url) {
        //Pattern p = Pattern.compile("(?<=id_)(\\w+)");
        Pattern p = Pattern.compile("youku\\.com/v_show/id_([a-zA-Z0-9=]+)");
        Pattern p2 = Pattern.compile("player\\.youku\\.com/player\\.php/sid/([a-zA-Z0-9=]+)/v\\.swf");
        Pattern p3 = Pattern.compile("loader\\.swf\\?VideoIDS=([a-zA-Z0-9=]+)");
        Pattern p4 = Pattern.compile("player\\.youku\\.com/embed/([a-zA-Z0-9=]+)");

        Matcher matcher = p.matcher(url);
        Matcher matcher2 = p2.matcher(url);
        Matcher matcher3 = p3.matcher(url);
        Matcher matcher4 = p4.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        } else if (matcher2.find()) {
            return matcher2.group(1);
        } else if (matcher3.find()) {
            return matcher3.group(1);
        } else if (matcher4.find()) {
            return matcher4.group(1);
        } else {
            return null;
        }
    }

    /**
     * 2、获取视频真实地址1
     * <p/>
     * params.addHeader("Referer", "http://static.youku.com/");
     * params.addHeader("Cookie", "__ysuid=1447684637230HKz");
     *
     * @param videoId 视频id
     */
    public static String getApiUrl(String videoId) {
        return "http://play.youku.com/play/get.json?ct=12&vid=" + videoId;

    }

    /**
     * 3、获取视频真实地址2
     * <p/>
     * 两个请求需要在header加入下列属性
     * params.addHeader("Referer", "http://static.youku.com/");
     * params.addHeader("Cookie", "__ysuid=1447684637230HKz");
     *
     * @param videoId 视频id
     */
    public static String getApiUrl2(String videoId) {
        return "http://play.youku.com/play/get.json?ct=10&vid=" + videoId;

    }


    /**
     * 4、获取包含下载地址的
     *
     * @param streamEntity
     * @param securityEntity
     * @return
     */
    public static StreamEntity parseStreamEntity(StreamEntity streamEntity, SecurityEntity securityEntity) {

        for (int i = 0; i < streamEntity.getSegs().size(); i++) {

            EpEntity epEntity = getEp(i, streamEntity.getStream_fileid(), securityEntity.getEncrypt_string());

            StringBuffer download_Url = new StringBuffer();

            download_Url
                    .append("http://k.youku.com/player/getFlvPath/sid/")
                    .append(epEntity.getSid())
                    .append("_00")
                    .append("/st/")
                    .append(getStreamType(streamEntity.getStream_type()).getVideo_Container())
                    .append("/fileid/")
                    .append(epEntity.getFileid())
                    .append("?ctype=12")
                    .append("&ep=")
                    .append(epEntity.getEp())
                    .append("&ev=1")
                    .append("&oip=")
                    .append(securityEntity.getIp())
                    .append("&token=")
                    .append(epEntity.getToken())
                    .append("&yxon=1")
                    .append("&K=")
                    .append(streamEntity.getSegs().get(i).getKey());


            streamEntity.getSegs().get(i).setDownlad_url(download_Url.toString());

        }
        return streamEntity;
    }

    /**
     * 获取视频的格式与描述
     *
     * @return
     */
    public static StreamTypeEntity getStreamType(String streamType) {
        StreamTypeEntity streamTypeEntity = new StreamTypeEntity();
        switch (streamType) {
            case "mp4hd3":
            case "hd3":
                streamTypeEntity.setVideo_Container("flv");
                streamTypeEntity.setVideo_profile("1080P");
                break;
            case "mp4hd2":
            case "hd2":
                streamTypeEntity.setVideo_Container("flv");
                streamTypeEntity.setVideo_profile("超清");
                break;
            case "mp4hd":
            case "mp4":
                streamTypeEntity.setVideo_Container("mp4");
                streamTypeEntity.setVideo_profile("高清");
                break;
            case "flvhd":
                streamTypeEntity.setVideo_Container("flv");
                streamTypeEntity.setVideo_profile("标清");
                break;
            case "flv":
                streamTypeEntity.setVideo_Container("flv");
                streamTypeEntity.setVideo_profile("标清");
                break;
            case "3gphd":
                streamTypeEntity.setVideo_Container("3gp");
                streamTypeEntity.setVideo_profile("标清(3GP)");
                break;
        }
        return streamTypeEntity;
    }

    /**
     * 加密信息解码
     *
     * @param template   加密模板
     * @param datas      数据
     * @param isToBase64 是否转换Base64
     * @return
     */
    private static String youkuEncoder(String template, byte[] datas, boolean isToBase64) {
        String result = "";
        List<Byte> bytesR = new ArrayList<>();
        int f = 0, h = 0, q = 0;
        int[] b = new int[256];
        for (int i = 0; i < 256; i++)
            b[i] = i;
        while (h < 256) {
            f = (f + b[h] + template.charAt(h % template.length())) % 256;
            int temp = b[h];
            b[h] = b[f];
            b[f] = temp;
            h++;
        }
        f = 0;
        h = 0;
        q = 0;
        while (q < datas.length) {
            h = (h + 1) % 256;
            f = (f + b[h]) % 256;
            int temp = b[h];
            b[h] = b[f];
            b[f] = temp;
            byte[] bytes = new byte[]{(byte) (datas[q] ^ b[(b[h] + b[f]) % 256])};
            bytesR.add(bytes[0]);
            result += new String(bytes);
            q++;
        }
        if (isToBase64) {
            Byte[] bytes = new Byte[bytesR.size()];
            Byte[] byteR = bytesR.toArray(bytes);
            result = Base64Utils.encode(byteR);
        }
        return result;
    }

    /**
     * 获取新的ep
     *
     * @param no
     * @param fileid
     * @param ep
     */
    private static EpEntity getEp(int no, String fileid, String ep) {
        EpEntity epEntity = new EpEntity();
        String number = "";

        String template1 = "becaf9be";
        String template2 = "bf7e5f01";

        number = Integer.toHexString(no);
        if (number.length() == 1) {
            number = "0" + no;
        } else {

        }
        fileid = fileid.substring(0, 8) + number + fileid.substring(10);

        byte[] bytes = Base64Utils.decode(ep);
        ep = new String(bytes);

        String temp = youkuEncoder(template1, bytes, false);

        //LogUtil.e(temp);

        String[] part = temp.split("_");

        String sid = part[0];
        String token = part[1];

        epEntity.setSid(sid);
        epEntity.setToken(token);
        epEntity.setFileid(fileid);

        // LogUtil.w("sid:" + sid);
        //LogUtil.w("token:" + token);
        //LogUtil.w("fileid:" + fileid);

        String whole = sid + "_" + fileid + "_" + token;
        byte[] newbytes = whole.getBytes();
        String epNew = youkuEncoder(template2, newbytes, true);
        try {
            epEntity.setEp(URLEncoder.encode(epNew, "UTF-8"));
            //LogUtil.w("epNew:" + URLEncoder.encode(epNew, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return epEntity;
    }
}
