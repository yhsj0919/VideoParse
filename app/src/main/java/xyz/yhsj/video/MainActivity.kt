package xyz.yhsj.video

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import xyz.yhsj.parse.extractors.Netease
import xyz.yhsj.parse.runAsync

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        runAsync {
            //            KuGou.kugou_download("http://www.kugou.com/yy/album/single/960327.html")
            //KuGou.kugou_download("http://www.kugou.com/yy/special/single/24745.html")
            //单曲
//             Netease.netease_download("http://music.163.com/#/song?id=27808044")

            try {
                //专辑
//                Netease.netease_download("http://music.163.com/#/album?id=2681139")
                //歌单
                //Netease.netease_download("http://music.163.com/#/playlist?id=692893056")

//                Netease.netease_download("http://music.163.com/#/program?id=2")
//                Netease.netease_download("http://music.163.com/#/radio?id=3697004")
                Netease.netease_download("http://music.163.com/#/mv?id=420144")
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}
