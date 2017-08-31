package xyz.yhsj.parse.extractors

import khttp.get
import xyz.yhsj.parse.jsonObject
import xyz.yhsj.parse.match1
import xyz.yhsj.parse.utils.MD5

/**Bilibili解析
 * https://www.bilibili.com/video/av13715520/
 * https://bangumi.bilibili.com/anime/6332/play#113312
 * Created by LOVE on 2017/5/12 012.
 */
object Bilibili {
    val heard = mapOf("Referer" to "http://www.bilibili.com")
    var url: String = ""

    val supported_stream_profile = hashMapOf("超清" to "TD", "高清" to "HD", "流畅" to "SD")
    val appkey = "f3bb208b3d081dc8"
    val SECRETKEY_MINILOADER = "1c15888dc316e05a15fdd0a02ed6584f"

    fun getId(url: String) {
        this.url = url

        var title: String?
        var vid: String?


        if ("#page=" in url) {
            val page_index = "#page=(\\d+)".match1(url)
            val av_id = "\\/(av\\d+)".match1(url)

            this.url = "http://www.bilibili.com/$av_id/index_$page_index.html"
        }

        val html = get(this.url, headers = heard).text

        vid = "cid=(\\d+)".match1(html) ?: "cid=\\\"(\\d+)".match1(html)
        title = "<title>([^<]+)".match1(html)?.replace("_bilibili_哔哩哔哩", "")

        if (vid.isNullOrBlank()) {
            val eid = "anime/v/(\\d+)".match1(this.url) ?: "'play#(\\d+)".match1(this.url) ?: "anime/v/(\\d+)".match1(html)

            val episode_info = get("http://bangumi.bilibili.com/web_api/episode/$eid.json").text.jsonObject.getJSONObject("result").getJSONObject("currentEpisode")
            vid = episode_info.getString("danmaku")
            title = episode_info.getString("indexTitle") + "  .  " + episode_info.getString("longTitle")

        }
        supported_stream_profile.forEach { (key, value) ->

            val sign_this = MD5.getMD5CodeStr("cid=$vid&from=miniplay&player=1&quality=$value$SECRETKEY_MINILOADER")

            val api_url = "http://interface.bilibili.com/playurl?cid=$vid&player=1&quality=$value&from=miniplay&sign=$sign_this"

            val html = get(api_url, headers = heard).text


            println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
            println(html)
            println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        }

        println(title)
        println(vid)
    }


}