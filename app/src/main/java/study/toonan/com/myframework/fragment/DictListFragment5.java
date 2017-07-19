package study.toonan.com.myframework.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.toonan.com.myframework.R;
import study.toonan.com.myframework.bean.DictBean;
import study.toonan.com.myframework.bean.HttpResult;
import study.toonan.com.myframework.net.NetManager;
import study.toonan.com.myframework.util.RecyclerViewUtil;
import study.toonan.com.myframework.util.UIUtils;
import study.toonan.com.myframework.widget.LoadingPage;

/**
 * Created by Administrator on 2017/1/20.
 */
public class DictListFragment5 extends BaseFragment {
	@Bind(R.id.recyclerview)
	RecyclerView recyclerview;
	@Bind(R.id.swipelayout)
	SwipeRefreshLayout swipelayout;
	protected HttpResult<List<DictBean>> result;
	protected List<DictBean> mDatas;

	@Override
	protected LoadingPage.LoadResult load() {
		result = new NetManager().appXtzdListSyn(getJzParam());
		return check(result);
	}

	private Map getJzParam() {
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("jsonConditionStr", "{dmlx='FWXJLX,FWSFLX'}");
		return reqMap;
	}

	@Override
	protected View createSuccessView() {
		mDatas = result.getResultList();
		View rootView = UIUtils.inflate(R.layout.fragment_common_list_no_title_bar);
		ButterKnife.bind(this, rootView);
		RecyclerViewUtil.setLinearAdapter(recyclerview, new Adapter());
//		RecyclerAdapter adapter = new RecyclerAdapter<Bean>(UIUtils.getContext(), mDatas) {
//			@Override
//			public int onCreateViewLayoutID(int i) {
//				return R.layout.list_item;
//			}
//
//			@Override
//			public void onBindViewData(BaseRecyclerHolder baseRecyclerHolder, final int position) {
//				baseRecyclerHolder.setText(R.id.tv_title, result.get(position).getName());
//			}
//
//		};
		return rootView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	class Adapter extends RecyclerView.Adapter<ViewHolder>{

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			return new ViewHolder(UIUtils.inflate(R.layout.list_item));
		}

		@Override
		public void onBindViewHolder(ViewHolder holder, int position) {
			holder.tvTitle.setText(mDatas.get(position).getMc());
		}

		@Override
		public int getItemCount() {
			return mDatas.size();
		}
	}

	static class ViewHolder extends RecyclerView.ViewHolder {
		@Bind(R.id.tv_title)
		TextView tvTitle;

		ViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}

	}
}
