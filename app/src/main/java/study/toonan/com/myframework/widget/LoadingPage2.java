package study.toonan.com.myframework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.pnikosis.materialishprogress.ProgressWheel;

import cn.pedant.SweetAlert.ProgressHelper;
import rx.Observable;
import rx.Subscriber;
import study.toonan.com.myframework.R;
import study.toonan.com.myframework.api.ThreadManager;
import study.toonan.com.myframework.bean.HttpResult;
import study.toonan.com.myframework.util.UIUtils;

public abstract class LoadingPage2<T> extends FrameLayout {
    //默认状态
    private final int UN_LOADING = 1;
    //加载状态
    private final int LOADING = 2;
    //加载失败状态
    private final int ERROR = 3;
    //加载成功。然后服务器没有返回数据
    private final int EMPTY = 4;
    //加载成功的状态
    private final int SUCCESS = 5;
    //用来记录某种状态
    private int mState;
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;

    private View mSuccessView;

    public LoadingPage2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        //首先赋值
        mState = UN_LOADING;

        mLoadingView = createLoadingView();

        if (null != mLoadingView) {
            addView(mLoadingView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }

        mErrorView = createErrorView();

        if (null != mErrorView) {
            addView(mErrorView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }

        mEmptyView = createEmptyView();

        if (null != mEmptyView) {
            addView(mEmptyView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }

        showSafePage();

    }

    private void showSafePage() {
        UIUtils.runInMainThread(new Runnable() {

            @Override
            public void run() {
                showPage();

            }
        });

    }

    protected void showPage() {
        if (null != mLoadingView) {
            mLoadingView.setVisibility(mState == UN_LOADING || mState == LOADING ? View.VISIBLE : View.INVISIBLE);
        }

        if (null != mErrorView) {
            mErrorView.setVisibility(mState == ERROR ? View.VISIBLE : View.INVISIBLE);
        }

        if (null != mEmptyView) {
            mEmptyView.setVisibility(mState == EMPTY ? View.VISIBLE : View.INVISIBLE);
        }

        if (null == mSuccessView && mState == SUCCESS) {
            mSuccessView = createSuccessView();
            addView(mSuccessView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        if (null != mSuccessView) {
            mSuccessView.setVisibility(mState == SUCCESS ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public abstract View createSuccessView();

    private View createEmptyView() {
        return UIUtils.inflate(R.layout.loading_page_empty);
    }

    private View createErrorView() {
        View view = UIUtils.inflate(R.layout.loading_page_error);
        view.findViewById(R.id.page_bt).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
        return view;
    }

    private View createLoadingView() {
        View view = UIUtils.inflate(R.layout.loading_page_loading);
        ProgressHelper progressHelper=new ProgressHelper(UIUtils.getContext());
        progressHelper.setProgressWheel((ProgressWheel) view.findViewById(R.id.progressBar));
        progressHelper.setBarColor(UIUtils.getColor(R.color.colorPrimary));
        return view;
    }

    public LoadingPage2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingPage2(Context context) {
        super(context);
        init();
    }

    public enum LoadResult{
        ERROR(3),EMPTY(4),SUCCESS(5);
        int value ;
        LoadResult(int value){
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }

    public T getDatas() {
        return mDatas;
    }

    protected T mDatas;

    private class LoadTask implements Runnable {

        @Override
        public void run() {
            final Observable<HttpResult<T>> result = Load();
            result.subscribe(new Subscriber<HttpResult<T>>() {


                @Override
                public void onCompleted() {
                    showSafePage();
                }

                @Override
                public void onError(Throwable e) {
                    mState=LoadResult.ERROR.getValue();
                    showSafePage();
                }

                @Override
                public void onNext(HttpResult<T> httpResult) {
                    if (httpResult != null && httpResult.isResult()) {
                        mDatas = httpResult.getResultList();
                        mState=LoadResult.SUCCESS.getValue();
                    }else {
                        mState=LoadResult.ERROR.getValue();
                    }
                }
            });

        }

    }

    public void show() {
        if (mState == ERROR || mState == EMPTY) {
            mState = UN_LOADING;
        }

        if (mState == UN_LOADING) {
            mState = LOADING;

            LoadTask task = new LoadTask();
            ThreadManager.getLongPool().execute(task);
        }
        showSafePage();
    }

    public abstract Observable Load();


}
