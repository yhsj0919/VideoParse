package xyz.yhsj.videoparse.extractors.youku.entity;

/**
 * 上传者
 * Created by LOVE on 2016/2/15.
 */
public class UploaderEntity {
    private boolean certification;
    private int fan_count;
    private String reason;
    private int show_brand;
    private AvatarEntity avatar;
    private String homepage;

    public void setCertification(boolean certification) {
        this.certification = certification;
    }

    public void setFan_count(int fan_count) {
        this.fan_count = fan_count;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setShow_brand(int show_brand) {
        this.show_brand = show_brand;
    }

    public void setAvatar(AvatarEntity avatar) {
        this.avatar = avatar;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public boolean isCertification() {
        return certification;
    }

    public int getFan_count() {
        return fan_count;
    }

    public String getReason() {
        return reason;
    }

    public int getShow_brand() {
        return show_brand;
    }

    public AvatarEntity getAvatar() {
        return avatar;
    }

    public String getHomepage() {
        return homepage;
    }

}
