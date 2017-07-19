package com.heima.googleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.heima.googleplay.R;
import com.heima.googleplay.bean.AppInfo;
import com.heima.googleplay.http.HttpHelper;
import com.heima.googleplay.utils.StringUtils;
import com.heima.googleplay.utils.UIUtils;

public class GameHolder extends BaseHolder<AppInfo> {

	private ImageView item_icon;
	private TextView item_title;
	private RatingBar item_rating;
	private TextView item_size;
	private TextView item_bottom;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.list_item);
		item_icon = (ImageView) view.findViewById(R.id.item_icon);
		item_title = (TextView) view.findViewById(R.id.item_title);
		item_rating = (RatingBar) view.findViewById(R.id.item_rating);
		item_size = (TextView) view.findViewById(R.id.item_size);
		item_bottom = (TextView) view.findViewById(R.id.item_bottom);
		return view;
	}

	@Override
	public void refreshView() {
		AppInfo appInfo = getData();
		Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" + appInfo.getIconUrl()).into(item_icon);
		item_title.setText(appInfo.getName());
		item_rating.setRating(appInfo.getStars());
		item_size.setText(StringUtils.formatFileSize(appInfo.getSize()));
		item_bottom.setText(appInfo.getDes());
	}

}
