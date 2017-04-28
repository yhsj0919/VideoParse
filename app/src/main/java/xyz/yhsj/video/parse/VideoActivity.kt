package xyz.yhsj.video.parse

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.GSYVideoPlayer
import com.shuyu.gsyvideoplayer.model.GSYVideoModel
import com.shuyu.gsyvideoplayer.utils.GSYVideoType
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.ListGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import xyz.yhsj.parse.entity.MediaFile
import xyz.yhsj.video.R
import xyz.yhsj.video.listener.SampleListener
import java.util.ArrayList

class VideoActivity : AppCompatActivity() {
    lateinit var player: ListGSYVideoPlayer
    lateinit var orientationUtils: OrientationUtils

    private var isPlay: Boolean = false
    private var isPause: Boolean = false

    private var mediaFile: MediaFile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        player = findViewById(R.id.player) as ListGSYVideoPlayer


        mediaFile = intent.getSerializableExtra("video") as MediaFile?


        GSYVideoManager.instance().setVideoType(this, GSYVideoType.IJKEXOPLAYER)


        if (mediaFile != null) {
            val videos = ArrayList<GSYVideoModel>()

            val playUrl = mediaFile!!.url[0]

            for (url in playUrl.playUrl) {
                videos.add(GSYVideoModel(url, mediaFile!!.title + "-" + playUrl.stream_type))
            }
            player.setUp(videos, false, 0)
        }

        initPlayer()

    }


    fun initPlayer() {
        //外部辅助的旋转，帮助全屏
        orientationUtils = OrientationUtils(this, player)
        //初始化不打开外部的旋转
        orientationUtils.isEnable = false

        player.setIsTouchWiget(true)
        //detailPlayer.setIsTouchWigetFull(false);
        //关闭自动旋转
        player.isRotateViewAuto = false
        player.isLockLand = false
        player.isShowFullAnimation = false
        player.isNeedLockFull = true


        player.fullscreenButton.setOnClickListener {

            //直接横屏
            orientationUtils.resolveByClick()

            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            player.startWindowFullscreen(this, true, true)
        }

        player.setStandardVideoAllCallBack(object : SampleListener() {
            override fun onPrepared(url: String, vararg objects: Any) {
                super.onPrepared(url, objects)
                //开始播放了才能旋转和全屏
                orientationUtils.isEnable = true
                isPlay = true
            }

            override fun onAutoComplete(url: String, vararg objects: Any) {
                super.onAutoComplete(url, objects)
            }

            override fun onClickStartError(url: String, vararg objects: Any) {
                super.onClickStartError(url, objects)
            }

            override fun onQuitFullscreen(url: String, vararg objects: Any) {
                super.onQuitFullscreen(url, objects)

                orientationUtils.backToProtVideo()

            }
        })

        player.setLockClickListener({ _, lock ->

            //配合下方的onConfigurationChanged
            orientationUtils.isEnable = !lock

        })
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
                if (!player.isIfCurrentIsFullscreen()) {
                    player.startWindowFullscreen(this, true, true)
                }
            } else {
                //新版本isIfCurrentIsFullscreen的标志位内部提前设置了，所以不会和手动点击冲突
                if (player.isIfCurrentIsFullscreen) {
                    StandardGSYVideoPlayer.backFromWindowFull(this)
                }

                orientationUtils.isEnable = true

            }
        }
    }

    override fun onBackPressed() {

        orientationUtils.backToProtVideo()

        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }


    override fun onPause() {
        super.onPause()
        isPause = true
    }

    override fun onResume() {
        super.onResume()
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoPlayer.releaseAllVideos()
        //GSYPreViewManager.instance().releaseMediaPlayer();
        if (orientationUtils != null)
            orientationUtils.releaseListener()
    }
}
