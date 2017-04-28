package xyz.yhsj.video

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by yph on 2017/3/14.
 */
abstract class BaseFragment : Fragment() {

    /**
     * 初始化view
     */
    final override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(getLayoutId(), null, false)
        initView(view)
        return view
    }

    /**
     * 返回布局id
     */
    abstract fun getLayoutId(): Int

    /**
     * 初始化view
     */
    abstract fun initView(view: View)



}