package com.heima.googleplay.fragment;

import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.heima.googleplay.DetailActivity;
import com.heima.googleplay.adapter.BaseListAdapter;
import com.heima.googleplay.adapter.MyBaseAdapter;
import com.heima.googleplay.bean.AppInfo;
import com.heima.googleplay.bean.DownloadInfo;
import com.heima.googleplay.holder.BaseHolder;
import com.heima.googleplay.holder.HomeHolder;
import com.heima.googleplay.holder.HomePicHolder;
import com.heima.googleplay.manager.DownloadManager.DownloadObserver;
import com.heima.googleplay.protocol.HomeProtocol;
import com.heima.googleplay.utils.UIUtils;
import com.heima.googleplay.widget.BaseListView;
import com.heima.googleplay.widget.LoadingPage.LoadResult;

public class HomeFragment extends BaseFragment implements DownloadObserver {

	@Override
	public void onResume() {
		if (adapter != null) {
			adapter.registerDownloadStateChanged(this);
			adapter.notifyDataSetChanged();
		}

		super.onResume();

	}

	@Override
	public void onStop() {
		if (adapter != null) {
			adapter.unregisterDataSetObserver(this);

		}
		super.onStop();

	}

	@Override
	protected View createSuccessView() {
		BaseListView mListView = new BaseListView(UIUtils.getContext());
		// 判断当前的图片集合是否为null并且当前的图片大小是否大于0
		if (null != pictureUrl && pictureUrl.size() > 0) {
			HomePicHolder picHolder = new HomePicHolder();
			// 设置图片的地址
			picHolder.setData(pictureUrl);
			// 给listview添加一个头
			mListView.addHeaderView(picHolder.getRootView());
		}

		adapter = new HomeAdapter(mListView, mDatas);
		if (adapter != null) {
			adapter.registerDownloadStateChanged(this);
			adapter.notifyDataSetChanged();
		}
		mListView.setAdapter(adapter);
		return mListView;
	}

	private String url = "http://127.0.0.1:8090/home?index= 0";
	private List<AppInfo> mDatas;
	private List<String> pictureUrl;
	private HomeAdapter adapter;

	@Override
	protected LoadResult load() {

		HomeProtocol protocol = new HomeProtocol();
		mDatas = protocol.load(0);
		// 获取到轮播图的地址
		pictureUrl = protocol.getPictureUrl();
		return chece(mDatas);
	}

	private class HomeAdapter extends BaseListAdapter implements
			OnItemClickListener {

		public HomeAdapter(ListView mListView, List<AppInfo> mDatas) {
			super(mListView, mDatas);
		}

		@Override
		protected List onLoadMore() {
			HomeProtocol protocol = new HomeProtocol();
			return protocol.load(getData().size());
		}

	}

	static class ViewHolder extends BaseHolder<String> {
		TextView textView;

		@Override
		public View initView() {
			textView = new TextView(UIUtils.getContext());
			return textView;
		}

		@Override
		public void refreshView() {
			textView.setText(getData());

		}
	}

	@Override
	public void onRegisterDownloadStateChanged(DownloadInfo info) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRegisterDownloadProgressed(DownloadInfo info) {
		// TODO Auto-generated method stub

	}

}
