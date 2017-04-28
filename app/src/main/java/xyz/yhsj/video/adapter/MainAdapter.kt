package xyz.yhsj.video.adapter

import android.support.v7.widget.RecyclerView
import xyz.yhsj.adapter.BaseRecyclerViewAdapter
import xyz.yhsj.helper.ViewHolderHelper
import xyz.yhsj.video.R

/**
 * Created by LOVE on 2017/4/24 024.
 */
class MainAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<String>(recyclerView, R.layout.item_main_list) {
    override fun bindData(helper: ViewHolderHelper, p1: Int, title: String) {
        helper.setText(R.id.text, title)
        when (title) {
            "优酷" -> helper.setImageResource(R.id.img, R.drawable.ic_youku)
            "爱奇艺" -> helper.setImageResource(R.id.img, R.drawable.ic_iqiyi)
            "乐视" -> helper.setImageResource(R.id.img, R.drawable.ic_letv)
            "网易云音乐" -> helper.setImageResource(R.id.img, R.drawable.ic_netease)
            "腾讯" -> helper.setImageResource(R.id.img, R.drawable.ic_qq)
            "搜狐" -> helper.setImageResource(R.id.img, R.drawable.ic_sohu)
            "酷狗" -> helper.setImageResource(R.id.img, R.drawable.ic_kugou)
        }
    }
}