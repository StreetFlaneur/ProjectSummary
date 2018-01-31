package com.example.leftdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.leftdrawer.fragment.HomeFragment;
import com.example.leftdrawer.fragment.PersonFragment;
import com.jkb.slidemenu.SlideMenuLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.facebook.common.internal.Preconditions.checkNotNull;

/**
 * Created by sam on 2018/1/31.
 */

public class SlideMenuActivity extends AppCompatActivity {

    @BindView(R.id.slidemenulayout)
    SlideMenuLayout slideMenuLayout;
    @BindView(R.id.button_person)
    Button buttonPerson;
    @BindView(R.id.button_home)
    Button buttonHome;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slidemenu);
        ButterKnife.bind(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HomeFragment fragment = (HomeFragment) (getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container));
        if (fragment == null) {
            fragment = HomeFragment.newInstance("");
            fragmentTransaction.add(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }
    }

    @OnClick(R.id.button_person)
    void personClick() {
        PersonFragment personFragment = PersonFragment.newInstance("Person");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, personFragment)
                .commitAllowingStateLoss();
        slideMenuLayout.closeLeftSlide();
    }

    @OnClick(R.id.button_home)
    void homeClick() {
        HomeFragment homeFragment = HomeFragment.newInstance("home");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, homeFragment)
                .commitAllowingStateLoss();
        slideMenuLayout.closeLeftSlide();
    }

    @OnClick(R.id.button_open)
    void openClick() {
        startActivity(new Intent(this, LeftDrawerActivity.class));
    }
}
