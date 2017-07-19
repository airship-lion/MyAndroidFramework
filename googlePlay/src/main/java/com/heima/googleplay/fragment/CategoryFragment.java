package com.heima.googleplay.fragment;

import java.util.List;

import com.heima.googleplay.R;
import com.heima.googleplay.adapter.MyBaseAdapter;
import com.heima.googleplay.bean.CategoryInfo;
import com.heima.googleplay.holder.BaseHolder;
import com.heima.googleplay.holder.CategoryHolder;
import com.heima.googleplay.holder.CategoryTitleHolder;
import com.heima.googleplay.protocol.CategoryProtocol;
import com.heima.googleplay.utils.UIUtils;
import com.heima.googleplay.widget.BaseListView;
import com.heima.googleplay.widget.LoadingPage.LoadResult;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CategoryFragment extends BaseFragment{

	private List<CategoryInfo> mDatas;

	@Override
	protected View createSuccessView() {
		
		BaseListView listView = new BaseListView(UIUtils.getContext());
		
		CategoryAdapter adapter = new CategoryAdapter(listView,mDatas);
		
		listView.setAdapter(adapter);
		
		return listView;
	}
	
	private class CategoryAdapter extends MyBaseAdapter<CategoryInfo>{

		public CategoryAdapter(ListView mListView, List<CategoryInfo> mDatas) {
			super(mListView, mDatas);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			this.position = position;
			return super.getView(position, convertView, parent);
		}
		
        int position = 0;
		@Override
		public BaseHolder getHolder() {
			if(mDatas.get(position).isTitle()){
				return new CategoryTitleHolder();
			}else{
				return new CategoryHolder();
			}
		}
		
		@Override
		public int getInnerItemViewType(int position) {
			if(mDatas.get(position).isTitle()){
				return super.getInnerItemViewType(position) + 1;
			}else{
				return super.getInnerItemViewType(position);
			}
			
		}

		@Override
		public int getViewTypeCount() {
			// TODO Auto-generated method stub
			return super.getViewTypeCount() + 1;
		}

		@Override
		protected List onLoadMore() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public boolean hasMore() {
			// TODO Auto-generated method stub
			return false;
		}

		
		
	}

	@Override
	protected LoadResult load() {
		CategoryProtocol protocol = new CategoryProtocol();
		mDatas = protocol.load(0);
		return chece(mDatas);
	}

}
