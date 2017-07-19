package com.heima.googleplay;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.heima.googleplay.fragment.AppFragment;
import com.heima.googleplay.fragment.BaseFragment;
import com.heima.googleplay.fragment.FragmentFactory;
import com.heima.googleplay.fragment.GameFragment;
import com.heima.googleplay.fragment.HomeFragment;
import com.heima.googleplay.utils.UIUtils;
import com.heima.googleplay.widget.PagerTab;

public class MainActivity extends BaseActivity implements OnPageChangeListener, DrawerListener {


	private PagerTab mTabs;
	private ViewPager mPager;
	private DrawerLayout drawer_layout;
	private ActionBarDrawerToggle toggle;


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	//菜单选中的方法
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
	}

     private class MainAdapter extends FragmentStatePagerAdapter{

		private String[] tab_names;
		private BaseFragment fragment;

		public MainAdapter(FragmentManager fm) {
			super(fm);
			tab_names = UIUtils.getStringArray(R.array.tab_names);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return tab_names[position];
		}
		
		@Override
		public Fragment getItem(int position) {
			
			return FragmentFactory.createFragment(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return tab_names.length;
		}
    	 
     }

	@Override
	protected void initView() {
		setContentView(R.layout.activity_main);
		mTabs = (PagerTab) findViewById(R.id.tabs);
		mPager = (ViewPager) findViewById(R.id.pager);
		drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer_layout.setDrawerListener(this);
		MainAdapter adapter = new MainAdapter(getSupportFragmentManager());
		mPager.setAdapter(adapter);
		//绑定到一起
		mTabs.setViewPager(mPager);
		mTabs.setOnPageChangeListener(this);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		BaseFragment fragment = FragmentFactory.createFragment(arg0);
		fragment.show();
	}

	@Override
	protected void initActionbar() {
		//获取到支持包里面的actionbar
		ActionBar actionBar = getSupportActionBar();
		//展示按钮
		actionBar.setDisplayHomeAsUpEnabled(true);
		//按钮可以被点击
		actionBar.setHomeButtonEnabled(true);
		//获取到actionbar的开关
		
		toggle = new ActionBarDrawerToggle(this, drawer_layout, R.drawable.ic_drawer_am, R.string.drawer_open, R.string.drawer_close);
	    //开关和滑动菜单关联起来
		toggle.syncState();
	}

	@Override
	public void onDrawerClosed(View arg0) {
		toggle.onDrawerClosed(arg0);
		
	}

	@Override
	public void onDrawerOpened(View arg0) {
		toggle.onDrawerOpened(arg0);
		
	}

	@Override
	public void onDrawerSlide(View arg0, float arg1) {
		toggle.onDrawerSlide(arg0, arg1);
		
	}

	@Override
	public void onDrawerStateChanged(int arg0) {
		toggle.onDrawerStateChanged(arg0);
		
	}
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}

}
