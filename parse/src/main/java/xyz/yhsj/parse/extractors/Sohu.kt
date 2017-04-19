package xyz.yhsj.parse.extractors

import xyz.yhsj.parse.jsonObject
import xyz.yhsj.parse.utils.HttpRequest
import xyz.yshj.parse.match1
import java.net.URL

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

        println(vid)


        if ("http://tv.sohu.com/" in url) {
            var info = HttpRequest.get("http://hot.vrs.sohu.com/vrs_flash.action?vid=$vid").body().jsonObject
            for (qtyp in arrayOf("oriVid", "superVid", "highVid", "norVid", "relativeId")) {

                var hqvid = 0

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

            for (i in 0..sus.length()-1) {
                val su = sus.getString(i)
                val clip = clipsURLs.getString(i)
                val ck = cks.getString(i)
                val clipURL = URL(clip).path

                println(clipURL)

            }


            println(host)
            println(prot)
            println(tvid)

        }


    }
}