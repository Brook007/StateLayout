/*
 * Copyright (c) 2016-present, Brook007 Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.brook.app.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.AttrRes;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.brook.app.android.statelayout.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @auther Brook
 * @time 2018年4月17日 16:17
 */
public class StateLayout extends FrameLayout {

    private View mNoNetworkView;
    private View mErrorView;
    private View mNoDataView;
    private View mSuccessView;
    private View mOnLoadingView;

    // 当前状态
    private int mState = DEFAULT_STATE;

    // 空数据
    public static final int NO_DATA = 5;
    // 加载中
    public static final int LOADING = 1;
    // 加载成功
    public static final int SUCCESS = 2;
    // 加载错误
    public static final int ERROR = 3;
    // 无网络
    public static final int NO_NETWORK = 4;


    @IntDef({NO_DATA, LOADING, SUCCESS, ERROR, NO_NETWORK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoadState {

    }

    public static int DEFAULT_STATE = LOADING;

    private boolean isFirst = true;

    private Handler mHandler;

    public StateLayout(@NonNull Context context) {
        this(context, null);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StateLayout);
            mState = typedArray.getInt(R.styleable.StateLayout_default_state, LOADING);
            int noDataView = typedArray.getResourceId(R.styleable.StateLayout_onEmptyView, -1);
            int onLoadingView = typedArray.getResourceId(R.styleable.StateLayout_onLoadingView, -1);
            int onErrorView = typedArray.getResourceId(R.styleable.StateLayout_onErrorView, -1);
            int onNoNetworkView = typedArray.getResourceId(R.styleable.StateLayout_onNoNetworkView, -1);

            LayoutInflater from = LayoutInflater.from(context);
            if (noDataView > 0) {
                setNoDataView(from.inflate(noDataView, this, false));
            }
            if (onLoadingView > 0) {
                setOnLoadingView(from.inflate(onLoadingView, this, false));
            }
            if (onErrorView > 0) {
                setErrorView(from.inflate(onErrorView, this, false));
            }
            if (onNoNetworkView > 0) {
                setNoNetworkView(from.inflate(onNoNetworkView, this, false));
            }
            typedArray.recycle();
        }
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (isFirst) {
            setState(mState);
            isFirst = false;
        }
    }


    /**
     * 设置无网络时的视图
     *
     * @param resId
     */
    public void setNoNetworkView(@LayoutRes int resId) {
        setNoNetworkView(LayoutInflater.from(getContext()).inflate(resId, this, false));
    }

    /**
     * 通过ID设置无网络时的视图
     *
     * @param resId
     */
    public void setNoNetworkViewById(@IdRes int resId) {
        setNoNetworkView(findViewById(resId));
    }

