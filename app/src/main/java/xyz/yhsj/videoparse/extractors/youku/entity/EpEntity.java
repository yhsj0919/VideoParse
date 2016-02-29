package xyz.yhsj.videoparse.extractors.youku.entity;

/**
 * Created by LOVE on 2016/2/17.
 */
public class EpEntity {
    private String sid;
    private String token;
    private String ep;
    private String fileid;

    public EpEntity() {
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEp() {
        return ep;
    }

    public void setEp(String ep) {
        this.ep = ep;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }
}
