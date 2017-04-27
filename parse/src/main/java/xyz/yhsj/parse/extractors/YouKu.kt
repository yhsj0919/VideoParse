package xyz.yhsj.parse.extractors

import xyz.yhsj.parse.entity.ParseResult
import xyz.yhsj.parse.intfc.Parse
import xyz.yhsj.parse.jsonObject
import xyz.yhsj.parse.match1
import xyz.yhsj.parse.utils.Base64
import xyz.yhsj.parse.utils.HttpRequest
import kotlin.experimental.and
import kotlin.experimental.xor


/**优酷解析
 * Created by LOVE on 2017/4/13 013.
 */
object YouKu : Parse {
    val template1 = "becaf9be"
    val template2 = "bf7e5f01"

    override fun download(url: String): ParseResult {
        getdata(get_vid_from_url(url))

        return ParseResult()
    }

    /**
     * 1、 根据url获取视频id
     *
     * @param url 视频网页地址
     * @return
     */
    fun get_vid_from_url(url: String): String {
        val regex1 = "youku\\.com/v_show/id_([a-zA-Z0-9=]+)"
        val regex2 = "player\\.youku\\.com/player\\.php/sid/([a-zA-Z0-9=]+)/v\\.swf"
        val regex3 = "loader\\.swf\\?VideoIDS=([a-zA-Z0-9=]+)"
        val regex4 = "player\\.youku\\.com/embed/([a-zA-Z0-9=]+)"
        return regex1.match1(url) ?: regex2.match1(url) ?: regex3.match1(url) ?: regex4.match1(url) ?: ""
    }

    /**
     * 获取下载地址
     */
    fun getdata(vid: String) {

        println(vid)

        val url10 = "http://play.youku.com/play/get.json?ct=10&vid=$vid"
        val url12 = "http://play.youku.com/play/get.json?ct=12&vid=$vid"

        val resp12 = HttpRequest.get(url12).header("Referer", "http://static.youku.com/").header("Cookie", "__ysuid={}").body().jsonObject

        if (!resp12.isNull("error")) {
            println(resp12.getJSONObject("error").getString("note"))
            return
        }

        val resp10 = HttpRequest.get(url10).header("Referer", "http://static.youku.com/").header("Cookie", "__ysuid={}").body().jsonObject

        val security = resp12.getJSONObject("data").getJSONObject("security")
        val strEncrypt = security.getString("encrypt_string")

        val sidAndToken = getSidAndToken(strEncrypt)

        val mSid = sidAndToken[0]
        val mToken = sidAndToken[1]
        val mOip = security.getString("ip")

        val streams = resp10.getJSONObject("data").getJSONArray("stream")

        for (i in 0..streams.length() - 1) {

            val stream_type = getStreamType(streams.getJSONObject(i).getString("stream_type"))

            println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>${stream_type["msg"]}>>>>>>>>>>>>>>>>>>>>>>>>>.")

            val segs = streams.getJSONObject(i).getJSONArray("segs")

            for (j in 0..segs.length() - 1) {

                val fileId = segs.getJSONObject(j).getString("fileid")
                val ep = getEp(mSid, fileId, mToken)
                val key = segs.getJSONObject(j).getString("key")

                //下载地址,还得解析一次
                val videoUrl = "http://k.youku.com/player/getFlvPath/sid/${mSid}_00/st/${stream_type["type"]}/fileid/$fileId?ctype=12&ep=$ep&ev=1&oip=$mOip&token=$mToken&yxon=1&K=$key"

                println(videoUrl)
            }

            println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        }

    }


    /**
     * 获取视频的格式与描述

     * @return
     */
    fun getStreamType(streamType: String): Map<String, String> {
        return when (streamType) {
            "mp4hd3", "hd3" -> mapOf("type" to "flv", "msg" to "1080")
            "mp4hd2", "hd2" -> mapOf("type" to "flv", "msg" to "超清")
            "mp4hd", "mp4" -> mapOf("type" to "mp4", "msg" to "高清")
            "flvhd", "flv" -> mapOf("type" to "flv", "msg" to "标清")
            "3gphd" -> mapOf("type" to "3gp", "msg" to "标清(3GP)")
            else -> mapOf("type" to "flv", "msg" to "标清")
        }
    }

    /**
     * 获取必要字段
     */
    fun getSidAndToken(encryptStr: String): List<String> {
        val s = String(youkuEncoder(template1.toByteArray(), Base64.decode(encryptStr)))
        val rst = s.split("_")
        return rst
    }

    /**
     * 获取必要字段
     */
    fun getEp(sid: String, fileId: String, token: String): String {
        val epT = youkuEncoder(template2.toByteArray(), "${sid}_${fileId}_${token}".toByteArray())
        return Base64.encode(epT)
    }

    fun youkuEncoder(b1: ByteArray, b2: ByteArray): ByteArray {
        val result = ByteArray(b2.size)

        val s = IntArray(256)
        for (i in 0..255) {
            s[i] = i
        }
        var t = 0
        var tmp: Int
        for (i in 0..255) {
            t = (t + s[i] + (b1[i % b1.size] and 0xff.toByte())) % 256
            tmp = s[i]
            s[i] = s[t]
            s[t] = tmp
        }
        var x = 0
        var y = 0
        for (i in 0..b2.size - 1) {
            y = (y + 1) % 256
            x = (x + s[y]) % 256
            tmp = s[x]
            s[x] = s[y]
            s[y] = tmp
            result[i] = (b2[i] and 0xff.toByte() xor s[(s[x] + s[y]) % 256].toByte())
        }
        return result

    }
}