package xyz.yhsj.mvpro.presenter;

import android.os.Bundle;

/**
 * Presenter的根接口<br />
 * Created by qibin on 2015/11/15.
 */
public interface IPresenter<T> {
    /**
     * 获取当前presenter泛型的类型
     * @return
     */
    Class<T> getViewClass();

    /**
     * View初始化之前可以在此方法做一些操作
     */
    void create(Bundle savedInstance);

    /**
     * View初始化完毕后调用
     */
    void created(Bundle savedInstance);
}
