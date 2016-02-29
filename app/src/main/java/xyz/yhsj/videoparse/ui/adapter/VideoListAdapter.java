package xyz.yhsj.videoparse.ui.adapter;

import android.support.v7.widget.RecyclerView;

import xyz.yhsj.adapter.BaseRecyclerViewAdapter;
import xyz.yhsj.helper.ViewHolderHelper;
import xyz.yhsj.videoparse.R;
import xyz.yhsj.videoparse.extractors.youku.YouKu;
import xyz.yhsj.videoparse.extractors.youku.entity.StreamEntity;


/**
 * Created by LOVE on 2016/2/17.
 */
public class VideoListAdapter extends BaseRecyclerViewAdapter<StreamEntity> {

    public VideoListAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.video_list_item);
    }

    @Override
    protected void bindData(ViewHolderHelper viewHolderHelper, int position, StreamEntity model) {

        viewHolderHelper.setText(R.id.title, YouKu.getStreamType(model.getStream_type()).getVideo_profile());

    }
}
