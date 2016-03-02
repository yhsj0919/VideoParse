package xyz.yhsj.videoparse.extractors.letv.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 播放地址（清晰度）
 * Created by LOVE on 2016/3/1.
 */
public class DispatchEntity {

    @SerializedName("1000")
    private List<String> r1000;
    @SerializedName("1080p")
    private List<String> r1080p;
    @SerializedName("1300")
    private List<String> r1300;
    @SerializedName("350")
    private List<String> r350;
    @SerializedName("720p")
    private List<String> r720p;

    public List<String> getR1000() {
        return r1000;
    }

    public void setR1000(List<String> r1000) {
        this.r1000 = r1000;
    }

    public List<String> getR1080p() {
        return r1080p;
    }

    public void setR1080p(List<String> r1080p) {
        this.r1080p = r1080p;
    }

    public List<String> getR1300() {
        return r1300;
    }

    public void setR1300(List<String> r1300) {
        this.r1300 = r1300;
    }

    public List<String> getR350() {
        return r350;
    }

    public void setR350(List<String> r350) {
        this.r350 = r350;
    }

    public List<String> getR720p() {
        return r720p;
    }

    public void setR720p(List<String> r720p) {
        this.r720p = r720p;
    }
}
