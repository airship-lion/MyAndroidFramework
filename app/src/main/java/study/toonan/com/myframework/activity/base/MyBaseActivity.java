package study.toonan.com.myframework.activity.base;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.pedant.SweetAlert.SweetAlertDialog;
import study.toonan.com.myframework.MyApplication;
import study.toonan.com.myframework.util.UIUtils;
import study.toonan.com.myframework.widget.LoadingPage;

/**
 * Created by Administrator on 2016/8/26.
 */
public abstract class MyBaseActivity extends AppCompatActivity {
	protected SweetAlertDialog progressDialog;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		MyApplication.activityList.add(this);
		LoadingPage mContentPage = new LoadingPage(UIUtils.getContext()) {

			@Override
			public View createSuccessView() {
				return MyBaseActivity.this.createSuccessView();
			}

			@Override
			public LoadResult Load() {
				return MyBaseActivity.this.load();
			}
		};
		setContentView(mContentPage);
		mContentPage.show();
	}

	protected abstract LoadingPage.LoadResult load();

	protected abstract View createSuccessView();


	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 结束Activity，从集合中移除
		MyApplication.getInstance().finishActivity(this);
	}

	public void showProgress() {
		showProgress(shouldShowProgress());
	}

	public void showProgress(boolean shouldShow) {
		if (shouldShow) {
			if (progressDialog == null) {
				progressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
				progressDialog.setTitleText("请稍后...");
				progressDialog.setCancelable(false);
			}
			progressDialog.show();
		}
	}

	public void hideProgress() {
		if (progressDialog != null) {
			progressDialog.hide();
		}
	}

	protected boolean shouldShowProgress() {
		return true;
	}

	public void showToast(String msg){
		MyApplication.getInstance().showToast(msg);
	}

	public void showLongToast(String msg) {
		MyApplication.getInstance().showLongToast(msg);
	}


}
