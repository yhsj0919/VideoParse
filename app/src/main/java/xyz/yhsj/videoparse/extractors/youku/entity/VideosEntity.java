package xyz.yhsj.videoparse.extractors.youku.entity;

import java.util.List;

/**
 * 视频列表？
 * Created by LOVE on 2016/2/15.
 */
public class VideosEntity {
    /**
     * vv : 0
     * title : 超体
     * encodevid : XODMxNzI4MjQ4
     * seq : 1
     * vid : 207932062
     */

    private List<ListEntity> list;

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public List<ListEntity> getList() {
        return list;
    }

}
