package xyz.yhsj.parse.extractors

import xyz.yshj.parse.match1
import java.util.*
import java.util.regex.Pattern

/**乐视
 * Created by LOVE on 2017/4/17 017.
 */
object Letv {


    fun letv_download(url: String) {
        val regex1 = "http://www.letv.com/ptv/vplay/(\\d+).html"
        val regex2 = "http://www.le.com/ptv/vplay/(\\d+).html"
        val regex3 = "vid=\"(\\d+)\""

        val vid = match1(url, regex1) ?: match1(url, regex2) ?: match1(url, regex3) ?: ""

        letv_download_by_vid(vid)
    }

    fun letv_download_by_vid(vid: String) {

        video_info(vid)

    }

    fun video_info(vid: String) {
        val url = "http://api.letv.com/mms/out/video/playJson?id=$vid&platid=1&splatid=101&format=1&tkey=${calcTimeKey(Date().time)}&domain=www.letv.com"

        println(url)
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