package xyz.yhsj.videoparse.extractors.youku.entity;

/**
 * 头像
 * Created by LOVE on 2016/2/15.
 */
public class AvatarEntity {
    private String big;
    private String small;
    private String middle;
    private String large;

    public void setBig(String big) {
        this.big = big;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getBig() {
        return big;
    }

    public String getSmall() {
        return small;
    }

    public String getMiddle() {
        return middle;
    }

    public String getLarge() {
        return large;
    }
}
