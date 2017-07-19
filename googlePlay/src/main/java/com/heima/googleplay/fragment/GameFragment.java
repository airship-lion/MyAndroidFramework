package com.heima.googleplay.fragment;

import java.util.List;

import android.view.View;
import android.widget.ListView;

import com.heima.googleplay.adapter.BaseListAdapter;
import com.heima.googleplay.adapter.MyBaseAdapter;
import com.heima.googleplay.bean.AppInfo;
import com.heima.googleplay.holder.BaseHolder;
import com.heima.googleplay.holder.GameHolder;
import com.heima.googleplay.protocol.GameProtocol;
import com.heima.googleplay.utils.UIUtils;
import com.heima.googleplay.widget.BaseListView;
import com.heima.googleplay.widget.LoadingPage.LoadResult;

public class GameFragment extends BaseFragment {

	private List<AppInfo> mDatas;


	@Override
	protected View createSuccessView() {
		BaseListView listView = new BaseListView(UIUtils.getContext());
		GameAdapter adapter = new GameAdapter(listView,mDatas);
		listView.setAdapter(adapter);
		return listView;
	}

	@Override
	protected LoadResult load() {
		GameProtocol protocol = new GameProtocol();
		mDatas = protocol.load(0);
		return chece(mDatas);
	}

	
	private class GameAdapter extends BaseListAdapter{

		public GameAdapter(ListView mListView, List<AppInfo> mDatas) {
			super(mListView, mDatas);
		}

		@Override
		protected List onLoadMore() {
			GameProtocol protocol = new GameProtocol();
			return protocol.load(getData().size());
		}

		
	}
}
