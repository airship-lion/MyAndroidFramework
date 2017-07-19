package study.toonan.com.myframework.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.toonan.com.myframework.R;
import study.toonan.com.myframework.fragment.BaseFragment;
import study.toonan.com.myframework.fragment.DictListFragment;
import study.toonan.com.myframework.fragment.DictListFragment2;
import study.toonan.com.myframework.fragment.DictListFragment3;
import study.toonan.com.myframework.fragment.DictListFragment4;
import study.toonan.com.myframework.fragment.DictListFragment5;
import study.toonan.com.myframework.fragment.JzListFragment;

public class ViewPagerActivity extends AppCompatActivity {

	@Bind(R.id.viewPager)
	ViewPager viewPager;
    protected List<BaseFragment> list;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager);
		ButterKnife.bind(this);
        list = new ArrayList<>();
        BaseFragment f=new JzListFragment();
		list.add(f);
		BaseFragment f1 = new DictListFragment();
		list.add(f1);
        BaseFragment f2 = new DictListFragment2();
		list.add(f2);
        BaseFragment f3 = new DictListFragment3();
		list.add(f3);
        BaseFragment f4 = new DictListFragment4();
		list.add(f4);
        BaseFragment f5 = new DictListFragment5();
		list.add(f5);

        setAdapter();
        f.show();
	}

    private void setAdapter() {
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BaseFragment baseFragment = list.get(position);
                baseFragment.show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
