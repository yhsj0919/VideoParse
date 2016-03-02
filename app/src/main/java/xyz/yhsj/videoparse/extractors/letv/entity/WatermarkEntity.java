package xyz.yhsj.videoparse.extractors.letv.entity;

import java.util.List;

/**
 * 水印
 * Created by LOVE on 2016/3/1.
 */
public class WatermarkEntity {
    private String offset;
    private List<ImgEntity> imgs;

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public void setImgs(List<ImgEntity> imgs) {
        this.imgs = imgs;
    }

    public String getOffset() {
        return offset;
    }

    public List<ImgEntity> getImgs() {
        return imgs;
    }

}
