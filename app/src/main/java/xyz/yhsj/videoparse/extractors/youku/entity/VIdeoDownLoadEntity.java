package xyz.yhsj.videoparse.extractors.youku.entity;

/**
 * 视频下载链接的实体
 * Created by LOVE on 2016/4/11 0011.
 */
public class VideoDownLoadEntity {

    /**
     * fileid : 0300802800548509235B7105CF07DD7C6B8F06-FC06-865C-9FC4-C1BC9E3C0299.flv
     * server : http://103.41.141.138/youku/6774D280A793A7AA157145766/0300802800548509235B7105CF07DD7C6B8F06-FC06-865C-9FC4-C1BC9E3C0299.flv
     */

    private String fileid;
    private String server;

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
