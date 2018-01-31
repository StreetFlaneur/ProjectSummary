package com.sam.projectsummary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.sam.library.widget.DMTabHost;
import com.sam.library.widget.MFViewPager;
import com.sam.projectsummary.R;

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

    private MainTabAdapter mainTabAdapter;


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
        mfViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                dmTabHost.setChecked(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        dmTabHost.setChecked(0);
        dmTabHost.setHasNew(2, true);
        dmTabHost.setOnCheckedChangeListener(new DMTabHost.OnCheckedChangeListener() {
            @Override
            public void onCheckedChange(int checkedPosition, boolean byUser) {
                mfViewPager.setCurrentItem(checkedPosition);
            }
        });
    }

    private class MainTabAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();
        private FragmentManager fm;

        public MainTabAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
            this.saveState();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        public void clear() {
            mFragmentTitles.clear();
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
            return mFragmentTitles.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }
}
