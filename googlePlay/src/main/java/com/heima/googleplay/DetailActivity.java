package com.heima.googleplay;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.heima.googleplay.bean.AppInfo;
import com.heima.googleplay.holder.DetailInfoHolder;
import com.heima.googleplay.holder.DetailSafeHolder;
import com.heima.googleplay.holder.DetailScreenHolder;
import com.heima.googleplay.protocol.DetailProtocol;
import com.heima.googleplay.utils.UIUtils;
import com.heima.googleplay.widget.LoadingPage;
import com.heima.googleplay.widget.LoadingPage.LoadResult;

public class DetailActivity extends BaseActivity {

	private String packageName;
	private AppInfo appInfo;

	@Override
	protected void initActionbar() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initView() {
		LoadingPage mContent = new LoadingPage(UIUtils.getContext()) {

			@Override
			public View createSuccessView() {
				// TODO Auto-generated method stub
				return DetailActivity.this.createSuccessView();
			}

			@Override
			public LoadResult Load() {
				// TODO Auto-generated method stub
				return DetailActivity.this.Load();
			}
		};
		setContentView(mContent);
		mContent.show();
	}

	protected LoadResult Load() {
		DetailProtocol protocol = new DetailProtocol();
		protocol.setPackageName(packageName);
		appInfo = protocol.load(0);
		if (null == appInfo) {
			return LoadResult.ERROR;
		}

		return LoadResult.SUCCESS;
	}

	protected View createSuccessView() {
		View view = UIUtils.inflate(R.layout.activity_detail);
		
		// 详情的基本信息
		
		FrameLayout detail_info = (FrameLayout) view.findViewById(R.id.detail_info);
		
		DetailInfoHolder detailInfoHolder = new DetailInfoHolder();
		
		detailInfoHolder.setData(appInfo);
		
		detail_info.addView(detailInfoHolder.getRootView());
		
		//安全界面
		
		FrameLayout detail_safe = (FrameLayout) view.findViewById(R.id.detail_safe);
		
		DetailSafeHolder detailSafeHolder = new DetailSafeHolder();
		
		detailSafeHolder.setData(appInfo);
		
		detail_safe.addView(detailSafeHolder.getRootView());
		
		//横着滚动的图片
		
		HorizontalScrollView detail_screen = (HorizontalScrollView) view.findViewById(R.id.detail_screen);
		
		DetailScreenHolder detailScreenHolder = new DetailScreenHolder();
		
		detailScreenHolder.setData(appInfo);
		
		detail_screen.addView(detailScreenHolder.getRootView());
		
		
		return view;
	}

	@Override
	protected void init() {
		Intent intent = getIntent();
		if (intent != null) {
			packageName = intent.getStringExtra("packageName");
			System.out.println("packageName---" + packageName);
		}
	}

}
