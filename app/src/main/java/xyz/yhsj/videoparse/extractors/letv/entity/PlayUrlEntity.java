package xyz.yhsj.videoparse.extractors.letv.entity;

import java.util.ArrayList;

/**
 * 播放地址
 * Created by LOVE on 2016/3/1.
 */
public class PlayUrlEntity {
    private int videoType;
    private String cid;
    private String pid;
    private String vid;
    private String title;
    private String mid;
    private String duration;
    private String pic;
    private int filter;
    private String total;
    private ArrayList<String> domain;
    private DispatchEntity dispatch;

    public int getVideoType() {
        return videoType;
    }

    public void setVideoType(int videoType) {
        this.videoType = videoType;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getFilter() {
        return filter;
    }

    public void setFilter(int filter) {
        this.filter = filter;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<String> getDomain() {
        return domain;
    }

    public void setDomain(ArrayList<String> domain) {
        this.domain = domain;
    }

    public DispatchEntity getDispatch() {
        return dispatch;
    }

    public void setDispatch(DispatchEntity dispatch) {
        this.dispatch = dispatch;
    }
}
