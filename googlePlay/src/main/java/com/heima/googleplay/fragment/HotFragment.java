package com.heima.googleplay.fragment;

import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.heima.googleplay.protocol.HotProtocol;
import com.heima.googleplay.utils.DrawableUtils;
import com.heima.googleplay.utils.UIUtils;
import com.heima.googleplay.widget.FlowLayout;
import com.heima.googleplay.widget.LoadingPage.LoadResult;

public class HotFragment extends BaseFragment {

	private List<String> mDatas;

	@Override
	protected View createSuccessView() {
		
		ScrollView scrollView = new ScrollView(UIUtils.getContext());
		
		FlowLayout mFlowLayout = new FlowLayout(UIUtils.getContext());
		
		int mVerticalSpacing = UIUtils.dip2px(4);
		
		int mHorizontalSpacing = UIUtils.dip2px(7);
		
		int padding = UIUtils.dip2px(13);
		
		mFlowLayout.setPadding(padding, padding, padding, padding);
		
		//设置纵轴的间距
		mFlowLayout.setVerticalSpacing(mVerticalSpacing);
		//设置横着的间距
		mFlowLayout.setHorizontalSpacing(mHorizontalSpacing);
		
		
		Random random = new Random();
		
		for (int i = 0; i < mDatas.size(); i++) {
			
			int red = 20 + random.nextInt(220);
			int green = 20 + random.nextInt(220);
			int blue = 20 + random.nextInt(220);
			
			int color =  Color.rgb(red, green, blue);
			
			
			TextView textView = new TextView(UIUtils.getContext());
			//设置字体居中
			textView.setGravity(Gravity.CENTER);
			//设置字体大小。如果当前的控件是包裹内容的话。那么必须使用dp
			textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
			
			int dip2px = UIUtils.dip2px(20);
			
			textView.setPadding(dip2px, dip2px, dip2px, dip2px);
			
			//设置字体颜色
			textView.setTextColor(Color.WHITE);
			
			//创建一张图片
			GradientDrawable createDrawable = DrawableUtils.createDrawable(color, color, 5);
			
			//设置背景图片
			textView.setBackgroundDrawable(createDrawable);
			
			textView.setText(mDatas.get(i));
			//不要使用下面这个方法
//			textView.setBackground(createDrawable);
			
			
			mFlowLayout.addView(textView);
		}
		
		
		
		
		scrollView.addView(mFlowLayout);
		
		
		return scrollView;
	}

	@Override
	protected LoadResult load() {
		HotProtocol protocol = new HotProtocol();
		mDatas = protocol.load(0);
		return chece(mDatas);
	}

}
