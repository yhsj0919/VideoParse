package xyz.yhsj.parse.extractors

import xyz.yshj.parse.match1

/**优酷解析
 * Created by LOVE on 2017/4/13 013.
 */
object YouKu {
    /**
     * 1、 根据url获取视频id
     *
     * @param url 视频网页地址
     * @return
     */
    fun getVideoId(url: String): String {
        val regex1 = "youku\\.com/v_show/id_([a-zA-Z0-9=]+)"
        val regex2 = "player\\.youku\\.com/player\\.php/sid/([a-zA-Z0-9=]+)/v\\.swf"
        val regex3 = "loader\\.swf\\?VideoIDS=([a-zA-Z0-9=]+)"
        val regex4 = "player\\.youku\\.com/embed/([a-zA-Z0-9=]+)"
        return match1(url, regex1) ?: match1(url, regex2) ?: match1(url, regex3) ?: match1(url, regex4) ?: ""
    }
}