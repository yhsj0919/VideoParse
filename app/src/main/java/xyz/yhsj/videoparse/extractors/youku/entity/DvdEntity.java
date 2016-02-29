package xyz.yhsj.videoparse.extractors.youku.entity;

import java.util.List;

/**
 * 视频预览简介
 * Created by LOVE on 2016/2/15.
 */
public class DvdEntity {
    private String notsharing;
    private List<PointEntity> point;

    public void setNotsharing(String notsharing) {
        this.notsharing = notsharing;
    }

    public void setPoint(List<PointEntity> point) {
        this.point = point;
    }

    public String getNotsharing() {
        return notsharing;
    }

    public List<PointEntity> getPoint() {
        return point;
    }

}
