package xyz.yhsj.videoparse.extractors.youku.entity;

/**
 * 视频实体
 * Created by LOVE on 2016/2/14.
 */

public class YouKuEntity {
    private double cost;
    private DataEntity data;
    private EEntity e;

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public EEntity getE() {
        return e;
    }

    public void setE(EEntity e) {
        this.e = e;
    }
}
