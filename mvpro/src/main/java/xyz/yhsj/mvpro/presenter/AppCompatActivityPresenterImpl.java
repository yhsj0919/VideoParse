package xyz.yhsj.mvpro.presenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import xyz.yhsj.mvpro.helper.ParameterizedTypeUtil;
import xyz.yhsj.mvpro.view.IView;


/**
 * 将Activity作为Presenter的基类 <br />
 * Created by qibin on 2015/11/15.
 */
public class AppCompatActivityPresenterImpl<T extends IView> extends AppCompatActivity implements IPresenter<T> {

    protected T mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(savedInstanceState);

        try {
            mView = getViewClass().newInstance();
            mView.bindPresenter(this);
            setContentView(mView.create(getLayoutInflater(), null));
            initToolbar();
            mView.bindEvent();
            created(savedInstanceState);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 初始化ToolBar
     */
    protected void initToolbar() {
        Toolbar toolbar = mView.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    /**
     * 菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mView.getOptionsMenuId() != -1) {
            getMenuInflater().inflate(mView.getOptionsMenuId(), menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public Class<T> getViewClass() {
        return ParameterizedTypeUtil.getViewClass(getClass());
    }

    @Override
    public void create(Bundle savedInstance) {

    }


    @Override
    public void created(Bundle savedInstance) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mView == null) {
            try {
                mView = getViewClass().newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException("create IDelegate error");
            } catch (IllegalAccessException e) {
                throw new RuntimeException("create IDelegate error");
            }
        }
    }

}
