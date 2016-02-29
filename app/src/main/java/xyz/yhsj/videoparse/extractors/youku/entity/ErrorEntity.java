package xyz.yhsj.videoparse.extractors.youku.entity;

/**
 * Created by LOVE on 2016/2/23.
 */
public class ErrorEntity {

    /**
     * code : -104
     * note : 视频不存在
     */

    private int code;
    private String note;

    public void setCode(int code) {
        this.code = code;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getCode() {
        return code;
    }

    public String getNote() {
        return note;
    }
}
