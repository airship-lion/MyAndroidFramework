package com.heima.googleplay.holder;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.heima.googleplay.ImageScaleActivity;
import com.heima.googleplay.R;
import com.heima.googleplay.bean.AppInfo;
import com.heima.googleplay.http.HttpHelper;
import com.heima.googleplay.utils.UIUtils;

public class DetailScreenHolder extends BaseHolder<AppInfo> implements
		OnClickListener {

	private ImageView[] screens;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.app_detail_screen);

		screens = new ImageView[5];

		screens[0] = (ImageView) view.findViewById(R.id.screen_1);
		screens[1] = (ImageView) view.findViewById(R.id.screen_2);
		screens[2] = (ImageView) view.findViewById(R.id.screen_3);
		screens[3] = (ImageView) view.findViewById(R.id.screen_4);
		screens[4] = (ImageView) view.findViewById(R.id.screen_5);

		screens[0].setOnClickListener(this);
		screens[1].setOnClickListener(this);
		screens[2].setOnClickListener(this);
		screens[3].setOnClickListener(this);
		screens[4].setOnClickListener(this);

		return view;
	}

	@Override
	public void refreshView() {
		AppInfo appInfo = getData();

		for (int i = 0; i < 5; i++) {
			if (i < appInfo.getScreen().size()) {
				screens[i].setVisibility(View.VISIBLE);
				Glide.with(UIUtils.getContext())
						.load(HttpHelper.URL + "image?name="
								+ appInfo.getScreen().get(i)).into(screens[i]);
			} else {
				screens[i].setVisibility(View.GONE);
			}

		}

	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.screen_1:
//			enterImageScaleActivity(0);
//			break;
//
//		case R.id.screen_2:
//			enterImageScaleActivity(1);
//			break;
//		case R.id.screen_3:
//			enterImageScaleActivity(2);
//			break;
//		case R.id.screen_4:
//			enterImageScaleActivity(3);
//			break;
//		case R.id.screen_5:
//			enterImageScaleActivity(4);
//			break;
//		}

	}
    /**
     * 进入到图片缩放的页面
     * @param position 位置
     */
	private void enterImageScaleActivity(int position) {
		Intent intent = new Intent(UIUtils.getContext(),ImageScaleActivity.class);
		intent.putExtra("position", position);
		//传入一个集合
		intent.putStringArrayListExtra("imageUrls", (ArrayList<String>) getData().getScreen());
        UIUtils.startActivity(intent);
	}

}