    /**
     * 设置无网络状态的视图
     *
     * @param view
     */
    public void setNoNetworkView(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof LayoutParams) {
            ((LayoutParams) layoutParams).mStateFlag = NO_NETWORK;
        } else {
            layoutParams = new StateLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((LayoutParams) layoutParams).mStateFlag = NO_NETWORK;
        }
        view.setLayoutParams(layoutParams);
        this.mNoNetworkView = view;
    }

    /**
     * 设置无数据时的视图
     *
     * @param resId
     */
    public void setNoDataView(@LayoutRes int resId) {
        setNoDataView(LayoutInflater.from(getContext()).inflate(resId, this, false));
    }

    /**
     * 通过ID设置空数据的视图
     *
     * @param resId
     */
    public void setNoDataViewById(@IdRes int resId) {
        setNoDataView(findViewById(resId));
    }

    public void setNoDataView(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof LayoutParams) {
            ((LayoutParams) layoutParams).mStateFlag = NO_DATA;
        } else {
            layoutParams = new StateLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((LayoutParams) layoutParams).mStateFlag = NO_DATA;
        }
        view.setLayoutParams(layoutParams);
        this.mNoDataView = view;
    }


    /**
     * 设置错误视图，通过ID
     *
     * @param resId
     */
    public void setErrorViewById(@IdRes int resId) {
        setErrorView(findViewById(resId));
    }


    /**
     * 设置加载错误时的视图
     *
     * @param resId
     */
    public void setErrorView(@LayoutRes int resId) {
        setErrorView(LayoutInflater.from(getContext()).inflate(resId, this, false));
    }


    /**
     * 设置错误视图
     *
     * @param view
     */
    public void setErrorView(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof LayoutParams) {
            ((LayoutParams) layoutParams).mStateFlag = ERROR;
        } else {
            layoutParams = new StateLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((LayoutParams) layoutParams).mStateFlag = ERROR;
        }
        view.setLayoutParams(layoutParams);
        this.mErrorView = view;
    }

    /**
     * 设置加载中的视图
     *
     * @param resId
     */
    public void setOnLoadingView(@LayoutRes int resId) {
        setOnLoadingView(LayoutInflater.from(getContext()).inflate(resId, this, false));
    }

    /**
     * 设置加载中的视图，通过ID
     *
     * @param resId
     */
    public void setOnLoadingViewById(@IdRes int resId) {
        setOnLoadingView(findViewById(resId));
    }

    /**
     * 设置加载中的视图
     *
     * @param view
     */
    public void setOnLoadingView(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof LayoutParams) {
            ((LayoutParams) layoutParams).mStateFlag = LOADING;
        } else {
            layoutParams = new StateLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((LayoutParams) layoutParams).mStateFlag = LOADING;
        }
        view.setLayoutParams(layoutParams);
        this.mOnLoadingView = view;
    }

    /**
     * 设置加载成功时的视图
     *
     * @param resId
     */
    public void setSuccessView(@LayoutRes int resId) {
        setSuccessView(LayoutInflater.from(getContext()).inflate(resId, this, false));
    }

    /**
     * 通过ID设置加载成功时的View
     *
     * @param resId
     */
    public void setSuccessViewById(@IdRes int resId) {
        setSuccessView(findViewById(resId));
    }

    /**
     * 设置加载成功时的View
     *
     * @param view
     */
    public void setSuccessView(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof LayoutParams) {
            ((LayoutParams) layoutParams).mStateFlag = SUCCESS;
        } else {
            layoutParams = new StateLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((LayoutParams) layoutParams).mStateFlag = SUCCESS;
        }
        view.setLayoutParams(layoutParams);
        this.mSuccessView = view;
    }

    /**
     * 设置状态
     *
     * @param newState
     */
    public void setState(@LoadState final int newState) {
        mHandler.removeCallbacksAndMessages(null);
        View newView = getViewByState(newState);
        if (newView == null) {
            return;
        }
        View viewState = getViewByState(this.mState);

        if (viewState != null && this.mState != newState) {
            viewState.clearAnimation();
        }
        removeAllViews();
        newView.clearAnimation();
        addView(newView);
        this.mState = newState;
    }

    /**
     * 延时更新状态
     *
     * @param newState
     * @param delayed
     */
    public void setStateDelayed(@LoadState final int newState, long delayed) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setState(newState);
            }
        }, delayed);
    }

    /**
     * 设置默认的视图状态
     *
     * @param state
     */
    public static void setDefaultState(int state) {
        DEFAULT_STATE = state;
    }

    /**
     * 根据state拿到View
     *
     * @param state
     * @return
     */
    public View getViewByState(@LoadState int state) {
        switch (state) {
            case NO_DATA:
                return mNoDataView;
            case LOADING:
                return mOnLoadingView;
            case SUCCESS:
                return mSuccessView;
            case ERROR:
                return mErrorView;
            case NO_NETWORK:
                return mNoNetworkView;
            default:
                return null;
        }
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Log.d("StateLayout", "onRestoreInstanceState");
        SavedState parcelable = (SavedState) state;
        super.onRestoreInstanceState(BaseSavedState.EMPTY_STATE);
        this.mState = parcelable.mState;
    }

    /**
     * 保存View的状态，返回的对象需要实现Parcelable接口并且继承自View#AbsSavedState类，
     * 并带有public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>()属性
     * 否则onRestoreInstanceState接受的数据无法转为对应的类型
     *
     * @return 返回值必须实现Parcelable接口并且继承自View#AbsSavedState类，否恢复状态异常
     */
    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        SavedState savedState = new SavedState(parcelable);
        savedState.mState = this.mState;
        return savedState;
    }

    public static final class SavedState extends BaseSavedState {

        int mState;

        protected SavedState(Parcelable superState) {
            super(superState);
        }

        protected SavedState(Parcel source) {
            super(source);
            mState = source.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(mState);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {

            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    /**
     * 得到当前的视图状态
     *
     * @return
     */
    public int getState() {
        return this.mState;
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public static class LayoutParams extends FrameLayout.LayoutParams {

        private int mStateFlag;

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(@NonNull Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.StateLayout_Layout);
            mStateFlag = array.getInt(R.styleable.StateLayout_Layout_view_state, 0);
            array.recycle();
        }

        public void setState(@LoadState int state) {
            this.mStateFlag = state;
        }

        public int getState() {
            return mStateFlag;
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        super.onInterceptTouchEvent(ev);
        return false;
    }
}
