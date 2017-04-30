package xyz.yhsj.video.adapter

import android.support.v7.widget.RecyclerView
import xyz.yhsj.adapter.BaseRecyclerViewAdapter
import xyz.yhsj.helper.ViewHolderHelper
import xyz.yhsj.parse.entity.MediaUrl
import xyz.yhsj.video.R

/**清晰度列表
 * Created by LOVE on 2017/4/30.
 */
class VideoStreamAdapter(recyclerView: RecyclerView) : BaseRecyclerViewAdapter<MediaUrl>(recyclerView, R.layout.item_stream_list) {
    override fun bindData(helper: ViewHolderHelper, index: Int, data: MediaUrl) {
        helper.setText(R.id.title, data.stream_type)
    }
}