package com.heima.googleplay.adapter;

import java.util.List;

import android.content.Intent;
import android.widget.ListView;

import com.heima.googleplay.DetailActivity;
import com.heima.googleplay.bean.AppInfo;
import com.heima.googleplay.bean.DownloadInfo;
import com.heima.googleplay.fragment.HomeFragment;
import com.heima.googleplay.holder.BaseHolder;
import com.heima.googleplay.holder.HomeHolder;
import com.heima.googleplay.manager.DownloadManager;
import com.heima.googleplay.manager.DownloadManager.DownloadObserver;
import com.heima.googleplay.utils.UIUtils;

public abstract class BaseListAdapter extends MyBaseAdapter<AppInfo> implements
		DownloadObserver {

	public BaseListAdapter(ListView mListView, List<AppInfo> mDatas) {
		super(mListView, mDatas);
		// TODO Auto-generated constructor stub
	}

	@Override
	public BaseHolder getHolder() {
		// TODO Auto-generated method stub
		return new HomeHolder();
	}

	/**
	 * 注册一个观察者下载状态
	 * 
	 * @param homeFragment
	 */
	public void registerDownloadStateChanged(DownloadObserver observer) {
		DownloadManager.getInstance().registerDownloadStateChanged(observer);

	}

	public void unregisterDataSetObserver(DownloadObserver observer) {
		DownloadManager.getInstance().unRegisterDownloadStateChanged(observer);

	}

	@Override
	public void onInnerItemClick(int position) {
		Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
		String packageName = getData().get(position).getPackageName();
		intent.putExtra("packageName", packageName);
		UIUtils.startActivity(intent);
	}

	@Override
	public void onRegisterDownloadStateChanged(DownloadInfo info) {

		refreshState(info);
	}

	private void refreshState(final DownloadInfo info) {
		// 获取到所有界面可见的holder
		List<BaseHolder> displayHolderLists = getDisplayHolderLists();

		if (displayHolderLists != null) {

			for (final BaseHolder holder : displayHolderLists) {

				final AppInfo appInfo = (AppInfo) holder.getData();

				UIUtils.runInMainThread(new Runnable() {

					@Override
					public void run() {
						// 说明当前是我需要刷新的holder
						if (appInfo.getId() == info.getId()) {
							((HomeHolder) holder).refreshState(
									info.getDownloadState(), info.getProgress());
						}

					}
				});

			}

		}

	}

	@Override
	public void onRegisterDownloadProgressed(DownloadInfo info) {
		refreshState(info);

	}

	@Override
	protected List onLoadMore() {
		// TODO Auto-generated method stub
		return null;
	}

}
