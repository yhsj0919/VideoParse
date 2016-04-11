package xyz.yhsj.videoparse.extractors.letv;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import xyz.yhsj.videoparse.utils.DateUtils;

/**
 * 乐视解析
 * Created by LOVE on 2016/2/29.
 */
public class Letv {

    /**
     * 1、 根据url获取视频id
     *
     * @param url 视频网页地址
     * @return
     */
    public static String getVideoId(String url) {
        //Pattern p = Pattern.compile("(?<=id_)(\\w+)");
        Pattern p = Pattern.compile("http://www\\.letv\\.com/ptv/vplay/(\\d+)\\.html");
        Pattern p2 = Pattern.compile("vid=\"(\\d+)\"");

        Matcher matcher = p.matcher(url);
        Matcher matcher2 = p2.matcher(url);

        if (matcher.find()) {
            return matcher.group(1);
        } else if (matcher2.find()) {
            return matcher2.group(1);
        } else {
            return null;
        }
    }


    /**
     * 获取时间密钥
     *
     * @param stime 当前时间的毫秒值
     * @return
     */
    public static long getTkey(long stime) {
        //将毫秒时间戳转化为unix时间戳
        if (stime > 1000000000000L) {
            stime = (stime / 1000) ;
            System.out.println(stime + "");
        }

        long key = 773625421;

        long value = GenerateKeyRor(stime, key % 13);

        value = value ^ key;

        value = GenerateKeyRor(value, key % 17);

        return value;
    }

    /**
     * 加密算法
     *
     * @param value 加密数据
     * @param key   密钥
     * @return
     */
    private static long GenerateKeyRor(long value, long key) {
        int temp = (int) (Math.pow(2, 32) - 1);
        return ((value & temp) >> key % 32) | (value << (32 - (key % 32)) & temp);
    }


    public static String getApiUrl(String videoId) {
        return getApiUrl(videoId, DateUtils.getCurrentTime());
    }

    public static String getApiUrl(String videoId, long time) {
        StringBuffer infoUrl = new StringBuffer();

        infoUrl
                .append("http://api.letv.com/mms/out/video/playJson?id=")
                .append(videoId)
                .append("&platid=1&splatid=101&format=1&tkey=")
                .append(getTkey(DateUtils.getCurrentTime()))
                .append("&domain=www.letv.com")
        ;
        return infoUrl.toString();
    }
}
