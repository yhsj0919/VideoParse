package xyz.yhsj.video

import android.os.Bundle
import android.support.v4.view.WindowCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem

/**
 * Created by yph on 2017/3/14.
 */
abstract class BaseActivity : AppCompatActivity() {

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_MODE_OVERLAY)
        setContentView(getLayoutId())

        val _toolBar = getToolBar()
        if (_toolBar != null) {
            setSupportActionBar(_toolBar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        init()
    }

    /**
     * 返回布局id
     */
    abstract fun getLayoutId(): Int

    /**
     * 返回toolbar
     */
    abstract fun getToolBar(): Toolbar?

    /**
     * 初始化功能
     */
    abstract fun init()

    /**
     * 获取菜单布局
     */
    open fun getMenuLayout(): Int = 0

    /**
     * 菜单的点击
     */
    open fun onMenuClickListener(item: MenuItem) {

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        val _menuLayout = getMenuLayout()

        if (_menuLayout > 0) {
            inflater.inflate(_menuLayout, menu)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        } else {
            onMenuClickListener(item)
        }

        return super.onOptionsItemSelected(item)
    }

}