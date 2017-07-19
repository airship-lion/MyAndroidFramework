package com.heima.googleplay.widget;

import com.heima.googleplay.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class BaseListView extends ListView {

	public BaseListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		setSelector(R.drawable.nothing);
		
	}

	public BaseListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BaseListView(Context context) {
		super(context);
		init();
	}

}
