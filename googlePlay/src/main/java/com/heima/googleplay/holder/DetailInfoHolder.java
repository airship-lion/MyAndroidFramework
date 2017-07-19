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

public class DetailInfoHolder extends BaseHolder<AppInfo> {

	private ImageView item_icon;
	private TextView item_title;
	private RatingBar item_rating;
	private TextView item_download;
	private TextView item_version;
	private TextView item_date;
	private TextView item_size;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.app_detail_info);
		item_icon = (ImageView) view.findViewById(R.id.item_icon);
		item_title = (TextView) view.findViewById(R.id.item_title);
		item_rating = (RatingBar) view.findViewById(R.id.item_rating);
		item_download = (TextView) view.findViewById(R.id.item_download);
		item_version = (TextView) view.findViewById(R.id.item_version);
		item_date = (TextView) view.findViewById(R.id.item_date);
		item_size = (TextView) view.findViewById(R.id.item_size);
		return view;
	}

	@Override
	public void refreshView() {
		AppInfo appInfo = getData();
		item_title.setText(appInfo.getName());
		item_rating.setRating(appInfo.getStars());
		item_download.setText("下载:" + appInfo.getDownloadNum());
		item_version.setText("版本:" + appInfo.getVersion());
		item_date.setText("时间:" + appInfo.getDate());
		item_size.setText("大小:" + StringUtils.formatFileSize(appInfo.getSize()));
		Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" + appInfo.getIconUrl()).into(item_icon);
	}

}
