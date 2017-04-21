package xyz.yhsj.parse.extractors

import xyz.yhsj.parse.intfc.Parse
import xyz.yhsj.parse.jsonObject
import xyz.yhsj.parse.match1
import xyz.yhsj.parse.matchAll
import xyz.yhsj.parse.utils.HttpRequest
import java.net.URL

/**QQ视频
 * Created by LOVE on 2017/4/21 021.
 */
object QQ : Parse {
    override fun download(tempurl: String) {

        var url = tempurl

        //全民k歌
        if ("kg.qq.com" in url || "kg2.qq.com" in url) {
            val chars = url.split("?s=")
            val shareid = chars[chars.lastIndex]

            println(shareid)
            return
        }

        if ("live.qq.com" in url) {

            return
        }

        if ("mp.weixin.qq.com/s" in url) {
            val content = HttpRequest.get(url).body()
            val vids = "\\bvid=(\\w+)".matchAll(content)

            for (vid in vids) {

            }
            return
        }

        if ("v.qq.com/page" in url) {
            val content = HttpRequest.get(url).followRedirects(true).body()
            println(content)
            url = "href=\"(.*?)\"".match1(content) ?: ""
        }

        if ("kuaibao.qq.com" in url || "http://daxue.qq.com/content/content/id/\\d+".match1(url) != null) {
            val content = HttpRequest.get(url).body()
            val vid = "vid\\s*=\\s*\"\\s*([^\"]+)\"".match1(content)
            val title = "title\">([^\"]+)</p>".match1(content)

        } else if ("iframe/player.html" in url) {
            val vid = "\\bvid=(\\w+)".match1(url)
            val title = vid
        } else {
            val content = HttpRequest.get(url).body()
            val vid = "\\bvid=(\\w+)".match1(url)
            // val vid2="\\bvid=(\\w+)".match1(content)
            val vid2 = "vid\"*\\s*:\\s*\"\\s*([^\"]+)\"".match1(content)
            qq_download_by_vid(vid ?: vid2 ?: "")
        }
    }

    fun qq_download_by_vid(vid: String) {
        val info_api = "http://vv.video.qq.com/getinfo?otype=json&appver=3%2E2%2E19%2E333&platform=11&defnpayver=1&vid=$vid"
        println(info_api)

        val info = HttpRequest.get(info_api).body().replace("QZOutputJson=", "").jsonObject


        val parts_vid = info.getJSONObject("vl").getJSONArray("vi").getJSONObject(0).getString("vid")
        val parts_ti = info.getJSONObject("vl").getJSONArray("vi").getJSONObject(0).getString("ti")
        var parts_prefix = info.getJSONObject("vl").getJSONArray("vi").getJSONObject(0).getJSONObject("ul").getJSONArray("ui").getJSONObject(0).getString("url")
        val parts_formats = info.getJSONObject("fl").getJSONArray("fi")
        println("$parts_prefix")
        if (parts_prefix.endsWith("/")) {
            parts_prefix = parts_prefix.substring(0, parts_prefix.length - 1)
        }

        println("$parts_prefix")

        println(info)
    }
}