package xyz.yhsj.videoparse.extractors.youku.entity;

/**
 * 视频清晰度片段
 * Created by LOVE on 2016/2/15.
 */
public class SegsEntity {
    /**
     * total_milliseconds_audio : 5359049
     * total_milliseconds_video : 3000000
     * key : c670baffb82c0b4a261ed89a
     * size : 198924690
     */
    private String total_milliseconds_audio;
    private String total_milliseconds_video;
    private String key;
    private String size;
    private String downlad_url;

    public void setTotal_milliseconds_audio(String total_milliseconds_audio) {
        this.total_milliseconds_audio = total_milliseconds_audio;
    }

    public void setTotal_milliseconds_video(String total_milliseconds_video) {
        this.total_milliseconds_video = total_milliseconds_video;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTotal_milliseconds_audio() {
        return total_milliseconds_audio;
    }

    public String getTotal_milliseconds_video() {
        return total_milliseconds_video;
    }

    public String getKey() {
        return key;
    }

    public String getSize() {
        return size;
    }

    public String getDownlad_url() {
        return downlad_url;
    }

    public void setDownlad_url(String downlad_url) {
        this.downlad_url = downlad_url;
    }
}
