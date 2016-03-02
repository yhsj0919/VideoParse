package xyz.yhsj.videoparse.extractors.letv.entity;

import org.xutils.http.annotation.HttpResponse;

import java.util.ArrayList;

import xyz.yhsj.videoparse.utils.JsonResponseParser;

/**
 * 乐视实体
 * Created by LOVE on 2016/3/1.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class LetvEntity {
    private String danmu;
    private String firstlook;
    private int isAlbumPay;
    private String isTryLook;
    private ArrayList<String> paylist;
    private PlayStatusEntity playstatus;
    private PlayUrlEntity playurl;
    private PointEntity point;
    private int statuscode;
    private String trylook;
    private WatermarkEntity watermark;

    public String getDanmu() {
        return danmu;
    }

    public void setDanmu(String danmu) {
        this.danmu = danmu;
    }

    public String getFirstlook() {
        return firstlook;
    }

    public void setFirstlook(String firstlook) {
        this.firstlook = firstlook;
    }

    public int getIsAlbumPay() {
        return isAlbumPay;
    }

    public void setIsAlbumPay(int isAlbumPay) {
        this.isAlbumPay = isAlbumPay;
    }

    public String getIsTryLook() {
        return isTryLook;
    }

    public void setIsTryLook(String isTryLook) {
        this.isTryLook = isTryLook;
    }

    public ArrayList<String> getPaylist() {
        return paylist;
    }

    public void setPaylist(ArrayList<String> paylist) {
        this.paylist = paylist;
    }

    public PlayStatusEntity getPlaystatus() {
        return playstatus;
    }

    public void setPlaystatus(PlayStatusEntity playstatus) {
        this.playstatus = playstatus;
    }

    public PlayUrlEntity getPlayurl() {
        return playurl;
    }

    public void setPlayurl(PlayUrlEntity playurl) {
        this.playurl = playurl;
    }

    public PointEntity getPoint() {
        return point;
    }

    public void setPoint(PointEntity point) {
        this.point = point;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public String getTrylook() {
        return trylook;
    }

    public void setTrylook(String trylook) {
        this.trylook = trylook;
    }

    public WatermarkEntity getWatermark() {
        return watermark;
    }

    public void setWatermark(WatermarkEntity watermark) {
        this.watermark = watermark;
    }
}
