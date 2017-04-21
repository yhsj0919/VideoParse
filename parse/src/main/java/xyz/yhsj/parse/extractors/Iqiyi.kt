package xyz.yhsj.parse.extractors

import org.json.JSONObject
import xyz.yhsj.parse.intfc.Parse
import xyz.yhsj.parse.jsonObject
import xyz.yhsj.parse.match1
import xyz.yhsj.parse.utils.HttpRequest
import xyz.yhsj.parse.utils.MD5

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**爱奇艺
 * Created by LOVE on 2017/4/18 018.
 */
object Iqiyi : Parse {

    val vd_2_id = mapOf(10 to "4k", 19 to "4k", 5 to "BD", 18 to "BD", 21 to "HD", 2 to "HD", 4 to "TD", 17 to "TD", 96 to "LD", 1 to "SD")
    val id_2_profile = mapOf("4k" to "4k", "BD" to "1080p", "TD" to "720p", "HD" to "540p", "SD" to "360p", "LD" to "210p")


    override fun download(url: String) {
        val html = HttpRequest.get(url)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .header("Accept-Charset", "UTF-8,*;q=0.5")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:54.0) Gecko/20100101 Firefox/54.0")
                //处理内部重定向
                .followRedirects(true)
                .body()

        val tvid = "#curid=(.+)_".match1(url) ?: "tvid=([^&]+)".match1(url) ?: "data-player-tvid=\"([^\"]+)\"".match1(html) ?: ""
        val videoid = "#curid=.+_(.*)$".match1(url) ?: "vid=([^&]+)".match1(url) ?: "data-player-videoid=\"([^\"]+)\"".match1(html) ?: ""
        val title = "<title>([^<]+)".match1(html)?.split('-')!![0]

        val info = getVMS(tvid, videoid)

        val vidl = info.getJSONObject("data").getJSONArray("vidl")


        val profiles = ArrayList<String>()

        val streams = HashMap<String, Map<String, String>>()

        for (i in 0..vidl.length() - 1) {
            val stream = vidl.getJSONObject(i)

            val stream_id = vd_2_id[stream.getInt("vd")] ?: continue

            val stream_profile = id_2_profile[stream_id] ?: continue

            if (stream_profile in profiles) continue else profiles.add(stream_profile)

            streams[stream_id] = mapOf("video_profile" to stream_profile, "container" to "m3u8", "src" to stream.getString("m3u"))

        }

        println(streams)
    }


    fun getVMS(tvid: String, vid: String): JSONObject {
        val t = Date().time
        val src = "76f90cbd92f94a2e925d83e8ccd22cb7"
        val key = "d5fb4bd9d50c4be6948c97edd7254b0e"

        val sc = MD5.getMD5CodeStr("$t$key$vid")

        val url = "http://cache.m.iqiyi.com/tmts/$tvid/$vid/?t=$t&sc=$sc&src=$src".format(tvid, vid, t, sc, src)

        return HttpRequest.get(url).body().jsonObject
    }
}