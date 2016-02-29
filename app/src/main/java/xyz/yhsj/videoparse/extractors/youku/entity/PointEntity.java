package xyz.yhsj.videoparse.extractors.youku.entity;

/**
 * 简介时间点
 * Created by LOVE on 2016/2/15.
 */
public class PointEntity {
    private String title;
    private String desc;
    private String start;
    private String ctype;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getStart() {
        return start;
    }

    public String getCtype() {
        return ctype;
    }
}
