package com.heima.googleplay.holder;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.heima.googleplay.R;
import com.heima.googleplay.http.HttpHelper;
import com.heima.googleplay.utils.UIUtils;
import com.heima.googleplay.widget.IndicatorView;

public class HomePicHolder extends BaseHolder<List<String>> implements OnPageChangeListener {

	private List<String> mPics;
	private ViewPager mViewPager;
	private AutoPlayTask task;
	private IndicatorView mIndicatorView;

	@Override
	public View initView() {
		// 初始化头
		RelativeLayout mHeadView = new RelativeLayout(UIUtils.getContext());
		// 初始化头的宽和高
		AbsListView.LayoutParams rl = new AbsListView.LayoutParams(
				LayoutParams.MATCH_PARENT,
				UIUtils.getDimens(R.dimen.list_header_hight));
		// 设置头的宽和高
		mHeadView.setLayoutParams(rl);

		// 初始化轮播图片

		mViewPager = new ViewPager(UIUtils.getContext());
		// 初始化轮播图的宽和高
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		// 设置轮播图的宽和高
		mViewPager.setLayoutParams(params);

		HomePicAdapter adapter = new HomePicAdapter();

		mViewPager.setAdapter(adapter);
		//初始化点
		
		mIndicatorView = new IndicatorView(UIUtils.getContext());
		//初始化点的宽和高
		params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		//设置到屏幕的右边
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		//设置到屏幕的下边
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		
		//设置点的宽和高
		mIndicatorView.setLayoutParams(params);
		//设置点的背景图片
		mIndicatorView.setIndicatorDrawable(UIUtils.getDrawable(R.drawable.indicator));
		//设置间隔
		mIndicatorView.setInterval(5);
		//设置间距
		mIndicatorView.setPadding(0, 0, 20, 20);
		//默认点选中第0个
		mIndicatorView.setSelection(0);

		task = new AutoPlayTask();

		// 当手指触摸屏幕的时候调用当前的方法
		mViewPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					task.stop();
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					task.start();
				}

				return false;
			}
		});
		
		mViewPager.setOnPageChangeListener(this);
		
        //需要注意。在添加点的时候。先添加的在下面。后添加的在上面
		mHeadView.addView(mViewPager);
		
		mHeadView.addView(mIndicatorView);

		return mHeadView;
	}

	private class AutoPlayTask implements Runnable {
		// 每隔5秒钟跳动一次
		final int AUTO_PLAY_TIME = 5000;

		boolean isAutoPlay = false;

		@Override
		public void run() {
			if (isAutoPlay) {
				mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1,
						false);
				UIUtils.postDelayed(this, AUTO_PLAY_TIME);
			}

		}

		public void stop() {
			if(isAutoPlay){
				isAutoPlay = false;
				UIUtils.removeCallbacks(this);
			}
			

		}

		public void start() {
			if (!isAutoPlay) {
				isAutoPlay = true;
				UIUtils.removeCallbacks(this);
				UIUtils.postDelayed(this, AUTO_PLAY_TIME);
			}

		}

	}

	private class HomePicAdapter extends PagerAdapter {

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			ImageView imageView = new ImageView(UIUtils.getContext());
			// 设置图片全屏
			imageView.setScaleType(ScaleType.FIT_XY);

			int location = position % mPics.size();

			Glide.with(UIUtils.getContext())
					.load(HttpHelper.URL + "image?name=" + mPics.get(location))
					.into(imageView);

			container.addView(imageView);

			return imageView;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

	}

	@Override
	public void refreshView() {
		mPics = getData();
		// 设置到中间
		mViewPager.setCurrentItem(mPics.size() * 100);
		//设置总共有多少个点
		mIndicatorView.setCount(mPics.size());
		// 开始跳动
		task.start();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		mIndicatorView.setSelection(position % mPics.size());
		
	}

}
