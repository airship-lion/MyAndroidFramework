package study.toonan.com.myframework.activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.eugene.ebase.base.adapter.BaseRecyclerHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import study.toonan.com.myframework.R;
import study.toonan.com.myframework.activity.base.MyBaseListActivity;
import study.toonan.com.myframework.adapter.base.PullToRefreshAdapter;
import study.toonan.com.myframework.bean.HttpResult;
import study.toonan.com.myframework.bean.RentPatrol;
import study.toonan.com.myframework.net.NetManager;
import study.toonan.com.myframework.util.UIUtils;
import study.toonan.com.myframework.widget.LoadingPage;

public class RentPatrolListActivity extends MyBaseListActivity<RentPatrol> {

	protected double flag;

	protected LoadingPage.LoadResult load() {
		httpResult = new NetManager().appRentPatrolListSyn(getJzParam());
		if (flag < 1) {
			flag++;
			return LoadingPage.LoadResult.ERROR;
		} else {
			return new Random().nextBoolean() ? LoadingPage.LoadResult.SUCCESS : LoadingPage.LoadResult.EMPTY;
		}
//		 return ResultUtil.check(httpResult);
	}

	@Override
	protected View onCreateSuccessView() {
		View rootView = UIUtils.inflate(R.layout.fragment_common_list_no_title_bar);
		return rootView;
	}

	@Override
	protected PullToRefreshAdapter onCreateAdapter() {
		RentPatrolAdapter rentPatrolAdapter = new RentPatrolAdapter(UIUtils.getContext(), mDatas);
		//不能用headerView，用就报错，基本adapter没处理好position
//		TextView t1 = new TextView(this);
//		t1.setText("Header 1");
//		rentPatrolAdapter.addHeaderView(t1);
		return rentPatrolAdapter;
	}

	private Map getJzParam() {
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("jsonConditionStr", "{townId=%27440113102%27,streetId=%2781000012993039%27}");
		reqMap.put("changePage", (currentPage + 1) + "");
		return reqMap;
	}

	class RentPatrolAdapter extends PullToRefreshAdapter<RentPatrol> {

		public RentPatrolAdapter(Context context, List<RentPatrol> result) {
			super(context, result);
		}

		@Override
		public void onChangePageParams() {
			currentPage = 0;
		}

		@Override
		protected List onLoadMoreData() {
			return RentPatrolListActivity.this.onLoadMoreData();
		}

		@Override
		public int onCreateViewLayoutID(int i) {
			return R.layout.list_item2;
		}

		@Override
		public void onBindViewData(BaseRecyclerHolder baseRecyclerHolder, int position) {
			baseRecyclerHolder.setText(R.id.xiaoke_name, result.get(position).getVo().getAqjcfxwt());
		}

		@Override
		public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			BaseRecyclerHolder holder = super.onCreateViewHolder(parent, viewType);
			AutoUtils.autoSize(holder.getConvertView());
			return holder;
		}
	}

	@Override
	protected HttpResult onLoadMoreDataSub() {
		return new NetManager().appRentPatrolListSyn(getJzParam());
	}
}
