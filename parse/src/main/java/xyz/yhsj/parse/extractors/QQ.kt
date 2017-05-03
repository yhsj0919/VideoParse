package xyz.yhsj.parse.extractors

import org.json.JSONArray
import xyz.yhsj.parse.entity.ParseResult
import xyz.yhsj.parse.intfc.Parse
import xyz.yhsj.parse.jsonObject
import xyz.yhsj.parse.match1
import xyz.yhsj.parse.matchAll
import xyz.yhsj.parse.utils.HttpRequest

/**QQ视频
 * Created by LOVE on 2017/4/21 021.
 */
object QQ : Parse {
    override fun download(url: String): ParseResult {

        downloadByiteSite(url)

        return ParseResult()
    }

    fun downloadByiteSite(tempurl: String): ParseResult {
        var url = tempurl

        //全民k歌
        if ("kg.qq.com" in url || "kg2.qq.com" in url) {
            val chars = url.split("?s=")
            val shareid = chars[chars.lastIndex]

            kg_qq_download_by_shareid(shareid)
            return ParseResult()
        }

        if ("live.qq.com" in url) {
            //TODO企鹅直播
            return ParseResult()
        }

        if ("mp.weixin.qq.com/s" in url) {
            val content = HttpRequest.get(url).body()
            val vids = "\\bvid=(\\w+)".matchAll(content)

            for (vid in vids) {
                qq_download_by_vid(vid)
            }
            return ParseResult()
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
        return ParseResult()
    }

    /**
     * 通过id获取视频
     */
    fun qq_download_by_vid(vid: String) {
        val info_api = "http://vv.video.qq.com/getinfo?otype=json&appver=3%2E2%2E19%2E333&platform=11&defnpayver=1&vid=$vid"
        println(info_api)

        val info = HttpRequest.get(info_api)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .header("Accept-Charset", "UTF-8,*;q=0.5")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:54.0) Gecko/20100101 Firefox/54.0")
                .body().replace("QZOutputJson=", "").jsonObject

        val parts_vid = info.getJSONObject("vl").getJSONArray("vi").getJSONObject(0).getString("vid")
        val parts_ti = info.getJSONObject("vl").getJSONArray("vi").getJSONObject(0).getString("ti")
        var parts_prefix = info.getJSONObject("vl").getJSONArray("vi").getJSONObject(0).getJSONObject("ul").getJSONArray("ui").getJSONObject(0).getString("url")
        val parts_formats = info.getJSONObject("fl").getJSONArray("fi")

        println(parts_ti)

        if (parts_prefix.endsWith("/")) {
            parts_prefix = parts_prefix.substring(0, parts_prefix.length - 1)
        }

        var best_quality = ""
        for (i in 0..parts_formats.length() - 1) {
            val part_format = parts_formats.getJSONObject(i)
            if ("fhd" == part_format.getString("name")) {
                best_quality = "fhd"
                break
            }
            if ("shd" == part_format.getString("name")) {
                best_quality = "shd"
            }
        }

        for (i in 0..parts_formats.length() - 1) {
            val part_format = parts_formats.getJSONObject(i)
            if (best_quality != "" && part_format.getString("name") != best_quality) {
                continue
            }
            val part_format_id = part_format.getInt("id")
            val part_format_sl = part_format.getInt("sl")

            if (part_format_sl == 0) {
                val part_urls = ArrayList<String>()
                try {
                    for (part in 1..100) {
                        val filename = "$vid.p${part_format_id % 10000}.$part.mp4"
                        val key_api = "http://vv.video.qq.com/getkey?otype=json&platform=11&format=$part_format_id&vid=$parts_vid&filename=$filename"
                        val part_info = HttpRequest.get(key_api)
                                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                                .header("Accept-Charset", "UTF-8,*;q=0.5")
                                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:54.0) Gecko/20100101 Firefox/54.0")
                                .body().replace("QZOutputJson=", "").jsonObject

                        val vkey = part_info.getString("key")
                        val url = "$parts_prefix/$filename?vkey=$vkey"
                        part_urls.add(url)
                    }
                } catch (e: Exception) {
                }
                println(part_urls)
            } else {
                //下面是有问题的/只取了一段视频,以后再改
                val fvkey = info.getJSONObject("vl").getJSONArray("vi").getJSONObject(0).getString("fvkey")
                var mp4: JSONArray? = null

                var mp4url: String = ""

                try {
                    mp4 = info.getJSONObject("vl").getJSONArray("vi").getJSONObject(0).getJSONObject("cl").getJSONArray("ci")
                } catch (e: Exception) {
                }
                if (mp4 != null) {
                    //TODO 加入for循环
                    val old_id = mp4.getJSONObject(0).getString("keyid").split('.')[1]
                    val new_id = "p${(old_id.toIntOrNull() ?: 1) % 1000}"
                    mp4url = mp4.getJSONObject(0).getString("keyid").replace(old_id, new_id) + ".mp4"

                } else {
                    mp4url = info.getJSONObject("vl").getJSONArray("vi").getJSONObject(0).getString("fn")
                }

                val url = "$parts_prefix/$mp4url?vkey=$fvkey"

                println("else " + url)
            }
        }

        println("$parts_prefix   $best_quality")

    }

    /**
     * 全民k歌下载
     */
    fun kg_qq_download_by_shareid(shareid: String) {
        val BASE_URL = "http://cgi.kg.qq.com/fcgi-bin/kg_ugc_getdetail"
        val params_str = "?dataType=jsonp&jsonp=callback&jsonpCallback=jsopgetsonginfo&v=4&outCharset=utf-8&shareid=$shareid"
        val url = BASE_URL + params_str
        println(url)
        val content = HttpRequest.get(url)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .header("Accept-Charset", "UTF-8,*;q=0.5")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:54.0) Gecko/20100101 Firefox/54.0")
                .body()
        val json_data = content.substring(0, content.length - 1).replace("jsonpcallback(", "").jsonObject

        val playurl = json_data.getJSONObject("data").getString("playurl")
        val videourl = json_data.getJSONObject("data").getString("playurl_video")
        val real_url = playurl ?: videourl ?: ""

        val song_name = json_data.getJSONObject("data").getString("song_name")

        val ksong_mid = json_data.getJSONObject("data").getString("ksong_mid")

        val lyric_url = "http://cgi.kg.qq.com/fcgi-bin/fcg_lyric?jsonpCallback=jsopgetlrcdata&outCharset=utf-8&ksongmid=$ksong_mid"
        val lyric_data = HttpRequest.get(lyric_url)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .header("Accept-Charset", "UTF-8,*;q=0.5")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:54.0) Gecko/20100101 Firefox/54.0")
                .body()

        val lyric_json = lyric_data.substring(0, lyric_data.length - 1).replace("jsopgetlrcdata(", "").jsonObject

        val lyric = lyric_json.getJSONObject("data").getString("lyric")

        println(song_name)
        println(real_url)
        println(lyric)
    }


}