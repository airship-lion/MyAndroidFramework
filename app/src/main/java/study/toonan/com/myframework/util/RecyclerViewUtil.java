package study.toonan.com.myframework.util;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import study.toonan.com.myframework.decoration.GridItemDecoration;
import study.toonan.com.myframework.decoration.ListItemDecoration;

/**
 * Created by Administrator on 2016/9/6.
 */
public class RecyclerViewUtil {

	public static void setLinearAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
		setLinearAdapter(recyclerView, adapter, true);
	}

	public static void setLinearAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter, boolean hasDivideLine) {
		initLinearManager(recyclerView, hasDivideLine);
		recyclerView.setAdapter(adapter);
	}

	/**
	 * 初始化线性布局管理器，默认有分割线
	 *
	 * @param recyclerView
	 */
	public static void initLinearManager(RecyclerView recyclerView) {
		initLinearManager(recyclerView, true);
	}

	/**
	 * @param recyclerView
	 * @param hasDivideLine
	 *            是否有分割线
	 */
	public static void initLinearManager(RecyclerView recyclerView, boolean hasDivideLine) {
		// 设置布局管理器
		recyclerView.setLayoutManager(new LinearLayoutManager(UIUtils.getContext(), LinearLayout.VERTICAL, false));
		if (hasDivideLine) {
			recyclerView.addItemDecoration(new ListItemDecoration());
		}
		// recyclerView.setItemAnimator(new DefaultItemAnimator());
	}

	/**
	 * 初始化线性布局管理器，默认有分割线
	 *
	 * @param recyclerView
	 */
	public static void initGridManager(RecyclerView recyclerView, int spanCount) {
        initGridManager(recyclerView, spanCount,true);
	}

	/**
	 * @param recyclerView
	 * @param hasDivideLine
	 *            是否有分割线
	 */
	public static void initGridManager(RecyclerView recyclerView, int spanCount, boolean hasDivideLine) {
		// 设置布局管理器
		recyclerView.setLayoutManager(new GridLayoutManager(UIUtils.getContext(), spanCount));
		if (hasDivideLine) {
			recyclerView.addItemDecoration(new GridItemDecoration());
		}
		// recyclerView.setItemAnimator(new DefaultItemAnimator());
	}

    public static void setGridAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter,int spanCount) {
        setGridAdapter(recyclerView, adapter,spanCount, true);
    }

    public static void setGridAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter,int spanCount, boolean hasDivideLine) {
        initGridManager(recyclerView, spanCount,hasDivideLine);
        recyclerView.setAdapter(adapter);
    }
}
