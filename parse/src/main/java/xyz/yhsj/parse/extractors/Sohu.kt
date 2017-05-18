package xyz.yhsj.parse.extractors

import xyz.yhsj.parse.entity.MediaFile
import xyz.yhsj.parse.entity.MediaUrl
import xyz.yhsj.parse.entity.ParseResult
import xyz.yhsj.parse.intfc.Parse
import xyz.yhsj.parse.jsonObject
import xyz.yhsj.parse.match1
import xyz.yhsj.parse.utils.HttpRequest
import java.net.URL
import java.util.*

/**搜狐视频
 * Created by LOVE on 2017/4/19 019.
 *
 *
 */
object Sohu : Parse {
    override fun download(url: String): ParseResult {

       val tempUrl =url.replace("://m.","://")


        try {
            val vid = getVid(url)
            println(vid)
            if (vid.isNullOrBlank()) {
                return ParseResult(code = 500, msg = "获取视频id失败")
            }
            return downloadByVid(tempUrl, vid)
        } catch (e: Exception) {
            return ParseResult(code = 500, msg = e.message ?: "未知错误")
        }
    }

    /**
     * 获取id
     */
    fun getVid(url: String): String {
        var vid: String? = ""
        if ("http://share.vrs.sohu.com" in url) {
            vid = "id=(\\d+)".match1(url)
        } else {
            val html = HttpRequest.get(url).body()

            vid = "\\Wvid\\s*[\\:=]\\s*['\"]?(\\d+)['\"]?".match1(html)
        }

        return vid ?: ""
    }

    /**
     * 获取连接
     */
    fun downloadByVid(url: String, vid: String): ParseResult {

        println(url)

        val mediaFile = MediaFile()

        if ("://tv.sohu.com/" in url) {
            //后期改为这个地址解析
            //http://api.tv.sohu.com/v4/video/info/3357323.json?site=1&api_key=695fe827ffeb7d74260a813025970bd5&sver=1.0&partner=1

            var info = HttpRequest.get("http://hot.vrs.sohu.com/vrs_flash.action?vid=$vid").body().jsonObject

            println(info)

            for (qtyp in arrayOf("oriVid", "superVid", "highVid", "norVid", "relativeId")) {
                var hqvid = 0

                if (!info.isNull("data")) {
                    hqvid = info.getJSONObject("data").getInt(qtyp)
                } else {
                    hqvid = info.getInt(qtyp)
                }

                if (hqvid != 0 && hqvid.toString() != vid) {
                    info = HttpRequest.get("http://hot.vrs.sohu.com/vrs_flash.action?vid=$hqvid").body().jsonObject

                    println(info)
                    if (info.isNull("allot")) {
                        continue
                    } else {
                        val host = info.getString("allot")
                        val prot = info.getString("prot")
                        val tvid = info.getString("tvid")

                        val data = info.getJSONObject("data")
                        val title = data.getString("tvName")
                        val size = data.getLong("totalBytes")

                        mediaFile.title = title

                        val sus = data.getJSONArray("su")
                        val clipsURLs = data.getJSONArray("clipsURL")
                        val cks = data.getJSONArray("ck")

                        val mediaUrl = MediaUrl(title)
                        mediaUrl.stream_type = qtyp

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
                            val realUrl = real_url(host, hqvid.toString(), tvid, su, clipURL, ck)
                            mediaUrl.playUrl.add(realUrl)
                            mediaUrl.downUrl.add(realUrl)
                        }
                        mediaFile.url.add(mediaUrl)
                    }
//                    break
                }

            }

            //TODO urls真实地址
        } else {
            val info = HttpRequest.get("http://my.tv.sohu.com/play/videonew.do?vid=$vid&referer=http://my.tv.sohu.com").body().jsonObject
            println(">>>>>>>>>>>>>>>>>>>>>>>")
            println(info)

            val host = info.getString("allot")
            val prot = info.getString("prot")
            val tvid = info.getString("tvid")

            val data = info.getJSONObject("data")
            val title = data.getString("tvName")
            val size = data.getLong("totalBytes")
            mediaFile.title = title


            val sus = data.getJSONArray("su")
            val clipsURLs = data.getJSONArray("clipsURL")
            val cks = data.getJSONArray("ck")

            val mediaUrl = MediaUrl(title)

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

                val realUrl = real_url(host, vid, tvid, su, clipURL, ck)
                mediaUrl.playUrl.add(realUrl)
                mediaUrl.downUrl.add(realUrl)
            }
            mediaFile.url.add(mediaUrl)
        }


        println(mediaFile)

        return ParseResult(data = mediaFile)
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