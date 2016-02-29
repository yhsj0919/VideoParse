package xyz.yhsj.videoparse.extractors.youku.entity;

import java.util.List;

/**
 * 视频信息
 * Created by LOVE on 2016/2/15.
 */
public class VideoEntity {
    private String logo;
    private String share_type;
    private int userid;
    private String privacy;
    private int category_id;
    private String upload;
    private int restrict;
    private boolean share;
    private String title;
    private String username;
    private int source;
    private String seconds;
    private String encodeid;
    private String category_letter_id;
    private List<String> tags;
    private List<String> type;
    /**
     * id : 2041
     * name : 韩国
     */

    private List<SubcategoriesEntity> subcategories;

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setShare_type(String share_type) {
        this.share_type = share_type;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }

    public void setRestrict(int restrict) {
        this.restrict = restrict;
    }

    public void setShare(boolean share) {
        this.share = share;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public void setSeconds(String seconds) {
        this.seconds = seconds;
    }

    public void setEncodeid(String encodeid) {
        this.encodeid = encodeid;
    }

    public void setCategory_letter_id(String category_letter_id) {
        this.category_letter_id = category_letter_id;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public void setSubcategories(List<SubcategoriesEntity> subcategories) {
        this.subcategories = subcategories;
    }

    public String getLogo() {
        return logo;
    }

    public String getShare_type() {
        return share_type;
    }

    public int getUserid() {
        return userid;
    }

    public String getPrivacy() {
        return privacy;
    }

    public int getCategory_id() {
        return category_id;
    }

    public String getUpload() {
        return upload;
    }

    public int getRestrict() {
        return restrict;
    }

    public boolean isShare() {
        return share;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }

    public int getSource() {
        return source;
    }

    public String getSeconds() {
        return seconds;
    }

    public String getEncodeid() {
        return encodeid;
    }

    public String getCategory_letter_id() {
        return category_letter_id;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<String> getType() {
        return type;
    }

    public List<SubcategoriesEntity> getSubcategories() {
        return subcategories;
    }

}
