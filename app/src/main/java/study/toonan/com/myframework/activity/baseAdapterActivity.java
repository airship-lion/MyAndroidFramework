package study.toonan.com.myframework.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.ProgressHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import study.toonan.com.myframework.R;
import study.toonan.com.myframework.adapter.base.LoadMoreWrapper;
import study.toonan.com.myframework.bean.HttpResult;
import study.toonan.com.myframework.bean.RentPatrol;
import study.toonan.com.myframework.net.NetManager;
import study.toonan.com.myframework.util.RecyclerViewUtil;
import study.toonan.com.myframework.util.UIUtils;
import study.toonan.com.myframework.widget.LoadingPage;

/**
 * Created by Administrator on 2017/2/13.
 */
public class baseAdapterActivity extends AppCompatActivity {
	@Bind(R.id.recyclerview)
	RecyclerView recyclerview;
	@Bind(R.id.swipelayout)
	SwipeRefreshLayout swipelayout;
	protected int currentPage = 3;
	protected List<RentPatrol> mDatas;
	protected int totalPages;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_container);
		LoadingPage mContentPage = new LoadingPage(UIUtils.getContext()) {

			@Override
			public View createSuccessView() {
				return baseAdapterActivity.this.createSuccessView();
			}

			@Override
			public LoadResult Load() {
				return baseAdapterActivity.this.load();
			}
		};
		((FrameLayout) findViewById(R.id.fl_container)).addView(mContentPage, FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT);
		mContentPage.show();

	}

	private LoadingPage.LoadResult load() {
		HttpResult<List<RentPatrol>> listHttpResult = new NetManager().appRentPatrolListSyn(getJzParam());
		if (listHttpResult != null && listHttpResult.isResult()) {
			mDatas = listHttpResult.getResultList();
			totalPages = listHttpResult.getTotalPages();
			return LoadingPage.LoadResult.SUCCESS;
		}

		return LoadingPage.LoadResult.ERROR;
	}

	private View createSuccessView() {
		View view = UIUtils.inflate(R.layout.fragment_common_list_no_title_bar);
		ButterKnife.bind(this, view);
		afterLoadData();
		return view;
	}

	private void afterLoadData() {
		currentPage++;
		// mDatas = new ArrayList<RentPatrol>();
		final CommonAdapter<RentPatrol> adapter = new CommonAdapter<RentPatrol>(baseAdapterActivity.this,
				R.layout.list_item2, mDatas) {
			@Override
			protected void convert(ViewHolder holder, RentPatrol o, final int position) {
				holder.setText(R.id.xiaoke_name, o.getVo().getAqjcfxwt());
				holder.getView(R.id.xiaoke_name).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(baseAdapterActivity.this, mDatas.get(position).getVo().getAqjcfxwt(),
								Toast.LENGTH_SHORT).show();
					}
				});
			}

			@Override
			public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
				ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
				AutoUtils.autoSize(viewHolder.getConvertView());
				return viewHolder;
			}
		};
		// adapter.setOnItemClickListener(new
		// MultiItemTypeAdapter.OnItemClickListener() {
		// @Override
		// public void onItemClick(View view,
		// RecyclerView.ViewHolder holder, int position) {
		// Toast.makeText(baseAdapterActivity.this,
		// mDatas.get(position).getVo().getCjrMc(),
		// Toast.LENGTH_SHORT).show();
		// }
		//
		// @Override
		// public boolean onItemLongClick(View view,
		// RecyclerView.ViewHolder holder, int position) {
		// return false;
		// }
		// });
		EmptyWrapper emptyWrapper = new EmptyWrapper(adapter);
		emptyWrapper.setEmptyView(R.layout.loading_page_empty);
		// HeaderAndFooterWrapper mHeaderAndFooterWrapper = new
		// HeaderAndFooterWrapper(emptyWrapper);
		// TextView t1 = new TextView(baseAdapterActivity.this);
		// t1.setText("Header 1");
		// TextView t2 = new TextView(baseAdapterActivity.this);
		// t2.setText("Header 2");
		// TextView t3 = new TextView(baseAdapterActivity.this);
		// t3.setText("footer 1");
		// TextView t4 = new TextView(baseAdapterActivity.this);
		// t4.setText("footer 2");
		// mHeaderAndFooterWrapper.addHeaderView(t1);
		// mHeaderAndFooterWrapper.addHeaderView(t2);
		// mHeaderAndFooterWrapper.addFootView(t3);
		// mHeaderAndFooterWrapper.addFootView(t4);
		final LoadMoreWrapper mLoadMoreWrapper = new LoadMoreWrapper(emptyWrapper);
		View view = UIUtils.inflate(R.layout.adapter_loading);
		ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		view.setLayoutParams(layoutParams);
		ProgressHelper progressHelper = new ProgressHelper(UIUtils.getContext());
		progressHelper.setProgressWheel((ProgressWheel) view.findViewById(R.id.progressBar));
		progressHelper.setBarColor(UIUtils.getColor(R.color.colorPrimary));
		mLoadMoreWrapper.setLoadMoreView(view);
		mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
			@Override
			public void onLoadMoreRequested() {
				getData(mLoadMoreWrapper);
			}
		});
		swipelayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				currentPage = 1;
				mDatas.clear();
				getData(mLoadMoreWrapper);
			}
		});
		if (currentPage > totalPages) {
			mLoadMoreWrapper.setLoadMoreEnabled(false);
		} else {
			mLoadMoreWrapper.setLoadMoreEnabled(true);
		}
		RecyclerViewUtil.setLinearAdapter(recyclerview, mLoadMoreWrapper);

	}

	private void getData(final LoadMoreWrapper mLoadMoreWrapper) {
		new NetManager().appRentPatrolList(getJzParam(), new Callback<HttpResult<List<RentPatrol>>>() {
			@Override
			public void onResponse(Call<HttpResult<List<RentPatrol>>> call,
					Response<HttpResult<List<RentPatrol>>> response) {
				swipelayout.setRefreshing(false);
				if (response != null && response.body().isResult()) {
					currentPage++;
					mDatas.addAll(response.body().getResultList());
					mLoadMoreWrapper.notifyDataSetChanged();
					if (currentPage > response.body().getTotalPages()) {
						mLoadMoreWrapper.setLoadMoreEnabled(false);
					} else {
						mLoadMoreWrapper.setLoadMoreEnabled(true);
					}
				}

			}

			@Override
			public void onFailure(Call<HttpResult<List<RentPatrol>>> call, Throwable t) {
				Toast.makeText(baseAdapterActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
				swipelayout.setRefreshing(false);

			}
		});
	}

	private Map getJzParam() {
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("jsonConditionStr", "{townId=%27440113102%27,streetId=%2781000012993039%27}");
		reqMap.put("changePage", currentPage + "");
		return reqMap;
	}
}
