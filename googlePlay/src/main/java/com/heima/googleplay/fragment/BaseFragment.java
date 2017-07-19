package com.heima.googleplay.fragment;

import java.util.List;

import com.heima.googleplay.utils.UIUtils;
import com.heima.googleplay.utils.ViewUtils;
import com.heima.googleplay.widget.LoadingPage;
import com.heima.googleplay.widget.LoadingPage.LoadResult;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public abstract class BaseFragment extends Fragment {

	private LoadingPage mContentPage;
	
	
	
    //setContentView
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//当前的view已经有了一个父亲。必须移除
		if(mContentPage == null){
			mContentPage = new LoadingPage(UIUtils.getContext()) {

				@Override
				public View createSuccessView() {
					// TODO Auto-generated method stub
					return BaseFragment.this.createSuccessView();
				}

				@Override
				public LoadResult Load() {
					// TODO Auto-generated method stub
					return BaseFragment.this.load();
				}
			};
		}else{
			ViewUtils.removeSelfFromParent(mContentPage);
		}
		
		return mContentPage;
	}
    /**
     * 检查服务器返回的json数据
     * @param object
     * @return
     */
	protected LoadResult chece(Object object) {
		if (object == null) {
			return LoadResult.ERROR;
		}
		if (object instanceof List) {
			List list = (List) object;
			if (list.size() == 0) {
				return LoadResult.EMPTY;
			}
		}

		return LoadResult.SUCCESS;
	}

	protected abstract LoadResult load();

	protected abstract View createSuccessView();

	public void show() {
		if (null != mContentPage) {
			mContentPage.show();
		}

	}

}
