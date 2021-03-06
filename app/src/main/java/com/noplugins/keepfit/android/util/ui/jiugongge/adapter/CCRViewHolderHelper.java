package com.noplugins.keepfit.android.util.ui.jiugongge.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import android.text.Html;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.noplugins.keepfit.android.util.ui.jiugongge.listen.CCROnItemChildCheckedChangeListener;
import com.noplugins.keepfit.android.util.ui.jiugongge.listen.CCROnItemChildClickListener;
import com.noplugins.keepfit.android.util.ui.jiugongge.listen.CCROnItemChildLongClickListener;
import com.noplugins.keepfit.android.util.ui.jiugongge.listen.CCROnNoDoubleClickListener;
import com.noplugins.keepfit.android.util.ui.jiugongge.listen.CCROnRVItemChildTouchListener;


/**
 * 在此写用途
 *
 * @Author: Acheng
 * @Email: 345887272@qq.com
 * @Date: 2017-09-22 12:04
 * @Version: V1.0 <描述当前版本功能>
 */

public class CCRViewHolderHelper implements View.OnLongClickListener, CompoundButton.OnCheckedChangeListener, View.OnTouchListener {
    protected final SparseArrayCompat<View> mViews;
    protected CCROnItemChildClickListener mOnItemChildClickListener;
    protected CCROnItemChildLongClickListener mOnItemChildLongClickListener;
    protected CCROnItemChildCheckedChangeListener mOnItemChildCheckedChangeListener;
    protected CCROnRVItemChildTouchListener mOnRVItemChildTouchListener;
    protected View mConvertView;
    protected Context mContext;
    protected int mPosition;
    protected CCRRecyclerViewHolder mRecyclerViewHolder;
    protected RecyclerView mRecyclerView;

    protected AdapterView mAdapterView;
    /**
     * 留着以后作为扩充对象
     */
    protected Object mObj;

    public CCRViewHolderHelper(ViewGroup adapterView, View convertView) {
        mViews = new SparseArrayCompat<>();
        mAdapterView = (AdapterView) adapterView;
        mConvertView = convertView;
        mContext = convertView.getContext();
    }

    public CCRViewHolderHelper(RecyclerView recyclerView, CCRRecyclerViewHolder recyclerViewHolder) {
        mViews = new SparseArrayCompat<>();
        mRecyclerView = recyclerView;
        mRecyclerViewHolder = recyclerViewHolder;
        mConvertView = mRecyclerViewHolder.itemView;
        mContext = mConvertView.getContext();
    }

