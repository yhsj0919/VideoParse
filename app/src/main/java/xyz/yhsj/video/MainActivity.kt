package xyz.yhsj.video

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import xyz.yhsj.parse.extractors.*
import xyz.yhsj.parse.runAsync
import xyz.yhsj.parse.utils.HttpRequest

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        runAsync {
            //KuGou.download("http://www.kugou.com/yy/album/single/960327.html")
            //KuGou.download("http://www.kugou.com/yy/special/single/24745.html")
            //单曲
            //Netease.download("http://music.163.com/#/song?id=27808044")

            try {
                //专辑
//                Netease.download("http://music.163.com/#/album?id=2681139")
                //歌单
                //Netease.download("http://music.163.com/#/playlist?id=692893056")

//                Netease.download("http://music.163.com/#/program?id=2")
//                Netease.download("http://music.163.com/#/radio?id=3697004")
//                Netease.download("http://music.163.com/#/mv?id=420144")


//                YouKu.download("http://v.youku.com/v_show/id_XODMxNzI4MjQ4.id_XODMxNzI4MjQ4html")

//                Letv.download("http://www.le.com/ptv/vplay/27085339.html?ref=100000001")
                //
//                val sss = Letv.decode("VC_01123456789123456789123456789".toByteArray())
//                println(sss)

//                Sohu.download("http://my.tv.sohu.com/pl/9186474/88706000.shtml")
            QQ.download("https://v.qq.com/x/cover/kp4m5ys7tms73cd.html")
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}
