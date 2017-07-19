package com.heima.googleplay.adapter;



import java.util.ArrayList;
import java.util.List;

import com.heima.googleplay.holder.BaseHolder;
import com.heima.googleplay.holder.MoreHolder;
import com.heima.googleplay.manager.ThreadManager;
import com.heima.googleplay.utils.UIUtils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.RecyclerListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

public abstract class MyBaseAdapter<T> extends BaseAdapter implements
		RecyclerListener, OnItemClickListener {

	public ListView mListView;
	
	
	public List<BaseHolder> getDisplayHolderLists(){
		return mDisplayHolderLists;
	}

	public MyBaseAdapter(ListView mListView, List<T> mDatas) {
		this.mListView = mListView;
		// 初始化一个界面可见的holder
		mDisplayHolderLists = new ArrayList<BaseHolder>();

		if (null != mListView) {
			// 设置listview的回收监听
			mListView.setRecyclerListener(this);
			//设置listview的item选中监听
			mListView.setOnItemClickListener(this);
		}
		setData(mDatas);
	}

	public List<T> mDatas;
	private BaseHolder holder;
	private List<BaseHolder> mDisplayHolderLists;

	public void setData(List<T> mDatas) {
		this.mDatas = mDatas;

	}

	public List<T> getData() {
		return mDatas;
	}

	@Override
	public int getCount() {
		// +1 表示添加一个特殊的条目
		return mDatas.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	// 表示加载更多
	private final int MORE_ITEM_TYPE = 0;
	// 表示普通的数据类型
	private final int ITEM_VIEW_TYPE = 1;
	private MoreHolder moreHolder;

	/**
	 * 获取到item的类型
	 * 
	 * @param position
	 * @return
	 */
	@Override
	public int getItemViewType(int position) {
		// 判断当前是否是最后一个条目
		if (position == getCount() - 1) {
			return MORE_ITEM_TYPE;
		} else {
			return getInnerItemViewType(position);
		}
	}

	public int getInnerItemViewType(int position) {
		// TODO Auto-generated method stub
		return ITEM_VIEW_TYPE;
	}

	/**
	 * 获取到一共有多少中数据类型
	 * 
	 * @return
	 */
	@Override
	public int getViewTypeCount() {
		return super.getViewTypeCount() + 1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView != null) {
			holder = (BaseHolder) convertView.getTag();
		} else {
			if (getItemViewType(position) == MORE_ITEM_TYPE) {
				holder = getMoreHolder();
			} else {
				holder = getHolder();
			}

		}
		if (getItemViewType(position) == ITEM_VIEW_TYPE) {
			holder.setData(mDatas.get(position));
		}

		// 把所有的holder全部丢入mDisplayHolderLists
		mDisplayHolderLists.add(holder);
		return holder.getRootView();
	}
    /**
     * 获取更多的holder
     * @return
     */
	private BaseHolder getMoreHolder() {
		if(moreHolder == null){
			moreHolder = new MoreHolder(hasMore(),this);
		}
		
		return moreHolder;
	}
    /**
     * 表示有更多的数据
     * @return
     */
	public boolean hasMore() {
		// TODO Auto-generated method stub
		return true;
	}

	public abstract BaseHolder getHolder();

	/**
	 * 当listview回收的时候调用当前的方法
	 */
	@Override
	public void onMovedToScrapHeap(View view) {
		System.out.println("我被回收了.....");
		if (null != view) {
			BaseHolder holder = (BaseHolder) view.getTag();
			if (null != holder) {
				synchronized (mDisplayHolderLists) {
					mDisplayHolderLists.remove(holder);
				}

			}
		}

	}
	private boolean isLoading = false;
    /**
     * 加载更多
     */
	public void loadMore() {
		if(!isLoading){
			isLoading = true;
			ThreadManager.getLongPool().execute(new Runnable() {
				
				@Override
				public void run() {
					final List list =  onLoadMore();
					UIUtils.runInMainThread(new Runnable() {
						
						@Override
						public void run() {
							if(null == list){
								//如果服务器返回的数据等于null。那么设置一个错误状态
								getMoreHolder().setData(MoreHolder.ERROR);
							}else if(list.size() < 20){
								//如果服务器返回的数据小于20条。那么设置没有更多数据的状态
								getMoreHolder().setData(MoreHolder.NO_MORE);
							}else{
								getMoreHolder().setData(MoreHolder.HAS_MORE);
							}
							
							if(null != list){
								if(null !=mDatas){
									mDatas.addAll(list);
								}else{
									setData(list);
								}
							}
							//刷新界面
							notifyDataSetChanged();
							isLoading = false;
						}
					});
					
				}
			});
		}
		
		
		
	}

	protected abstract List onLoadMore();

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		position =  position - getListViewHeadCount(position);
		onInnerItemClick(position);
		
	}

	public void onInnerItemClick(int position){
		
	}

	public int getListViewHeadCount(int position) {

		int count = 0;
		
        if(null != mListView){
        	//判断listview是否有头
        	if(mListView.getHeaderViewsCount() > 0){
        		count = mListView.getHeaderViewsCount();
        	}
        	
        }
		return count;
	}

}
