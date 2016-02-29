package xyz.yhsj.videoparse.extractors.youku.entity;

import java.util.List;

/**
 * 数据实体
 * Created by LOVE on 2016/2/14.
 */
public class DataEntity {

    private int id;
    /**
     * 视频精彩简介
     */
    private DvdEntity dvd;
    /**
     * 视频列表？
     */
    private VideosEntity videos;
    /**
     * 上传者
     */
    private UploaderEntity uploader;
    /**
     * 展示?
     */
    private ShowEntity show;
    /**
     * 加密信息
     */
    private SecurityEntity security;
    /**
     * 网络
     */
    private NetworkEntity network;
    /**
     * 预览图
     */
    private PreviewEntity preview;
    /**
     * 控制器
     */
    private ControllerEntity controller;
    /**
     * 用户
     */
    private UserEntity user;
    /**
     * 视频信息
     */
    private VideoEntity video;
    /**
     * 清晰度
     */
    private List<StreamEntity> stream;

    /**
     * 错误信息
     */
    private ErrorEntity error;

    public void setDvd(DvdEntity dvd) {
        this.dvd = dvd;
    }

    public void setVideos(VideosEntity videos) {
        this.videos = videos;
    }

    public void setUploader(UploaderEntity uploader) {
        this.uploader = uploader;
    }

    public void setShow(ShowEntity show) {
        this.show = show;
    }

    public void setSecurity(SecurityEntity security) {
        this.security = security;
    }

    public void setNetwork(NetworkEntity network) {
        this.network = network;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPreview(PreviewEntity preview) {
        this.preview = preview;
    }

    public void setController(ControllerEntity controller) {
        this.controller = controller;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setVideo(VideoEntity video) {
        this.video = video;
    }

    public void setStream(List<StreamEntity> stream) {
        this.stream = stream;
    }

    public DvdEntity getDvd() {
        return dvd;
    }

    public VideosEntity getVideos() {
        return videos;
    }

    public UploaderEntity getUploader() {
        return uploader;
    }

    public ShowEntity getShow() {
        return show;
    }

    public SecurityEntity getSecurity() {
        return security;
    }

    public NetworkEntity getNetwork() {
        return network;
    }

    public int getId() {
        return id;
    }

    public PreviewEntity getPreview() {
        return preview;
    }

    public ControllerEntity getController() {
        return controller;
    }

    public UserEntity getUser() {
        return user;
    }

    public VideoEntity getVideo() {
        return video;
    }

    public List<StreamEntity> getStream() {
        return stream;
    }

    public ErrorEntity getError() {
        return error;
    }

    public void setError(ErrorEntity error) {
        this.error = error;
    }
}
