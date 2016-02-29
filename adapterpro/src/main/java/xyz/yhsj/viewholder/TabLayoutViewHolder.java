/**
 * Copyright 2015 bingoogolapple
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.yhsj.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.yhsj.helper.ViewHolderHelper;

/**
 * Tablayout ViewHodel
 * Created by LOVE on 2015/12/14.
 */
public class TabLayoutViewHolder {
    protected View mConvertView;
    protected ViewHolderHelper mViewHolderHelper;

    private TabLayoutViewHolder(Context context, int layoutId) {
        mConvertView = LayoutInflater.from(context).inflate(layoutId, null);
//        mConvertView.setTag(this);
        mViewHolderHelper = new ViewHolderHelper(null, mConvertView);
    }

    /**
     * 拿到一个可重用的ViewHolder对象
     *
     * @param context
     * @param layoutId
     * @return
     */
    public static TabLayoutViewHolder dequeueReusableAdapterViewHolder(Context context, int layoutId) {

        return new TabLayoutViewHolder(context, layoutId);

    }

    public ViewHolderHelper getViewHolderHelper() {
        return mViewHolderHelper;
    }

    public View getConvertView() {
        return mConvertView;
    }

}