package xyz.yhsj.video

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import xyz.yhsj.parse.extractors.*
import xyz.yhsj.parse.match0
import xyz.yhsj.parse.runAsync
import xyz.yhsj.video.adapter.MainAdapter
import xyz.yhsj.video.parse.VideoActivity


class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var mainAdapter: MainAdapter
    lateinit var et_url: EditText
    lateinit var btn_parse: Button

    val netArray = arrayListOf("爱奇艺", "优酷", "酷狗", "乐视", "网易云音乐", "搜狐", "腾讯")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView) as RecyclerView

        et_url = findViewById(R.id.et_url) as EditText
        btn_parse = findViewById(R.id.btn_parse) as Button

        et_url.setText(getUrl(getShare()))

        btn_parse.setOnClickListener {
            parseUrl(et_url.text.toString())
        }

        recyclerView.layoutManager = GridLayoutManager(this, 4)
        mainAdapter = MainAdapter(recyclerView)
        recyclerView.adapter = mainAdapter
        mainAdapter.data = netArray
        mainAdapter.setOnItemClickListener { _, _, i ->
            runAsync {
                try {

                    when (netArray[i]) {
                        "酷狗" -> {
//                            KuGou.download("http://www.kugou.com/song/#hash=2688ADB1CA449448388270987BDCE6E8&album_id=960327")
                            KuGou.download("http://www.kugou.com/yy/album/single/960327.html")
                            //KuGou.download("http://www.kugou.com/yy/special/single/24745.html")
                        }
                        "网易云音乐" -> {
                            //单曲
                            //Netease.download("http://music.163.com/#/song?id=27808044")
                            //专辑
                            //Netease.download("http://music.163.com/#/album?id=2681139")
                            //歌单
                            //Netease.download("http://music.163.com/#/playlist?id=692893056")

                            //Netease.download("http://music.163.com/#/program?id=2")
                            //Netease.download("http://music.163.com/#/radio?id=3697004")
                            //Netease.download("http://music.163.com/#/mv?id=420144")
                        }
                        "优酷" -> {
                            val result = YouKu.download("http://v.youku.com/v_show/id_XODMxNzI4MjQ4.id_XODMxNzI4MjQ4html")
                            if (result.code == 200) {
                                println(result.data?.title)
                                println(result.data)

                            } else {
                                println(result.msg)
                            }

                        }

                        "乐视" -> {
                            val result = Letv.download("http://www.le.com/ptv/vplay/27085339.html?ref=100000001")
                            if (result.code == 200) {
                                println(result.data?.title)
                                println(result.data)

                            } else {
                                println(result.msg)
                            }
                        }
                        "爱奇艺" -> {
                            val result = Iqiyi.download("http://www.iqiyi.com/v_19rrl9crao.html")
                            runOnUiThread {
                                if (result.code == 200) {
                                    println(result.data?.title)
                                    startActivity(Intent(this, VideoActivity::class.java).putExtra("video", result.data?.url!![0]))
                                } else {
                                    Toast.makeText(this, "解析失败,请稍后重试", Toast.LENGTH_SHORT).show()
                                }
                            }

                        }
                        "搜狐" -> {
                            Sohu.download("http://m.tv.sohu.com/v3745060.shtml?channeled=1211010100&aid=9218631")
//                            Sohu.download("http://my.tv.sohu.com/pl/9186474/88706000.shtml")
//                            Sohu.download("http://tv.sohu.com/20150915/n421160663.shtml")
//                            https://m.tv.sohu.com/v2581078.shtml?aid=9056592&channeled=1210040001&columnid=297
                        }
                        "腾讯" -> {
                            QQ.download("https://v.qq.com/x/cover/nuijxf6k13t6z9b/a0023zlgbz3.html")
//                            QQ.download("https://v.qq.com/x/cover/kp4m5ys7tms73cd.html")
//                            QQ.download("http://kg.qq.com/share.html?s=2QEQDZ2QTvNYN2qz")

                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


    fun parseUrl(url: String) {

        if ("kugou.com" in url) {
            Toast.makeText(this, "酷狗", Toast.LENGTH_SHORT).show()
        } else if ("music.163.com" in url) {
            Toast.makeText(this, "网易", Toast.LENGTH_SHORT).show()
        } else if ("youku.com" in url) {
            Toast.makeText(this, "优酷", Toast.LENGTH_SHORT).show()
            runAsync {
                YouKu.download(url)
            }

        } else if ("le.com" in url) {
            Toast.makeText(this, "乐视", Toast.LENGTH_SHORT).show()
        } else if ("iqiyi.com" in url) {
            Toast.makeText(this, "爱奇艺", Toast.LENGTH_SHORT).show()
            runAsync {
                val result = Iqiyi.download(url)
                runOnUiThread {
                    if (result.code == 200) {
                        println(result.data?.title)
                        startActivity(Intent(this, VideoActivity::class.java).putExtra("video", result.data))
                    } else {
                        Toast.makeText(this, "解析失败,请稍后重试", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else if ("sohu.com" in url) {
            Toast.makeText(this, "搜狐", Toast.LENGTH_SHORT).show()
        } else if ("qq.com" in url) {
            Toast.makeText(this, "腾讯", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "暂不支持该网站", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 获取url
     */
    fun getUrl(url: String): String {
        val match = "(http://|ftp://|https://|www){0,1}[^\u4e00-\u9fa5\\s]*?\\.(com|net|cn|me|tw|fr)[^\u4e00-\u9fa5\\s]*"
        return match.match0(url) ?: url
    }

    /**
     * 获取分享内容
     */
    fun getShare(): String {
        val intent = intent ?: return ""
        val extras = intent.extras ?: return ""

        if (intent.type == "text/plain") {
            return extras.getString(Intent.EXTRA_TEXT)
        } else {
            return ""
        }
    }
}
