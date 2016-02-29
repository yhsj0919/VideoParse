package xyz.yhsj.videoparse.extractors.youku.entity;

import java.util.List;

/**
 * 展示?
 * Created by LOVE on 2016/2/15.
 */
public class ShowEntity {
    private String id;
    private String title;
    private int video_pay;
    private int video_type;
    private boolean exclusive;
    private String encodeid;
    private int pay;
    private String copyright;
    private String stage;
    private List<?> pay_type;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVideo_pay(int video_pay) {
        this.video_pay = video_pay;
    }

    public void setVideo_type(int video_type) {
        this.video_type = video_type;
    }

    public void setExclusive(boolean exclusive) {
        this.exclusive = exclusive;
    }

    public void setEncodeid(String encodeid) {
        this.encodeid = encodeid;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public void setPay_type(List<?> pay_type) {
        this.pay_type = pay_type;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getVideo_pay() {
        return video_pay;
    }

    public int getVideo_type() {
        return video_type;
    }

    public boolean isExclusive() {
        return exclusive;
    }

    public String getEncodeid() {
        return encodeid;
    }

    public int getPay() {
        return pay;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getStage() {
        return stage;
    }

    public List<?> getPay_type() {
        return pay_type;
    }
}
