package com.heima.googleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.heima.googleplay.R;
import com.heima.googleplay.bean.SubjectInfo;
import com.heima.googleplay.http.HttpHelper;
import com.heima.googleplay.utils.UIUtils;

public class SubjectHolder extends BaseHolder<SubjectInfo> {

	private ImageView item_icon;
	private TextView item_txt;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.subject_item);
		item_icon = (ImageView) view.findViewById(R.id.item_icon);
		item_txt = (TextView) view.findViewById(R.id.item_txt);
		return view;
	}

	@Override
	public void refreshView() {
		SubjectInfo subjectInfo = getData();
		item_txt.setText(subjectInfo.getDes());
		Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" + subjectInfo.getUrl()).into(item_icon);
	}

}
