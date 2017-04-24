package xyz.yhsj.video

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import xyz.yhsj.parse.extractors.QQ
import xyz.yhsj.parse.runAsync

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var mainAdapter: MainAdapter

    val netArray = arrayListOf("爱奇艺", "优酷", "酷狗", "乐视", "网易云音乐", "搜狐", "腾讯")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView) as RecyclerView

        recyclerView.layoutManager = GridLayoutManager(this, 4)
        mainAdapter = MainAdapter(recyclerView)
        recyclerView.adapter = mainAdapter
        mainAdapter.data = netArray


        mainAdapter.setOnItemClickListener { _, _, i ->
            runAsync {
                try {

                    when (netArray[i]) {
                        "酷狗" -> {
                            //KuGou.download("http://www.kugou.com/yy/album/single/960327.html")
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
                            //YouKu.download("http://v.youku.com/v_show/id_XODMxNzI4MjQ4.id_XODMxNzI4MjQ4html")
                        }

                        "乐视" -> {
                            //Letv.download("http://www.le.com/ptv/vplay/27085339.html?ref=100000001")
                        }
                        "爱奇艺" -> {
                        }
                        "搜狐" -> {
                            //Sohu.download("http://my.tv.sohu.com/pl/9186474/88706000.shtml")
                        }
                        "腾讯" -> {
//                            QQ.download("https://v.qq.com/x/cover/kp4m5ys7tms73cd.html")
                            QQ.download("http://kg.qq.com/share.html?s=2QEQDZ2QTvNYN2qz")

                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }


    }
}
