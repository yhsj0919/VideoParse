package xyz.yhsj.parse.extractors

import org.json.JSONObject
import xyz.yhsj.parse.intfc.Parse
import xyz.yhsj.parse.utils.HttpRequest
import xyz.yhsj.parse.jsonObject
import xyz.yhsj.parse.match1
import xyz.yhsj.parse.utils.Base64
import xyz.yhsj.parse.utils.MD5

import java.util.regex.Matcher
import java.util.regex.Pattern


/**酷狗
 * Created by LOVE on 2017/2/28 028.
 */
object KuGou : Parse {

    /**
     * 下载列表
     */
    private fun download_playlist(url: String) {

        val html = HttpRequest.get(url).body()

        val p = Pattern.compile("title=\"(.*?)\".* data=\"(\\w*)\\|.*?\"")
        val p2 = Pattern.compile("data=\"(\\w*)\\|(.*?)\"")
        val matcher: Matcher = p.matcher(html)
        val matcher2: Matcher = p2.matcher(html)

        var singNum = 0
        while (matcher.find() && matcher2.find()) {
            singNum++
            try {
                kugou_download_by_hash(matcher.group(1), matcher2.group(1))
            } catch (e: Exception) {
                println("出错了:${e.message}")
            }
        }
        if (singNum == 0) {
            throw Exception("暂时只支持酷狗播放页面,专辑页面,歌单,5sing原创播放页面")
        }

    }

    /**
     * 下载单个歌曲
     */
    private fun kugou_download_by_hash(title: String? = null, hash_val: String) {
        val key = MD5.getMD5CodeStr("${hash_val}kgcloud")
        //{"status":0,
        // "error":"The Resource Needs to be Paid"}
        //{
        // "fileHead": 100,
        // "q": 0,
        // "fileSize": 3635736,
        // "fileName": "香香 - 炫爱",
        // "status": 1,
        // "url": "http:\/\/fs.web.kugou.com\/079ea1900aa803579b269f2825b0dc38\/58b531cb\/G073\/M01\/05\/19\/KZQEAFfGct2AaaC4ADd6GC0ewQ0893.mp3",
        // "extName": "mp3",
        // "bitRate": 128000,
        // "timeLength": 227
        // }
//        println(title)
        val url = "http://trackercdn.kugou.com/i/?pid=6&key=$key&acceptMp3=1&cmd=4&hash=$hash_val"
//        println(url)
        val obj = HttpRequest.get(url).body().jsonObject

        if (obj.getInt("status") == 0) {
            println("出错啦:${obj.getString("error")}")
        } else {
//            println("文件名:${obj.getString("fileName")}.${obj.getString("extName")}")

            val temptitle = obj.getString("fileName")
            val real_title = if (title == null) {
                temptitle
            } else {
                if (temptitle.length > title.length) {
                    title
                } else {
                    temptitle
                }
            }

            println("文件名:     $real_title.${obj.getString("extName")}")
            println("下载地址:   ${obj.getString("url")}")
        }
    }

    /**
     * 原创音乐下载
     */
    private fun kugou_5sing(url: String) {
        val html = HttpRequest.get(url).body()

        val ticket = "\"ticket\":\\s*\"(.*)\"".match1(html)

        if (ticket == null) {
            println("未发现")
            return
        }
        try {
            val obj = JSONObject(String(Base64.decode(ticket)))
//                    {
//                        "songID": 3287805,
//                        "songType": "yc",
//                        "songName": "可念不可说",
//                        "file": "http:\/\/data.5sing.kgimg.com\/G086\/M04\/13\/13\/lg0DAFitALWAD4wrAKysyjpYK24758.mp3",
//                        "singerID": 11945608,
//                        "singer": "崔子格",
//                        "avatar": "http:\/\/img5.5sing.kgimg.com\/force\/T1sq_xBQKT1RXrhCrK.jpg",
//                        "collect": false
//                    }
            println("文件名   :${obj.getString("songName")}")
            println("下载地址 :${obj.getString("file")}")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun download(url: String) {
        //http://5sing.kugou.com/yc/3287805.html
        //http://www.kugou.com/song/#B274BD2549B723B966A52DBC5921AA7B
//        http://www.kugou.com/song/#hash=2688ADB1CA449448388270987BDCE6E8&album_id=960327
        //http://www.kugou.com/yy/album/single/1776093.html


        if ("5sing" in url.toLowerCase()) {
            kugou_5sing(url)
        } else if ("http://www.kugou.com/song/" in url.toLowerCase()) {
            val hash = "hash=([^&]+)".match1(url) ?: ""
            kugou_download_by_hash(hash_val = hash)

        } else {
            download_playlist(url)
        }

    }


}


