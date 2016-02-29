package xyz.yhsj.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xyz.yhsj.helper.ViewHolderHelper;
import xyz.yhsj.viewholder.TabLayoutViewHolder;

/**
 * TabLayout适配器
 * <p>
 * Created by LOVE on 2015/12/14.
 */
public abstract class BaseTabLayoutAdapter<T> extends FragmentStatePagerAdapter {

    private Context mContext;

    protected final int mItemLayoutId;

    protected List<Fragment> mFragments;

    protected List<T> mDatas;

    /**
     * 扩充数据，用于某些特殊的多数据源场景
     */
    protected HashMap<String, Object> mObj;

    public BaseTabLayoutAdapter(Context context, FragmentManager fm, int mItemLayoutId) {
        super(fm);
        this.mContext = context;
        this.mItemLayoutId = mItemLayoutId;
        mDatas = new ArrayList<>();
        mObj=new HashMap<>();
        mFragments = new ArrayList<>();
    }

    /**
     * 添加页面，不建议直接使用，请配合helper，可能会产生tab无法添加的情况
     *
     * @param fragment
     * @param newData
     */
    public void addFragment(Fragment fragment, T newData) {
        mFragments.add(fragment);
        mDatas.add(newData);
        notifyDataSetChanged();
    }

    /**
     * 删除页面，不建议直接使用，请配合helper，可能会产生tab无法删除的情况
     *
     * @param position
     */
    public void removeFragment(int position) {
        if ((position < mDatas.size() || position < mFragments.size()) && position >= 0) {
            mFragments.remove(position);
            mDatas.remove(position);
        }
        notifyDataSetChanged();
    }

    /**
     * 删除页面，不建议直接使用，请配合helper，可能会产生tab无法删除的情况
     *
     * @param fragment
     */
    public void removeFragment(Fragment fragment) {
        int position = mFragments.indexOf(fragment);

        if (position < mDatas.size() || position < mFragments.size()) {
            mFragments.remove(position);
            mDatas.remove(position);
        }

        notifyDataSetChanged();
    }

    /**
     * 删除页面，不建议直接使用，请配合helper，可能会产生tab无法删除的情况
     *
     * @param data
     */
    public void removeFragment(T data) {
        int position = mDatas.indexOf(data);

        if (position < mDatas.size() || position < mFragments.size()) {
            mFragments.remove(position);
            mDatas.remove(position);
        }
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDatas.get(position).toString();
    }


    /**
     * 自定义view时使用，获取tab的填充布局
     *
     * @param position
     * @return
     */
    public View getTabView(int position) {

        final TabLayoutViewHolder viewHolder = TabLayoutViewHolder.dequeueReusableAdapterViewHolder(mContext, mItemLayoutId);
        viewHolder.getViewHolderHelper().setPosition(position);

        bindData(viewHolder.getViewHolderHelper(), position, mDatas.get(position));

        if (position == 0) {
            viewHolder.getViewHolderHelper().getConvertView().setSelected(true);
        }

        return viewHolder.getViewHolderHelper().getConvertView();
    }


    /**
     * 填充item数据
     *
     * @param viewHolderHelper
     * @param position
     * @param model
     */
    protected abstract void bindData(ViewHolderHelper viewHolderHelper, int position, T model);

    public Object getmObj(String key) {
        return mObj.get(key);
    }

    public void addmObj(String key, Object mObj) {
        this.mObj.put(key, mObj);
    }
}
