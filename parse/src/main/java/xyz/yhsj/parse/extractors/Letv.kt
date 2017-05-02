package xyz.yhsj.parse.extractors

import xyz.yhsj.parse.entity.MediaFile
import xyz.yhsj.parse.entity.MediaUrl
import xyz.yhsj.parse.entity.ParseResult
import xyz.yhsj.parse.getSDPath
import xyz.yhsj.parse.intfc.Parse
import xyz.yhsj.parse.jsonObject
import xyz.yhsj.parse.match1
import xyz.yhsj.parse.utils.HttpRequest
import xyz.yhsj.parse.writeToFile
import java.util.*

/**乐视
 * Created by LOVE on 2017/4/17 017.
 */
object Letv : Parse {

    override fun download(url: String): ParseResult {
        try {
            return letv_download_by_vid(getId(url))
        } catch (e: Exception) {
            return ParseResult(code = 500, msg = e.message ?: "")
        }

    }

    fun getId(url: String): String {
        val regex1 = "http://www.letv.com/ptv/vplay/(\\d+).html"
        val regex2 = "http://www.le.com/ptv/vplay/(\\d+).html"
        val regex3 = "vid=\"(\\d+)\""
        val regex4 = "vplay_([a-zA-Z0-9=]+)"

        return regex1.match1(url) ?: regex2.match1(url) ?: regex3.match1(url) ?: regex4.match1(url) ?: ""
    }


    fun letv_download_by_vid(vid: String): ParseResult {
        val url = "http://api.letv.com/mms/out/video/playJson?id=$vid&platid=1&splatid=101&format=1&tkey=${calcTimeKey(Date().time)}&domain=www.letv.com"

        println(url)

        val resp = HttpRequest.get(url)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .header("Accept-Charset", "UTF-8,*;q=0.5")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:54.0) Gecko/20100101 Firefox/54.0")
                .body()
                .jsonObject

        val video = resp.getJSONObject("playurl")

        val title = video.getString("title")

        val mediaFile = MediaFile()
        mediaFile.title = title

        val dispatch = video.getJSONObject("dispatch")

        val vkey = dispatch.keys().asSequence().toList()

        for (key in vkey) {

            println(key)

            val mediaUrl = MediaUrl(title)

            val m3u8Url = video.getJSONArray("domain").getString(0) + dispatch.getJSONArray(key).getString(0) + "&ctv=pc&m3v=1&termid=1&format=1&hwtype=un&ostype=Linux&tag=letv&sign=letv&expect=3&tn=${Math.random()}&pay=0&iscpn=f9051&rateid=$key"
            val resp2 = HttpRequest.get(m3u8Url)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .header("Accept-Charset", "UTF-8,*;q=0.5")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:54.0) Gecko/20100101 Firefox/54.0")
                    .body()
                    .jsonObject

            val m3u8 = HttpRequest.get(resp2.getString("location"))
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .header("Accept-Charset", "UTF-8,*;q=0.5")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:54.0) Gecko/20100101 Firefox/54.0")
                    .bytes()

            val m3u8_list = decode(m3u8)

            val filePath = writeToFile(getSDPath() + "/VideoParse/", "$title-$key.m3u8", m3u8_list, false)

            println(">>>>>>>>>>>>>>>>>>>" + filePath)

            mediaUrl.stream_type = key
            mediaUrl.playUrl.add(filePath)
            mediaUrl.downUrl.add(filePath)

            mediaFile.url.add(mediaUrl)

        }
        return ParseResult(data = mediaFile)
    }

    /**
     * 解析m3u8
     */
    fun decode(resp: ByteArray): String {
        val data = IntArray(resp.size)
        //这里主要是因为Java的byte是有符号的,直接转换为int操作
        resp.forEachIndexed { index, byte ->
            if (byte.toInt() >= 0) {
                data[index] = byte.toInt()
            } else {
                data[index] = 256 + byte.toInt()
            }
        }
        val version = resp.copyOfRange(0, 5)
        if (String(version).toLowerCase() == "vc_01") {
            //获取真正的m3u8
            val loc2 = data.copyOfRange(5, data.size)
            val length = loc2.size
            val loc4 = IntArray(2 * length)

            for (i in 0..(length - 1)) {
                loc4[2 * i] = loc2[i] ushr 4
                loc4[2 * i + 1] = loc2[i] and 15
            }
            val loc6 = loc4.copyOfRange(loc4.size - 11, loc4.size) + loc4.copyOfRange(0, loc4.size - 11)
            val loc7 = IntArray(length)
            for (i in 0..(length - 1)) {
                loc7[i] = (loc6[2 * i] shl 4) + loc6[2 * i + 1]
            }
            val m3u8 = StringBuffer()
            loc7.forEach {
                m3u8.append(it.toChar())
            }
            return m3u8.toString()
        } else {
            return String(resp)
        }
    }


    fun calcTimeKey(time: Long): Long {
        //将毫秒时间戳转化为unix时间戳

        var stime = 0.toLong()
        if (time > 1000000000000L) {
            stime = time / 1000
            println(stime.toString())
        }

        val key: Long = 773625421

        var value = GenerateKeyRor(stime, key % 13)

        value = value xor key

        value = GenerateKeyRor(value, key % 17)

        return value
    }

    fun GenerateKeyRor(value: Long, key: Long): Long {
        val temp = (Math.pow(2.0, 32.0) - 1).toLong()
        return value and temp shr (key % 32).toInt() or (value shl (32 - key % 32).toInt() and temp)
    }

}