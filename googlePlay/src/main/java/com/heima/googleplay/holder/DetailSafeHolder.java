package com.heima.googleplay.holder;

import java.util.List;

import android.graphics.Color;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.heima.googleplay.R;
import com.heima.googleplay.bean.AppInfo;
import com.heima.googleplay.http.HttpHelper;
import com.heima.googleplay.utils.UIUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator;

public class DetailSafeHolder extends BaseHolder<AppInfo> implements
		OnClickListener {

	private RelativeLayout safe_layout;
	private LinearLayout safe_content;
	private ImageView safe_arrow;
	private ImageView[] ivs;
	private ImageView[] des_ivs;
	private LinearLayout[] des_layouts;
	private TextView[] des_tvs;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.app_detail_safe);

		safe_layout = (RelativeLayout) view.findViewById(R.id.safe_layout);

		safe_layout.setOnClickListener(this);

		safe_content = (LinearLayout) view.findViewById(R.id.safe_content);

		safe_arrow = (ImageView) view.findViewById(R.id.safe_arrow);
		// 设置当前箭头的标记
		// false表示向下
		// ture表示向上
		safe_arrow.setTag(false);

		// 进来内容页面默认的高度就是0
		safe_content.getLayoutParams().height = 0;
		
		
		ivs = new ImageView[4];
		
		ivs[0] = (ImageView) view.findViewById(R.id.iv_1);
		ivs[1] = (ImageView) view.findViewById(R.id.iv_2);
		ivs[2] = (ImageView) view.findViewById(R.id.iv_3);
		ivs[3] = (ImageView) view.findViewById(R.id.iv_4);
		
		
		
		des_ivs = new ImageView[4];
		des_ivs[0] = (ImageView) view.findViewById(R.id.des_iv_1);
		des_ivs[1] = (ImageView) view.findViewById(R.id.des_iv_2);
		des_ivs[2] = (ImageView) view.findViewById(R.id.des_iv_3);
		des_ivs[3] = (ImageView) view.findViewById(R.id.des_iv_4);
		
		
		des_tvs = new TextView[4];
		
		des_tvs[0] = (TextView) view.findViewById(R.id.des_tv_1);
		des_tvs[1] = (TextView) view.findViewById(R.id.des_tv_2);
		des_tvs[2] = (TextView) view.findViewById(R.id.des_tv_3);
		des_tvs[3] = (TextView) view.findViewById(R.id.des_tv_4);
		
		
		des_layouts = new LinearLayout[4];
		
		des_layouts[0] = (LinearLayout) view.findViewById(R.id.des_layout_1);
		des_layouts[1] = (LinearLayout) view.findViewById(R.id.des_layout_2);
		des_layouts[2] = (LinearLayout) view.findViewById(R.id.des_layout_3);
		des_layouts[3] = (LinearLayout) view.findViewById(R.id.des_layout_4);

		return view;
	}

	@Override
	public void refreshView() {
		AppInfo info = getData();
		//对应着官方。安全。无广告等的图片下载地址
		List<String> safeUrl = info.getSafeUrl();
		//小框框打勾的下载地址
		List<String> safeDesUrl = info.getSafeDesUrl();
		//小框框打勾后面的描述信息
		List<String> safeDes = info.getSafeDes();
		//描述的文字颜色，有广告的颜色比较醒目
		List<Integer> safeDesColor = info.getSafeDesColor();

		for (int i = 0; i < 4; i++) {
			if (i < safeUrl.size() && i < safeDesUrl.size() && i < safeDes.size() && i < safeDesColor.size()) {
//				ImageLoader.load(mIv[i], safeUrl.get(i));
//				ImageLoader.load(mDesIv[i], safeDesUrl.get(i));
				
				Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" + safeUrl.get(i)).into(ivs[i]);
				Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" + safeDesUrl.get(i)).into(des_ivs[i]);
				des_tvs[i].setText(safeDes.get(i));

				int color;
				int colorType = safeDesColor.get(i);
				if (colorType >= 1 && colorType <= 3) {
					color = Color.rgb(255, 153, 0);
				} else if (colorType == 4) {
					color = Color.rgb(0, 177, 62);
				} else {
					color = Color.rgb(122, 122, 122);
				}
				des_tvs[i].setTextColor(color);

				ivs[i].setVisibility(View.VISIBLE);
				des_layouts[i].setVisibility(View.VISIBLE);
			} else {
				ivs[i].setVisibility(View.GONE);
				des_layouts[i].setVisibility(View.GONE);
			}
		}
		
	}

	@Override
	public void onClick(View v) {
		// 点击完毕之后。展开内容页面。箭头也要跟着变化
		switch (v.getId()) {
		case R.id.safe_layout:
			expand();
			break;

		}

	}

	private void expand() {
		final LayoutParams params = safe_content.getLayoutParams();
		int targetHeight;
		int height = safe_content.getMeasuredHeight();
		boolean flag = (Boolean) safe_arrow.getTag();
		if (flag) {
			safe_arrow.setTag(false);
			targetHeight = 0;
		} else {
			safe_arrow.setTag(true);
			targetHeight = measureContentHeight();
		}

		safe_layout.setEnabled(false);

		ValueAnimator va = ValueAnimator.ofInt(height, targetHeight);
		va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator va) {
				params.height = (Integer) va.getAnimatedValue();
				safe_content.setLayoutParams(params);
			}
		});
		va.addListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator arg0) {

			}

			@Override
			public void onAnimationRepeat(Animator arg0) {

			}

			@Override
			public void onAnimationEnd(Animator arg0) {
				boolean flag = (Boolean) safe_arrow.getTag();
				safe_arrow.setImageResource(flag ? R.drawable.arrow_up : R.drawable.arrow_down);
				safe_layout.setEnabled(true);
			}

			@Override
			public void onAnimationCancel(Animator arg0) {

			}
		});
		va.setDuration(500);
		va.start();
		
	}
    /**
     * 测量内容的高度
     * @return
     */
	private int measureContentHeight() {
		
		int width = safe_content.getMeasuredWidth();
		safe_content.getLayoutParams().height = LayoutParams.WRAP_CONTENT;
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
		int heightMeasureSpec = MeasureSpec.makeMeasureSpec(22222222, MeasureSpec.AT_MOST);
		safe_content.measure(widthMeasureSpec, heightMeasureSpec);
		return safe_content.getMeasuredHeight();
	}

}
