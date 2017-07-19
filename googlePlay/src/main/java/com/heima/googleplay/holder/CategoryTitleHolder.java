package com.heima.googleplay.holder;

import android.view.View;
import android.widget.TextView;

import com.heima.googleplay.R;
import com.heima.googleplay.bean.CategoryInfo;
import com.heima.googleplay.utils.UIUtils;

public class CategoryTitleHolder extends BaseHolder<CategoryInfo> {

	private TextView tv_title;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.category_item_title);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		return view;
	}

	@Override
	public void refreshView() {
		CategoryInfo categoryInfo = getData();
		tv_title.setText(categoryInfo.getTitle());
	}

}
