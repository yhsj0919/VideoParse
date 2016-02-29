package xyz.yhsj.videoparse.extractors.youku.entity;

/**
 * 错误实体
 * Created by LOVE on 2016/2/14.
 */
public class EEntity {

    /**
     * desc :
     * provider : play
     * code : 0
     */

    private String desc;
    private String provider;
    private int code;

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public String getProvider() {
        return provider;
    }

    public int getCode() {
        return code;
    }
}
