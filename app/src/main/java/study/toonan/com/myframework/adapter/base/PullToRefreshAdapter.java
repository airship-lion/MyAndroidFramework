package study.toonan.com.myframework.adapter.base;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import java.util.List;

/**
 * Created by Administrator on 2017/2/7.
 */
public abstract class PullToRefreshAdapter<T> extends MyBaseAdapter<T> implements SwipeRefreshLayout.OnRefreshListener {
	public PullToRefreshAdapter(Context context, List<T> result) {
		super(context, result);
	}

	public PullToRefreshAdapter(Context context, List<T> result, int pageSize) {
		super(context, result, pageSize);
	}

	public PullToRefreshAdapter(Context context, List<T> result, boolean isLoadMore) {
		super(context, result, isLoadMore);
	}

	public abstract void onChangePageParams();

	@Override
	public void onRefresh() {
		onChangePageParams();
		result.clear();
		onLoadMore();
	}
}
