package study.toonan.com.myframework.adapter.base;

import android.content.Context;
import android.view.LayoutInflater;

import com.eugene.ebase.base.adapter.RecyclerAdapter;

import java.util.List;

import study.toonan.com.myframework.R;
import study.toonan.com.myframework.api.ThreadManager;
import study.toonan.com.myframework.util.UIUtils;

/**
 * Created by Administrator on 2017/2/7.
 */
public abstract class MyBaseAdapter<T> extends RecyclerAdapter<T> implements RecyclerAdapter.OnLoadMoreListener {

	public void setPageSize(int mPageSize) {
		this.mPageSize = mPageSize;
	}

	private static final int DEFAULT_PAGE_SIZE=10;
	protected int mPageSize=DEFAULT_PAGE_SIZE;

	public MyBaseAdapter(Context context, List<T> result) {
		this(context, result, DEFAULT_PAGE_SIZE);
	}

	public MyBaseAdapter(Context context, List<T> result, int pageSize) {
		this(context, result, result.size()>=pageSize);
		mPageSize =pageSize;
	}

	public MyBaseAdapter(Context context, List<T> result, boolean isLoadMore) {
		super(context, result);
		this.addLoadView(LayoutInflater.from(context).inflate(R.layout.item_footer, null));
		this.setOnLoadMoreListener(this);
		openLoadMore(isLoadMore);
	}

	private boolean isLoading = false;

	@Override
	public void onLoadMore() {
		if (!isLoading) {
			isLoading = true;
			ThreadManager.getLongPool().execute(new Runnable() {

				@Override
				public void run() {
					final List list = onLoadMoreData();
					UIUtils.runInMainThread(new Runnable() {

						@Override
						public void run() {
							if (null != list) {
								openLoadMore(list.size() >= mPageSize);
								if (null != result) {
									addMoreData(list);
								} else {
									setNewData(list);
								}
							}
							isLoading = false;
						}
					});
				}
			});
		}
	}

	protected abstract List onLoadMoreData();
}
