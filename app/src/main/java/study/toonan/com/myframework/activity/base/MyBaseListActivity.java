package study.toonan.com.myframework.activity.base;

import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.toonan.com.myframework.R;
import study.toonan.com.myframework.adapter.base.PullToRefreshAdapter;
import study.toonan.com.myframework.bean.HttpResult;
import study.toonan.com.myframework.util.RecyclerViewUtil;

/**
 * Created by Administrator on 2016/8/26.
 */
public abstract class MyBaseListActivity<T> extends MyBaseActivity {
	@Bind(R.id.recyclerview)
	protected RecyclerView recyclerview;
	@Bind(R.id.swipelayout)
	protected SwipeRefreshLayout swipelayout;
	protected int currentPage = 0;
	protected List<T> mDatas;
	protected HttpResult<List<T>> httpResult;
	protected PullToRefreshAdapter adapter;

	@Override
	protected View createSuccessView() {
		View view = onCreateSuccessView();
		currentPage++;
		mDatas = httpResult.getResultList();
		ButterKnife.bind(this, view);
		adapter = onCreateAdapter();
		RecyclerViewUtil.setLinearAdapter(recyclerview, adapter);
		swipelayout.setColorSchemeResources(R.color.colorPrimary);
		swipelayout.setOnRefreshListener(adapter);
		return view;
	}

	protected abstract View onCreateSuccessView();
	protected abstract PullToRefreshAdapter onCreateAdapter();
	protected  HttpResult onLoadMoreDataSub(){
		return null;
	}

	@Nullable
	public List onLoadMoreData() {
		if (swipelayout.isRefreshing()) {
			swipelayout.setRefreshing(false);
		}
		HttpResult<List<T>> httpResult = onLoadMoreDataSub();
		if (httpResult != null) {
			currentPage++;
			return httpResult.getResultList();
		} else {
			return null;
		}
	}
}