    public CCRRecyclerViewHolder getRecyclerViewHolder() {
        return mRecyclerViewHolder;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public int getPosition() {
        if (mRecyclerViewHolder != null) {
            return mRecyclerViewHolder.getAdapterPositionWrapper();
        }
        return mPosition;
    }

    /**
     * 设置item子控件点击事件监听器
     *
     * @param onItemChildClickListener
     */
    public void setOnItemChildClickListener(CCROnItemChildClickListener onItemChildClickListener) {
        mOnItemChildClickListener = onItemChildClickListener;
    }

    /**
     * 为id为viewId的item子控件设置点击事件监听器
     *
     * @param viewId
     */
    public void setItemChildClickListener(@IdRes int viewId) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(new CCROnNoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (mOnItemChildClickListener != null) {
                        if (mRecyclerView != null) {
                            mOnItemChildClickListener.onItemChildClick(mRecyclerView, v, getPosition());
                        } else if (mAdapterView != null) {
                            mOnItemChildClickListener.onItemChildClick(mAdapterView, v, getPosition());
                        }
                    }
                }
            });
        }

    }

    /**
     * 设置item子控件长按事件监听器
     *
     * @param onItemChildLongClickListener
     */
    public void setOnItemChildLongClickListener(CCROnItemChildLongClickListener onItemChildLongClickListener) {
        mOnItemChildLongClickListener = onItemChildLongClickListener;
    }

    /**
     * 为id为viewId的item子控件设置长按事件监听器
     *
     * @param viewId
     */
    public void setItemChildLongClickListener(@IdRes int viewId) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnLongClickListener(this);
        }
    }

    /**
     * 设置 RecyclerView 中的 item 子控件触摸事件监听器
     *
     * @param onRVItemChildTouchListener
     */
    public void setOnRVItemChildTouchListener(CCROnRVItemChildTouchListener onRVItemChildTouchListener) {
        mOnRVItemChildTouchListener = onRVItemChildTouchListener;
    }

    /**
     * 为 id 为 viewId 的 RecyclerView 的 item 子控件设置触摸事件监听器
     *
     * @param viewId
     */
    public void setRVItemChildTouchListener(@IdRes int viewId) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnTouchListener(this);
        }
    }

    /**
     * 设置item子控件选中状态变化事件监听器
     *
     * @param onItemChildCheckedChangeListener
     */
    public void setOnItemChildCheckedChangeListener(CCROnItemChildCheckedChangeListener onItemChildCheckedChangeListener) {
        mOnItemChildCheckedChangeListener = onItemChildCheckedChangeListener;
    }

    /**
     * 为id为viewId的item子控件设置选中状态变化事件监听器
     *
     * @param viewId
     */
    public void setItemChildCheckedChangeListener(@IdRes int viewId) {
        View view = getView(viewId);
        if (view != null && view instanceof CompoundButton) {
            ((CompoundButton) view).setOnCheckedChangeListener(this);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (mOnRVItemChildTouchListener != null && mRecyclerView != null) {
            return mOnRVItemChildTouchListener.onRvItemChildTouch(mRecyclerViewHolder, view, motionEvent);
        }
        return false;
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemChildLongClickListener != null) {
            if (mRecyclerView != null) {
                return mOnItemChildLongClickListener.onItemChildLongClick(mRecyclerView, v, getPosition());
            } else if (mAdapterView != null) {
                return mOnItemChildLongClickListener.onItemChildLongClick(mAdapterView, v, getPosition());
            }
        }
        return false;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (mOnItemChildCheckedChangeListener != null) {
            if (mRecyclerView != null) {
                CCRRecyclerViewAdapter recyclerViewAdapter;

                RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
                if (adapter instanceof CCRHeaderAndFooterAdapter) {
                    recyclerViewAdapter = (CCRRecyclerViewAdapter) ((CCRHeaderAndFooterAdapter) adapter).getInnerAdapter();
                } else {
                    recyclerViewAdapter = (CCRRecyclerViewAdapter) adapter;
                }
                if (!recyclerViewAdapter.isIgnoreCheckedChanged()) {
                    mOnItemChildCheckedChangeListener.onItemChildCheckedChanged(mRecyclerView, buttonView, getPosition(), isChecked);
                }
            } else if (mAdapterView != null) {
                if (!((CCRAdapterViewAdapter) mAdapterView.getAdapter()).isIgnoreCheckedChanged()) {
                    mOnItemChildCheckedChangeListener.onItemChildCheckedChanged(mAdapterView, buttonView, getPosition(), isChecked);
                }
            }
        }
    }

    /**
     * 通过控件的Id获取对应的控件，如果没有则加入mViews，则从item根控件中查找并保存到mViews中
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 通过ImageView的Id获取ImageView
     *
     * @param viewId
     * @return
     */
    public ImageView getImageView(@IdRes int viewId) {
        return getView(viewId);
    }

    /**
     * 通过TextView的Id获取TextView
     *
     * @param viewId
     * @return
     */
    public TextView getTextView(@IdRes int viewId) {
        return getView(viewId);
    }

    /**
     * 获取item的根控件
     *
     * @return
     */
    public View getConvertView() {
        return mConvertView;
    }

    public void setObj(Object obj) {
        mObj = obj;
    }

    public Object getObj() {
        return mObj;
    }

    /**
     * 设置对应id的控件的文本内容
     *
     * @param viewId
     * @param text
     * @return
     */
    public CCRViewHolderHelper setText(@IdRes int viewId, CharSequence text) {
        if (text == null) {
            text = "";
        }
        getTextView(viewId).setText(text);
        return this;
    }

    /**
     * 设置对应id的控件的文本内容
     *
     * @param viewId
     * @param stringResId 字符串资源id
     * @return
     */
    public CCRViewHolderHelper setText(@IdRes int viewId, @StringRes int stringResId) {
        getTextView(viewId).setText(stringResId);
        return this;
    }

    /**
     * 设置对应id的控件的文字大小，单位为 sp
     *
     * @param viewId
     * @param size   文字大小，单位为 sp
     * @return
     */
    public CCRViewHolderHelper setTextSizeSp(@IdRes int viewId, float size) {
        getTextView(viewId).setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        return this;
    }

    /**
     * 设置对应id的控件的文字是否为粗体
     *
     * @param viewId
     * @param isBold 是否为粗体
     * @return
     */
    public CCRViewHolderHelper setIsBold(@IdRes int viewId, boolean isBold) {
        getTextView(viewId).getPaint().setTypeface(isBold ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        return this;
    }

    /**
     * 设置对应id的控件的html文本内容
     *
     * @param viewId
     * @param source html文本
     * @return
     */
    public CCRViewHolderHelper setHtml(@IdRes int viewId, String source) {
        if (source == null) {
            source = "";
        }
        getTextView(viewId).setText(Html.fromHtml(source));
        return this;
    }

    /**
     * 设置对应id的控件是否选中
     *
     * @param viewId
     * @param checked
     * @return
     */
    public CCRViewHolderHelper setChecked(@IdRes int viewId, boolean checked) {
        Checkable view = getView(viewId);
        view.setChecked(checked);
        return this;
    }

    public CCRViewHolderHelper setTag(@IdRes int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public CCRViewHolderHelper setTag(@IdRes int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public CCRViewHolderHelper setVisibility(@IdRes int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    public CCRViewHolderHelper setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public CCRViewHolderHelper setImageDrawable(@IdRes int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    /**
     * @param viewId
     * @param textColorResId 颜色资源id
     * @return
     */
    public CCRViewHolderHelper setTextColorRes(@IdRes int viewId, @ColorRes int textColorResId) {
        getTextView(viewId).setTextColor(mContext.getResources().getColor(textColorResId));
        return this;
    }

    /**
     * @param viewId
     * @param textColor 颜色值
     * @return
     */
    public CCRViewHolderHelper setTextColor(@IdRes int viewId, int textColor) {
        getTextView(viewId).setTextColor(textColor);
        return this;
    }

    /**
     * @param viewId
     * @param backgroundResId 背景资源id
     * @return
     */
    public CCRViewHolderHelper setBackgroundRes(@IdRes int viewId, int backgroundResId) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundResId);
        return this;
    }

    /**
     * @param viewId
     * @param color  颜色值
     * @return
     */
    public CCRViewHolderHelper setBackgroundColor(@IdRes int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    /**
     * @param viewId
     * @param colorResId 颜色值资源id
     * @return
     */
    public CCRViewHolderHelper setBackgroundColorRes(@IdRes int viewId, @ColorRes int colorResId) {
        View view = getView(viewId);
        view.setBackgroundColor(mContext.getResources().getColor(colorResId));
        return this;
    }

    /**
     * @param viewId
     * @param imageResId 图像资源id
     * @return
     */
    public CCRViewHolderHelper setImageResource(@IdRes int viewId, @DrawableRes int imageResId) {
        ImageView view = getView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    /**
     * 设置字体是否为粗体
     *
     * @param viewId
     * @param isBold
     * @return
     */
    public CCRViewHolderHelper setBold(@IdRes int viewId, boolean isBold) {
        getTextView(viewId).getPaint().setTypeface(isBold ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        return this;
    }
}