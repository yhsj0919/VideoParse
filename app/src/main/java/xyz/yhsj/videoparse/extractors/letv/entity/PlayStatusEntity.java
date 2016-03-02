package xyz.yhsj.videoparse.extractors.letv.entity;

/**
 * 播放状态
 * Created by LOVE on 2016/3/1.
 */
public class PlayStatusEntity {
    private int status;
    private int stime;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setStime(int stime) {
        this.stime = stime;
    }

    public int getStatus() {
        return status;
    }

    public int getStime() {
        return stime;
    }
}
