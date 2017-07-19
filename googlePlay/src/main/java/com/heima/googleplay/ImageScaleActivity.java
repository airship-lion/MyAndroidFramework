package com.heima.googleplay;

import java.util.List;


import com.bumptech.glide.Glide;
import com.heima.googleplay.http.HttpHelper;
import com.heima.googleplay.utils.UIUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

public class ImageScaleActivity extends Activity{

	private List<String> mDatas;
	private ViewPager pager;
	private int mPostion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.image_scale_activity);
		pager = (ViewPager) findViewById(R.id.pager);
		inData();
	}

	private void inData() {
		Intent intent = getIntent();
		if(null != intent){
			mDatas = intent.getStringArrayListExtra("imageUrls");
//			mPostion = intent.getStringExtra("position");
			mPostion = intent.getIntExtra("position", 0);
		}
		ImageScaleAdapter adapter = new ImageScaleAdapter();
		pager.setAdapter(adapter);
		//设置viewpager的位置
		pager.setCurrentItem(mPostion, false);
	}
	
	private class ImageScaleAdapter extends PagerAdapter{

		
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = View.inflate(UIUtils.getContext(), R.layout.item_image_scale, null);
			
//			ImageView image = (ImageView) view.findViewById(R.id.image);
//			Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" + mDatas.get(position)).into(image);
			
//			PhotoViewAttacher mAttacher = new PhotoViewAttacher(image);
//			mAttacher.update();
//			container.addView(image);
			
			return view;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mDatas.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}
		
	}

}
