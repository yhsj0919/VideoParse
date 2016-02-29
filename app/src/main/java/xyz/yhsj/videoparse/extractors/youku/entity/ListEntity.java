package xyz.yhsj.videoparse.extractors.youku.entity;

/**
 * 视频列表详情
 * Created by LOVE on 2016/2/15.
 */
public class ListEntity {
    private int vv;
    private String title;
    private String encodevid;
    private String seq;
    private String vid;

    public void setVv(int vv) {
        this.vv = vv;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEncodevid(String encodevid) {
        this.encodevid = encodevid;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public int getVv() {
        return vv;
    }

    public String getTitle() {
        return title;
    }

    public String getEncodevid() {
        return encodevid;
    }

    public String getSeq() {
        return seq;
    }

    public String getVid() {
        return vid;
    }
}
