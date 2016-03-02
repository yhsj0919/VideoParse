package xyz.yhsj.videoparse.extractors.letv.entity;

/**
 * 水印图片实体
 * Created by LOVE on 2016/3/1.
 */
public class ImgEntity {
    private String lasttime;
    private String link;
    private String position;
    private String url100;

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setUrl100(String url100) {
        this.url100 = url100;
    }

    public String getLasttime() {
        return lasttime;
    }

    public String getLink() {
        return link;
    }

    public String getPosition() {
        return position;
    }

    public String getUrl100() {
        return url100;
    }
}
