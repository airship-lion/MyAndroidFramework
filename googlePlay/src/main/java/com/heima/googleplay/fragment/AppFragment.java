package com.heima.googleplay.fragment;

import java.util.List;

import android.view.View;
import android.widget.ListView;

import com.heima.googleplay.adapter.BaseListAdapter;
import com.heima.googleplay.adapter.MyBaseAdapter;
import com.heima.googleplay.bean.AppInfo;
import com.heima.googleplay.holder.AppHolder;
import com.heima.googleplay.holder.BaseHolder;
import com.heima.googleplay.protocol.AppProtocol;
import com.heima.googleplay.utils.UIUtils;
import com.heima.googleplay.widget.BaseListView;
import com.heima.googleplay.widget.LoadingPage.LoadResult;

public class AppFragment extends BaseFragment {

	private List<AppInfo> mDatas;

	@Override
	protected View createSuccessView() {
		BaseListView listView = new BaseListView(UIUtils.getContext());
		AppAdapter adapter = new AppAdapter(listView,mDatas);
		listView.setAdapter(adapter);
		return listView;
	}

	@Override
	protected LoadResult load() {
		AppProtocol protocol = new AppProtocol();
		mDatas = protocol.load(0);
		return chece(mDatas);
	}
	
	private class AppAdapter extends BaseListAdapter{

		public AppAdapter(ListView mListView, List<AppInfo> mDatas) {
			super(mListView, mDatas);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected List onLoadMore() {
			AppProtocol protocol = new AppProtocol();
			return protocol.load(getData().size());
		}

	}

}
