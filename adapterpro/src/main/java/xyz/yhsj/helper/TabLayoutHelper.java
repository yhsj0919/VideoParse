package xyz.yhsj.helper;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import xyz.yhsj.adapter.BaseTabLayoutAdapter;
import xyz.yhsj.adapter.SimpleTabLayoutAdapter;

/**
 * TabLayout的帮助类
 * Created by LOVE on 2015/12/14.
 */
public class TabLayoutHelper<T> {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BaseTabLayoutAdapter adapter;
    private Boolean isBindView;


    /**
     * 不含Adapter的绑定方法
     *
     * @param context
     * @param tabLayout
     * @param viewPager
     */
    public void bindTab(AppCompatActivity context, @Nullable TabLayout tabLayout, @Nullable ViewPager viewPager) {

        SimpleTabLayoutAdapter mAdapter = new SimpleTabLayoutAdapter(context, context.getSupportFragmentManager());

        this.tabLayout = tabLayout;
        this.viewPager = viewPager;
        this.adapter = mAdapter;
        this.isBindView = false;
        bindTab();
    }

    /**
     * 自定义view的绑定方法
     *
     * @param tabLayout
     * @param viewPager
     * @param adapter
     */
    public void bindTab(@Nullable TabLayout tabLayout, @Nullable ViewPager viewPager, @Nullable BaseTabLayoutAdapter adapter) {

        this.tabLayout = tabLayout;
        this.viewPager = viewPager;
        this.adapter = adapter;
        this.isBindView = true;

        bindTab();
    }


    /**
     * 添加Fragment同时刷新tab，当绑定时使用的是不含自定义view的方法时，请传入String类型的数据
     *
     * @param fragment
     * @param newData
     */
    public void addFragment(Fragment fragment, T newData) {
        adapter.addFragment(fragment, newData);

        bindTab();

    }

    /**
     * 删除Fragment同时刷新tab，当绑定时使用的是不含自定义view的方法时，请传入String类型的数据
     *
     * @param position
     */
    public void removeFragment(int position) {
        adapter.removeFragment(position);
        bindTab();
    }

    /**
     * 删除Fragment同时刷新tab，当绑定时使用的是不含自定义view的方法时，请传入String类型的数据
     *
     * @param fragment
     */
    public void removeFragment(Fragment fragment) {
        adapter.removeFragment(fragment);
        bindTab();
    }

    /**
     * 删除Fragment同时刷新tab，当绑定时使用的是不含自定义view的方法时，请传入String类型的数据
     *
     * @param data
     */
    public void removeFragment(T data) {
        adapter.removeFragment(data);
        bindTab();
    }


    /**
     * 绑定tab
     * 每次更新了数据需要重新绑定，技术有限，暂时没有别的方法。
     */
    private void bindTab() {
        if (viewPager.getAdapter() == null) {
            viewPager.setAdapter(adapter);
        }
        //tab和滑动界面绑定
        tabLayout.setupWithViewPager(viewPager);

        if (isBindView) {
            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                tab.setCustomView(adapter.getTabView(i));
            }
        }

    }


    public BaseTabLayoutAdapter getAdapter() {
        return adapter;
    }
}
