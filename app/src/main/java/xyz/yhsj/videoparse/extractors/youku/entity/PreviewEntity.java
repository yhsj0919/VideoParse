package xyz.yhsj.videoparse.extractors.youku.entity;

import java.util.List;

/**
 * 预览图
 * Created by LOVE on 2016/2/15.
 */
public class PreviewEntity {
    private String timespan;
    private List<String> thumb;

    public void setTimespan(String timespan) {
        this.timespan = timespan;
    }

    public void setThumb(List<String> thumb) {
        this.thumb = thumb;
    }

    public String getTimespan() {
        return timespan;
    }

    public List<String> getThumb() {
        return thumb;
    }
}
