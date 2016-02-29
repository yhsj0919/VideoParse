package xyz.yhsj.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;

import xyz.yhsj.helper.ViewHolderHelper;

/**一个不含自定义布局的Tab适配器，用于helper中
 * Created by LOVE on 2015/12/14.
 */
public final class SimpleTabLayoutAdapter extends BaseTabLayoutAdapter<String> {
    public SimpleTabLayoutAdapter(Context context, FragmentManager fm) {
        super(context, fm, 0);
    }

    @Override
    protected void bindData(ViewHolderHelper viewHolderHelper, int position, String model) {

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDatas.get(position);
    }
}
