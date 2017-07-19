package com.heima.googleplay.fragment;

import java.util.List;

import com.heima.googleplay.adapter.MyBaseAdapter;
import com.heima.googleplay.bean.SubjectInfo;
import com.heima.googleplay.holder.BaseHolder;
import com.heima.googleplay.holder.SubjectHolder;
import com.heima.googleplay.protocol.SubjectProtocol;
import com.heima.googleplay.utils.UIUtils;
import com.heima.googleplay.widget.BaseListView;
import com.heima.googleplay.widget.LoadingPage.LoadResult;

import android.view.View;
import android.widget.ListView;

public class SubjectFragment extends BaseFragment{

	private List<SubjectInfo> mDatas;

	@Override
	protected View createSuccessView() {
		BaseListView listView = new BaseListView(UIUtils.getContext());
		SubjectAdapter adapter = new SubjectAdapter(listView, mDatas);
		listView.setAdapter(adapter);
		return listView;
	}
	
	
	private class SubjectAdapter extends MyBaseAdapter<SubjectInfo>{

		public SubjectAdapter(ListView mListView, List<SubjectInfo> mDatas) {
			super(mListView, mDatas);
			// TODO Auto-generated constructor stub
		}

		@Override
		public BaseHolder getHolder() {
			// TODO Auto-generated method stub
			return new SubjectHolder();
		}

		@Override
		protected List onLoadMore() {
			SubjectProtocol protocol = new SubjectProtocol();
			return protocol.load(getData().size());
		}
		
	}

	@Override
	protected LoadResult load() {
		SubjectProtocol protocol = new SubjectProtocol();
		mDatas = protocol.load(0);
		return chece(mDatas);
	}

}
