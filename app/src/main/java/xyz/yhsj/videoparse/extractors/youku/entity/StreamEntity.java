package xyz.yhsj.videoparse.extractors.youku.entity;

import java.util.List;

/**
 * 清晰度
 * Created by LOVE on 2016/2/15.
 */
public class StreamEntity {
    private String logo;
    private String audio_lang;
    private int height;
    private String subtitle_lang;
    private String stream_type;
    private int width;
    private int milliseconds_video;
    private String drm_type;
    private String transfer_mode;
    private int milliseconds_audio;
    private String stream_fileid;
    private int size;

    /**
     * 片段
     */
    private List<SegsEntity> segs;

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setAudio_lang(String audio_lang) {
        this.audio_lang = audio_lang;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSubtitle_lang(String subtitle_lang) {
        this.subtitle_lang = subtitle_lang;
    }

    public void setStream_type(String stream_type) {
        this.stream_type = stream_type;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setMilliseconds_video(int milliseconds_video) {
        this.milliseconds_video = milliseconds_video;
    }

    public void setDrm_type(String drm_type) {
        this.drm_type = drm_type;
    }

    public void setTransfer_mode(String transfer_mode) {
        this.transfer_mode = transfer_mode;
    }

    public void setMilliseconds_audio(int milliseconds_audio) {
        this.milliseconds_audio = milliseconds_audio;
    }

    public void setStream_fileid(String stream_fileid) {
        this.stream_fileid = stream_fileid;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setSegs(List<SegsEntity> segs) {
        this.segs = segs;
    }

    public String getLogo() {
        return logo;
    }

    public String getAudio_lang() {
        return audio_lang;
    }

    public int getHeight() {
        return height;
    }

    public String getSubtitle_lang() {
        return subtitle_lang;
    }

    public String getStream_type() {
        return stream_type;
    }

    public int getWidth() {
        return width;
    }

    public int getMilliseconds_video() {
        return milliseconds_video;
    }

    public String getDrm_type() {
        return drm_type;
    }

    public String getTransfer_mode() {
        return transfer_mode;
    }

    public int getMilliseconds_audio() {
        return milliseconds_audio;
    }

    public String getStream_fileid() {
        return stream_fileid;
    }

    public int getSize() {
        return size;
    }

    public List<SegsEntity> getSegs() {
        return segs;
    }

}
