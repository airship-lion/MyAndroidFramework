package com.heima.googleplay.fragment;

import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.heima.googleplay.protocol.RecommendProtocol;
import com.heima.googleplay.randomLayout.StellarMap;
import com.heima.googleplay.randomLayout.StellarMap.Adapter;
import com.heima.googleplay.utils.UIUtils;
import com.heima.googleplay.widget.LoadingPage.LoadResult;

public class RecommentFragment extends BaseFragment {

	private List<String> mDatas;

	@Override
	protected View createSuccessView() {
		StellarMap map = new StellarMap(UIUtils.getContext());
		//设置x轴和y轴的规则
		map.setRegularity(6, 9);
		int padding = UIUtils.dip2px(13);
		//设置左上右下
		map.setInnerPadding(padding, padding, padding, padding);
		map.setAdapter(new StellarMapAdapter());
		//从第0组开始加载。设置动画
		map.setGroup(0, true);
		
		return map;
	}

	private class StellarMapAdapter implements Adapter{
		
		
		
        private Random mRandom;

		public StellarMapAdapter() {
			mRandom = new Random();
		}
		/**
         * 一共有多少组数据
         */
		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return 2;
		}
        /**
         * 每一组有多少数据
         */
		@Override
		public int getCount(int group) {
			// TODO Auto-generated method stub
			return 15;
		}

		@Override
		public View getView(int group, int position, View convertView) {
			
			TextView textView = new TextView(UIUtils.getContext());
			
			int red = 20 + mRandom.nextInt(220);
			int green = 20 + mRandom.nextInt(220);
			int blue = 20 + mRandom.nextInt(220);
			//合成一种新的颜色
			int color =  Color.rgb(red, green, blue);
			
			//设置字体颜色
			textView.setTextColor(color);
			//设置字体大小
			textView.setTextSize(10 + mRandom.nextInt(15));
			
			textView.setText(mDatas.get(position));
			
			return textView;
		}

		@Override
		public int getNextGroupOnPan(int group, float degree) {
			// TODO Auto-generated method stub
			return (group + 1) % 2;
		}

		@Override
		public int getNextGroupOnZoom(int group, boolean isZoomIn) {
			// TODO Auto-generated method stub
			return (group + 1) % 2;
		}
		
	}
	
	@Override
	protected LoadResult load() {
		RecommendProtocol protocol = new RecommendProtocol();
		mDatas = protocol.load(0);
		return chece(mDatas);
	}

}
