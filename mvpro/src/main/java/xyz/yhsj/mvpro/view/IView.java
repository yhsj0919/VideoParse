package xyz.yhsj.mvpro.view;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.yhsj.mvpro.presenter.IPresenter;


/**
 * View层的根接口 <br />
 * Created by qibin on 2015/11/15.
 */
public interface IView {
    /**
     * 根据 {@link getLayoutId}方法生成生成setContentView需要的根布局
     *
     * @param inflater
     * @param container
     * @return
     */
    View create(LayoutInflater inflater, ViewGroup container);

    /**
     * 返回当前视图需要的layout的id
     *
     * @return
     */
    int getLayoutId();

    /**
     * 返回当前视图需要的layout的id
     *
     * @return
     */
    int getOptionsMenuId();


    /**
     * 返回Toolbar
     *
     * @return Toolbar
     */
    Toolbar getToolbar();

    /**
     * 当Activity的onCreate完毕后调用
     */
    void created();

    /**
     * 根据id获取view
     *
     * @param id
     * @param <V>
     * @return
     */
    <V extends View> V findViewById(int id);

    /**
     * 绑定Presenter
     *
     * @param presenter
     */
    void bindPresenter(IPresenter presenter);

    /**
     * {@link created}后调用，可以调用{@link xyz.yhsj.mvpro.helper.EventHelper.click}
     * 等方法为控件设置点击事件，一般推荐使用{@link xyz.yhsj.mvpro.helper.EventHelper.click(IPresenter presenter, View ...views)}
     * 方法并且让你的Presenter实现相应接口。
     */
    void bindEvent();
}