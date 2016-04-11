package xyz.yhsj.mvpro.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.yhsj.mvpro.helper.ParameterizedTypeUtil;
import xyz.yhsj.mvpro.view.IView;


/**
 * Fragment作为Presenter的基类 <br />
 * Created by qibin on 2015/11/15.
 */
public class FragmentPresenterImpl<T extends IView> extends Fragment implements IPresenter<T> {

    protected T mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        create(savedInstanceState);
        try {
            mView = getViewClass().newInstance();
            View view = mView.create(inflater, container);
            mView.bindPresenter(this);
            mView.bindEvent();
            created(savedInstanceState);
            return view;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Class<T> getViewClass() {
        return ParameterizedTypeUtil.getViewClass(getClass());
    }

    @Override
    public void create(Bundle savedInstance) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (mView.getOptionsMenuId() != 0) {
            inflater.inflate(mView.getOptionsMenuId(), menu);
        }
    }

    @Override
    public void created(Bundle savedInstance) {

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (mView == null) {
            try {
                mView = getViewClass().newInstance();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
