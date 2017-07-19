package study.toonan.com.myframework.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.eugene.ebase.base.adapter.BaseRecyclerHolder;
import com.eugene.ebase.base.adapter.RecyclerAdapter;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.toonan.com.myframework.R;
import study.toonan.com.myframework.adapter.base.MyBaseAdapter;
import study.toonan.com.myframework.util.RecyclerViewUtil;
import study.toonan.com.myframework.util.UIUtils;
import study.toonan.com.myframework.widget.LoadingPage;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.OnRecyclerViewItemClickListener {

	protected List<String> mDatas;
	@Bind(R.id.recyclerview)
	RecyclerView recyclerview;
	@Bind(R.id.swipelayout)
	SwipeRefreshLayout swipelayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LoadingPage mContentPage = new LoadingPage(UIUtils.getContext()) {

			@Override
			public View createSuccessView() {
				return MainActivity.this.createSuccessView();
			}

			@Override
			public LoadResult Load() {
				return MainActivity.this.load();
			}
		};
		setContentView(mContentPage);
		mContentPage.show();
	}

	private LoadingPage.LoadResult load() {
		return LoadingPage.LoadResult.SUCCESS;
	}

	private View createSuccessView() {
		String[] array = { RentPatrolListActivity.class.getSimpleName(),RentPatrolListActivity2.class.getSimpleName(), baseAdapterActivity.class.getSimpleName(), baseAdapterActivity2.class.getSimpleName(), baseAdapterActivity3.class.getSimpleName() };
		mDatas = Arrays.asList(array);
		View rootView = UIUtils.inflate(R.layout.fragment_common_list_no_title_bar);
		ButterKnife.bind(this, rootView);
		MyBaseAdapter<String> adapter = new MyBaseAdapter<String>(this, mDatas) {
			@Override
			protected List onLoadMoreData() {
				return null;
			}

			@Override
			public int onCreateViewLayoutID(int i) {
				return R.layout.list_item;
			}

			@Override
			public void onBindViewData(BaseRecyclerHolder holder, int position) {
				holder.setText(R.id.tv_title, mDatas.get(position));
			}

		};
		adapter.setOnRecyclerViewItemClickListener(this);
		RecyclerViewUtil.setLinearAdapter(recyclerview, adapter);
		return rootView;
	}

	@Override
	public void onItemClick(View view, int i) {
		switch (i) {
		case 0:
			startActivity(new Intent(this, RentPatrolListActivity.class));
			break;
		case 1:
			startActivity(new Intent(this, RentPatrolListActivity2.class));
			break;
		case 2:
			startActivity(new Intent(this, baseAdapterActivity.class));
			break;
		case 3:
			startActivity(new Intent(this, baseAdapterActivity2.class));
			break;
		case 4:
			startActivity(new Intent(this, baseAdapterActivity3.class));
			break;
		}
	}

}
