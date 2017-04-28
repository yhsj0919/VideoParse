package xyz.yhsj.video.parse

import android.content.Intent
import android.net.Uri
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import kotterknife.bindView
import xyz.yhsj.video.BaseActivity
import xyz.yhsj.parse.extractors.Iqiyi
import xyz.yhsj.parse.extractors.YouKu
import xyz.yhsj.parse.match0
import xyz.yhsj.parse.runAsync
import xyz.yhsj.video.R

class VideoParseActivity : BaseActivity() {
    val recyclerView by bindView<RecyclerView>(R.id.recyclerView)
    val toolbar by bindView<Toolbar>(R.id.toolbar)
    val fab by bindView<FloatingActionButton>(R.id.fab)

    var htmlUrl: String? = null


    override fun getLayoutId(): Int = R.layout.activity_video_parse

    override fun getToolBar(): Toolbar = toolbar

    override fun getMenuLayout(): Int = R.menu.menu_parse

    override fun init() {
        htmlUrl = getShare()
        fab.setOnClickListener {

            runAsync {
                parseUrl(htmlUrl ?: "")
            }


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
            Toast.makeText(this, "优酷", Toast.LENGTH_SHORT).show()
            runAsync {
                YouKu.download(url)
            }
        } else if ("le.com" in url) {
            Toast.makeText(this, "乐视", Toast.LENGTH_SHORT).show()
        } else if ("iqiyi.com" in url) {
            Toast.makeText(this, "爱奇艺", Toast.LENGTH_SHORT).show()
            Iqiyi.download(url)
        } else if ("sohu.com" in url) {
            Toast.makeText(this, "搜狐", Toast.LENGTH_SHORT).show()
        } else if ("qq.com" in url) {
            Toast.makeText(this, "腾讯", Toast.LENGTH_SHORT).show()
        } else {

            Toast.makeText(this, "暂不支持该网站", Toast.LENGTH_SHORT).show()
        }
    }


    /**
     * 获取分享内容
     */
    fun getShare(): String {
        val intent = intent ?: return ""
        val extras = intent.extras ?: return ""

        if (intent.type == "text/plain") {
            return getUrl(extras.getString(Intent.EXTRA_TEXT))
        } else {
            return ""
        }
    }

    /**
     * 获取url
     */
    fun getUrl(url: String): String {
        val match = "(http://|ftp://|https://|www){0,1}[^\u4e00-\u9fa5\\s]*?\\.(com|net|cn|me|tw|fr)[^\u4e00-\u9fa5\\s]*"
        return match.match0(url) ?: url
    }


}
