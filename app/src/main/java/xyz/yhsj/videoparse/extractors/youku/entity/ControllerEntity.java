package xyz.yhsj.videoparse.extractors.youku.entity;

/**
 * 控制器
 * Created by LOVE on 2016/2/15.
 */
public class ControllerEntity {
    private boolean html5_disable;
    private boolean continuous;
    private boolean video_capture;
    private int stream_mode;
    private boolean download_disable;
    private boolean share_disable;
    private boolean circle;
    private int play_mode;

    public void setHtml5_disable(boolean html5_disable) {
        this.html5_disable = html5_disable;
    }

    public void setContinuous(boolean continuous) {
        this.continuous = continuous;
    }

    public void setVideo_capture(boolean video_capture) {
        this.video_capture = video_capture;
    }

    public void setStream_mode(int stream_mode) {
        this.stream_mode = stream_mode;
    }

    public void setDownload_disable(boolean download_disable) {
        this.download_disable = download_disable;
    }

    public void setShare_disable(boolean share_disable) {
        this.share_disable = share_disable;
    }

    public void setCircle(boolean circle) {
        this.circle = circle;
    }

    public void setPlay_mode(int play_mode) {
        this.play_mode = play_mode;
    }

    public boolean isHtml5_disable() {
        return html5_disable;
    }

    public boolean isContinuous() {
        return continuous;
    }

    public boolean isVideo_capture() {
        return video_capture;
    }

    public int getStream_mode() {
        return stream_mode;
    }

    public boolean isDownload_disable() {
        return download_disable;
    }

    public boolean isShare_disable() {
        return share_disable;
    }

    public boolean isCircle() {
        return circle;
    }

    public int getPlay_mode() {
        return play_mode;
    }
}
