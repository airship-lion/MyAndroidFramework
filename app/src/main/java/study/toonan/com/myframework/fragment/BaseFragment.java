package study.toonan.com.myframework.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import study.toonan.com.myframework.bean.HttpResult;
import study.toonan.com.myframework.util.ResultUtil;
import study.toonan.com.myframework.util.UIUtils;
import study.toonan.com.myframework.util.ViewUtils;
import study.toonan.com.myframework.widget.LoadingPage;
import study.toonan.com.myframework.widget.LoadingPage.LoadResult;

public abstract class BaseFragment extends Fragment {

	private LoadingPage mContentPage;

	// setContentView
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// 当前的view已经有了一个父亲。必须移除
		if (mContentPage == null) {
			mContentPage = new LoadingPage(UIUtils.getContext()) {

				@Override
				public View createSuccessView() {
					return BaseFragment.this.createSuccessView();
				}

				@Override
				public LoadResult Load() {
					return BaseFragment.this.load();
				}
			};
		} else {
			ViewUtils.removeSelfFromParent(mContentPage);
		}

		return mContentPage;
	}

	/**
	 * 检查服务器返回的数据
	 * 
	 * @param result
	 * @return
	 */
	protected LoadResult check(HttpResult result) {
		return ResultUtil.check(result);
	}

	protected abstract LoadResult load();

	protected abstract View createSuccessView();

	public void show() {
		if (null != mContentPage) {
			mContentPage.show();
		}

	}

}
