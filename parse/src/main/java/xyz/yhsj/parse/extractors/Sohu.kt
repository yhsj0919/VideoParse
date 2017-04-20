package xyz.yhsj.parse.extractors

import xyz.yhsj.parse.jsonObject
import xyz.yhsj.parse.utils.HttpRequest
import xyz.yshj.parse.match1
import java.net.URL
import java.util.*

/**搜狐视频
 * Created by LOVE on 2017/4/19 019.
 */
object Sohu {
    fun download(url: String) {
        var vid: String? = ""
        if ("http://share.vrs.sohu.com" in url) {
            vid = match1(url, "id=(\\d+)")
        } else {
            val html = HttpRequest.get(url).body()

            vid = match1(html, "\\Wvid\\s*[\\:=]\\s*['\"]?(\\d+)['\"]?")
        }

        if ("http://tv.sohu.com/" in url) {

            var hqvid = 0
            var info = HttpRequest.get("http://hot.vrs.sohu.com/vrs_flash.action?vid=$vid").body().jsonObject
            for (qtyp in arrayOf("oriVid", "superVid", "highVid", "norVid", "relativeId")) {


                if (!info.isNull("data")) {
                    hqvid = info.getJSONObject("data").getInt(qtyp)
                } else {
                    hqvid = info.getInt(qtyp)
                }

                if (hqvid != 0 && hqvid.toString() != vid) {
                    info = HttpRequest.get("http://hot.vrs.sohu.com/vrs_flash.action?vid=$hqvid").body().jsonObject

                    if (info.isNull("allot")) {
                        continue
                    }
                    break
                }
            }

            val host = info.getString("allot")
            val prot = info.getString("prot")
            val tvid = info.getString("tvid")

            val urls = ArrayList<String>()

            val data = info.getJSONObject("data")
            val title = data.getString("tvName")
            val size = data.getLong("totalBytes")


            val sus = data.getJSONArray("su")
            val clipsURLs = data.getJSONArray("clipsURL")
            val cks = data.getJSONArray("ck")

            for (i in 0..sus.length() - 1) {
                val su = sus.getString(i)
                val clip = clipsURLs.getString(i)
                val ck = cks.getString(i)
                var clipURL: String
                try {
                    clipURL = URL(clip).path
                } catch (e: Exception) {
                    clipURL = clip
                }

                urls.add(real_url(host, hqvid.toString(), tvid, su, clipURL, ck))
            }
            //TODO urls真实地址
        } else {
            val info = HttpRequest.get("http://my.tv.sohu.com/play/videonew.do?vid=$vid&referer=http://my.tv.sohu.com").body().jsonObject
            val host = info.getString("allot")
            val prot = info.getString("prot")
            val tvid = info.getString("tvid")

            val urls = ArrayList<String>()

            val data = info.getJSONObject("data")
            val title = data.getString("tvName")
            val size = data.getLong("totalBytes")

            val sus = data.getJSONArray("su")
            val clipsURLs = data.getJSONArray("clipsURL")
            val cks = data.getJSONArray("ck")

            for (i in 0..sus.length() - 1) {
                val su = sus.getString(i)
                val clip = clipsURLs.getString(i)
                val ck = cks.getString(i)

                var clipURL: String
                try {
                    clipURL = URL(clip).path
                } catch (e: Exception) {
                    clipURL = clip
                }

                urls.add(real_url(host, vid!!, tvid, su, clipURL, ck))
            }
        }
    }

    /**
     * 获取真实地址
     */
    fun real_url(host: String, vid: String, tvid: String, new: String, clipURL: String, ck: String): String {
        val url = "http://$host/?prot=9&prod=flash&pt=1&file=$clipURL&new=$new&key=$ck&vid=$vid&uid=${Date().time}&t=${Math.random()}&rb=1"
        val realUrl = HttpRequest.get(url).body().jsonObject.getString("url")
        println(realUrl)
        return realUrl
    }
}