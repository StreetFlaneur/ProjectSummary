package com.techidea.sgsb.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.techidea.library.widget.DMTabHost;
import com.techidea.library.widget.MFViewPager;
import com.techidea.sgsb.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sam on 2018/1/29.
 */

public class BottomTabActivtiy extends AppCompatActivity {

    @BindView(R.id.viewpager)
    MFViewPager mfViewPager;
    @BindView(R.id.tab_host)
    DMTabHost dmTabHost;
    @BindView(R.id.imageview_left)
    ImageView imageViewLeft;
    @BindView(R.id.imageview_right)
    ImageView imageViewRight;
    @BindView(R.id.textview_title)
    TextView textViewTitle;

    private MainTabAdapter mainTabAdapter;
    private int pageIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_tab);
        ButterKnife.bind(this);
        initTab();
    }

    private void initTab() {
        mainTabAdapter = new MainTabAdapter(getSupportFragmentManager());
        mainTabAdapter.clear();
        mainTabAdapter.addFragment(HomeFragment.newInstance("Home"), "Home");
        mainTabAdapter.addFragment(HomeFragment.newInstance("Contacts"), "Contacts");
        mainTabAdapter.addFragment(HomeFragment.newInstance("Profile"), "Profile");
        mainTabAdapter.addFragment(HomeFragment.newInstance("Me"), "Me");
        mfViewPager.setAdapter(mainTabAdapter);
        //不允许滑动切换
        mfViewPager.setScrollble(false);

        mfViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                setTabMsg(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        dmTabHost.setOnCheckedChangeListener(new DMTabHost.OnCheckedChangeListener() {
            @Override
            public void onCheckedChange(int checkedPosition, boolean byUser) {
                setTabMsg(checkedPosition);
            }
        });

        imageViewRight.setVisibility(View.VISIBLE);
        imageViewRight.setImageResource(R.mipmap.icon_add);
        imageViewLeft.setVisibility(View.GONE);
        dmTabHost.setChecked(0);
        dmTabHost.setHasNew(2, true);
    }

    private void setTabMsg(int checkedPosition) {
        pageIndex = checkedPosition;
        mfViewPager.setCurrentItem(checkedPosition);
        switch (checkedPosition) {
            case 0:
                imageViewRight.setVisibility(View.VISIBLE);
                imageViewRight.setImageResource(R.mipmap.icon_add);
                textViewTitle.setText(getString(R.string.app_name));
                break;
            case 1:
                imageViewRight.setVisibility(View.VISIBLE);
                imageViewRight.setImageResource(R.mipmap.icon_titleaddfriend);
                textViewTitle.setText(getString(R.string.contacts));
                break;
            case 2:
                imageViewRight.setVisibility(View.GONE);
                textViewTitle.setText(getString(R.string.discover));
                break;
            case 3:
                imageViewRight.setVisibility(View.GONE);
                textViewTitle.setText(getString(R.string.me));
                break;
        }
    }

    private class MainTabAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        //        private final List<String> mFragmentTitles = new ArrayList<>();
        private FragmentManager fm;

        public MainTabAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
            this.saveState();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
//            mFragmentTitles.add(title);
        }

        public void clear() {
//            mFragmentTitles.clear();
            mFragments.clear();
            notifyDataSetChanged();
        }


        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
//            return mFragmentTitles.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }
}
