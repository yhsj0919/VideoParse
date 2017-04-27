package xyz.yhsj.parse.extractors


import org.json.JSONObject
import xyz.yhsj.parse.entity.ParseResult
import xyz.yhsj.parse.intfc.Parse
import xyz.yhsj.parse.jsonObject
import xyz.yhsj.parse.match1
import xyz.yhsj.parse.utils.Base64
import xyz.yhsj.parse.utils.HttpRequest
import xyz.yhsj.parse.utils.MD5
import kotlin.experimental.xor


/**网易
 * Created by LOVE on 2017/3/1 001.
 */
object Netease : Parse {
    override fun download(url: String): ParseResult {
        if ("163.fm" in url) {
        }
        if ("music.163.com" in url) {
            netease_cloud_music_download(url)
        } else {

        }

        return ParseResult()
    }

    /**
     * 音乐分类
     */
    private fun netease_cloud_music_download(url: String) {
        var rid = "\\Wid=(.*)".match1(url)
        if (rid == null) {
            rid = "/(\\d+)/?".match1(url)
        }

        if ("album" in url) {
            //专辑
            val resp = HttpRequest.get("http://music.163.com/api/album/$rid?id=$rid&csrf_token=").header("Referer", "http://music.163.com/").body().jsonObject
            //resp['album']['artists'][0]['name']
            val artist_name = resp.getJSONObject("album").getJSONArray("artists").getJSONObject(0).getString("name")
            val album_name = resp.getJSONObject("album").getString("name")
            val cover_url = resp.getJSONObject("album").getString("picUrl")
            //TODO 后期封装
            println("歌手:$artist_name    专辑:$album_name   封面:$cover_url")

            val songs = resp.getJSONObject("album").getJSONArray("songs")
            for (i in 0..songs.length() - 1) {
                netease_song_download(songs.getJSONObject(i))
            }

        } else if ("playlist" in url) {
            //歌单
            val resp = HttpRequest.get("http://music.163.com/api/playlist/detail?id=$rid&csrf_token=").header("Referer", "http://music.163.com/").body().jsonObject
            val playlist_name = resp.getJSONObject("result").getString("name")
            val cover_url = resp.getJSONObject("result").getString("coverImgUrl")

            println("$playlist_name  封面:$cover_url")

            val songs = resp.getJSONObject("result").getJSONArray("tracks")
            for (i in 0..songs.length() - 1) {
                netease_song_download(songs.getJSONObject(i))
            }

        } else if ("song" in url) {
            val resp = HttpRequest.get("http://music.163.com/api/song/detail/?id=$rid&ids=[$rid]&csrf_token=").header("Referer", "http://music.163.com/").body().jsonObject
            netease_song_download(resp.getJSONArray("songs").getJSONObject(0))

        } else if ("program" in url) {
            //电台节目
            val resp = HttpRequest.get("http://music.163.com/api/dj/program/detail/?id=$rid&ids=[$rid]&csrf_token=").header("Referer", "http://music.163.com/").body().jsonObject
            netease_song_download(resp.getJSONObject("program").getJSONObject("mainSong"))
        } else if ("radio" in url) {
            //电台
            val resp = HttpRequest.get("http://music.163.com/api/dj/program/byradio/?radioId=$rid&ids=[$rid]&csrf_token=&limit=200").header("Referer", "http://music.163.com/").body().jsonObject
            val songs = resp.getJSONArray("programs")
            for (i in 0..songs.length() - 1) {
                netease_song_download(songs.getJSONObject(i).getJSONObject("mainSong"))
            }
        } else if ("mv" in url) {
            //MV
            val resp = HttpRequest.get("http://music.163.com/api/mv/detail/?id=$rid&ids=[$rid]&csrf_token=").header("Referer", "http://music.163.com/").body().jsonObject
            netease_video_download(resp.getJSONObject("data"))

        }
    }

    /**
     * 网易云音乐下载
     */
    fun netease_song_download(song: JSONObject) {
        val title = "${song["name"]}"
        val songNet = "p" + song["mp3Url"].toString().split("/")[2].substring(1)
        var url_best: String = ""
        if (!song.isNull("hMusic") && song["hMusic"] != null) {
            url_best = make_url(songNet, song.getJSONObject("hMusic").getLong("dfsId"))
        } else if (!song.isNull("mp3Url")) {
            url_best = song.getString("mp3Url")
        } else if (!song.isNull("bMusic")) {
            url_best = make_url(songNet, song.getJSONObject("bMusic").getLong("dfsId"))
        }

        println("歌曲名称: $title")
        println("下载地址:" + url_best)


    }

    /**
     * 视频下载
     */
    fun netease_video_download(video: JSONObject) {
        val title = "${video["name"]}"
        val artistName = "${video["artistName"]}"

        val videos = video.getJSONObject("brs")

        println("歌曲名称: $title")
        println("歌曲名称: $artistName")

        val vkey = videos.keys().asSequence().toList().maxBy { it.toInt() }

        println(vkey)
        println(videos[vkey])
    }

    /**
     * 拼接下载地址
     */
    fun make_url(songNet: String, dfsId: Long): String {
        val encId = encrypted_id(dfsId.toString())
        val mp3_url = "http://$songNet/$encId/$dfsId.mp3"
        return mp3_url
    }

    /**
     * 核心算法
     * 加密的Key
     */
    fun encrypted_id(dfsId: String): String {
        val byte1 = "3go8&$8*3*3h0k(2)2".toByteArray()
        val byte2 = dfsId.toByteArray()
        for (i in 0..byte2.size - 1) {
            byte2[i] = byte2[i] xor byte1[i % byte1.size]
        }
        val m = MD5.getMD5Code(byte2)
        var result = Base64.encode(m!!)
        result = result.replace("/", "_").replace("+", "-")
        return result
    }
}