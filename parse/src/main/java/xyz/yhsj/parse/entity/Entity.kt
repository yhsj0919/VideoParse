package xyz.yhsj.parse.entity

import java.io.Serializable

/**媒体文件实体
 * Created by LOVE on 2017/4/24 024.
 */
data class MediaFile(
        var htmlUrl: String? = null,
        var title: String? = null,
        var type: MediaType = MediaType.VIDEO,
        var url: ArrayList<MediaUrl> = ArrayList(),
        val mediaList: ArrayList<MediaFile> = ArrayList()
) : Serializable

/**
 * 媒体文件的url
 */
data class MediaUrl(
        var title: String,
        var stream_type: String? = null,
        var playUrl: ArrayList<String> = ArrayList(),
        var downUrl: ArrayList<String> = ArrayList()
) : Serializable

/**媒体文件实体
 * Created by LOVE on 2017/4/24 024.
 */
data class ParseResult(
        var code: Int = 200,
        var msg: String = "OK",
        var data: MediaFile? = null
) : Serializable

/**
 * 文件类型
 */
enum class MediaType {
    VIDEO,
    MUSIC,
    VIDEO_LIST,
    MUSIC_LIST
}