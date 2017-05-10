package xyz.yhsj.video.parse

import android.content.Intent
import android.net.Uri
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import kotterknife.bindView
import xyz.yhsj.parse.entity.ParseResult
import xyz.yhsj.video.BaseActivity
import xyz.yhsj.parse.extractors.Iqiyi
import xyz.yhsj.parse.extractors.Letv
import xyz.yhsj.parse.extractors.YouKu
import xyz.yhsj.parse.match0
import xyz.yhsj.parse.runAsync
import xyz.yhsj.video.R
import xyz.yhsj.video.adapter.VideoStreamAdapter
import android.text.Spannable
import android.text.style.ImageSpan
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.text.SpannableStringBuilder
import android.widget.TextView
import xyz.yhsj.parse.extractors.Sohu


class VideoParseActivity : BaseActivity() {
    val recyclerView by bindView<RecyclerView>(R.id.recyclerView)
    val toolbar by bindView<Toolbar>(R.id.toolbar)
    val toolbarLayout by bindView<CollapsingToolbarLayout>(R.id.toolbar_layout)
    val fab by bindView<FloatingActionButton>(R.id.fab)

    var htmlUrl: String? = null
    lateinit var streamAdapter: VideoStreamAdapter


    override fun getLayoutId(): Int = R.layout.activity_video_parse

    override fun getToolBar(): Toolbar = toolbar

    override fun getMenuLayout(): Int = R.menu.menu_parse

    override fun init() {
        streamAdapter = VideoStreamAdapter(recyclerView)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = streamAdapter

        streamAdapter.setOnItemClickListener { viewGroup, view, i ->

            startActivity(Intent(this, VideoActivity::class.java).putExtra("video", streamAdapter.data[i]))
        }



        htmlUrl = getShare()
        fab.setOnClickListener {

            parseUrl(htmlUrl ?: "")

        }

    }


    override fun onMenuClickListener(item: MenuItem) {
        super.onMenuClickListener(item)

        when (item.itemId) {
            R.id.action_link -> {
                if (!htmlUrl.isNullOrBlank()) {
                    val uri = Uri.parse(htmlUrl)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "url获取失败,请重试", Toast.LENGTH_SHORT).show()
                }

            }
            R.id.action_about -> {
            }
        }

    }


    fun parseUrl(url: String) {

        if ("kugou.com" in url) {
            Toast.makeText(this, "酷狗", Toast.LENGTH_SHORT).show()
        } else if ("music.163.com" in url) {
            Toast.makeText(this, "网易", Toast.LENGTH_SHORT).show()
        } else if ("youku.com" in url) {
            toolbarLayout.title = "优酷"
            runAsync {
                val result = YouKu.download(url)
                runOnUiThread {
                    setResult(result)
                }
            }
            runAsync {
                YouKu.download(url)
            }
        } else if ("le.com" in url) {
            toolbarLayout.title = "乐视"
            runAsync {
                val result = Letv.download(url)
                runOnUiThread {
                    setResult(result)
                }
            }
        } else if ("iqiyi.com" in url) {
            toolbarLayout.title = "爱奇艺"
            runAsync {
                val result = Iqiyi.download(url)
                runOnUiThread {
                    setResult(result)
                }
            }

        } else if ("sohu.com" in url) {
            toolbarLayout.title = "搜狐"
            runAsync {
                val result = Sohu.download(url)
                runOnUiThread {
                    setResult(result)
                }
            }

        } else if ("qq.com" in url) {
            Toast.makeText(this, "腾讯", Toast.LENGTH_SHORT).show()
        } else {

            Toast.makeText(this, "暂不支持该网站", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 显示数据
     */
    fun setResult(result: ParseResult) {

        if (result.code == 200) {
            toolbarLayout.title = result.data?.title
            streamAdapter.data = result.data?.url
        } else {
            println(result.msg)
            Toast.makeText(this, "解析失败,请稍后重试", Toast.LENGTH_SHORT).show()
        }

    }

    /**
     * 获取分享内容
     */
    fun getShare(): String {
        val intent = intent ?: return ""
        val extras = intent.extras ?: return ""

        return getUrl(extras.getString(Intent.EXTRA_TEXT) ?: "")

    }

    /**
     * 获取url
     */
    fun getUrl(url: String): String {
        val match = "(http://|ftp://|https://|www){0,1}[^\u4e00-\u9fa5\\s]*?\\.(com|net|cn|me|tw|fr)[^\u4e00-\u9fa5\\s]*"
        return match.match0(url) ?: url
    }


}
