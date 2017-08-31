package xyz.yhsj.parse.extractors

import khttp.get
import xyz.yhsj.parse.jsonObject
import xyz.yhsj.parse.match1
import java.util.*
import kotlin.concurrent.thread

/**百度音乐
 * Created by user on 2017/8/30 030.
 */
object BaiduMusic {

    /**
     * 获取id
     */
    fun getId(url: String) = "http://music.baidu.com/song/([\\d]+)".match1(url)

    /**
     * 获取歌曲地址
     */
    fun parse(vid: String?) {
        val info = get("http://music.baidu.com/data/music/songlink", params = mapOf("songIds" to "$vid"))
                .text
                .jsonObject
                .getJSONObject("data")

        if (!info.getString("xcode").isNullOrBlank()) {
            val songList = info.getJSONArray("songList").getJSONObject(0).getString("songLink")
            println(songList)
        } else {
            println("地址获取失败了")
        }
    }

    /**
     * 获取专辑
     */
    fun parseList(url: String) {
        val album_id = "http://music.baidu.com/album/([\\d]+)".match1(url)

        val info = get("http://play.baidu.com/data/music/box/album?albumId=$album_id&type=album&_=${Date().time}")
                .text
                .jsonObject
                .getJSONObject("data")

        val songIdList = info.getJSONArray("songIdList")

        for (i in 0 until songIdList.length()) {
            thread { parse(songIdList.getString(i)) }
        }
        println(info)

    }

}