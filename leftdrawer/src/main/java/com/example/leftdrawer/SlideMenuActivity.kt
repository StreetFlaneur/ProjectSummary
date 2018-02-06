package com.example.leftdrawer

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.widget.Button

import com.example.data.net.HttpMethods
import com.example.leftdrawer.fragment.HomeFragment
import com.example.leftdrawer.fragment.PersonFragment
import com.jkb.slidemenu.SlideMenuLayout

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_slidemenu.*
import kotlinx.android.synthetic.main.fragment_home_left.*


/**
 * Created by sam on 2018/1/31.
 */

class SlideMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slidemenu)
        ButterKnife.bind(this)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        var fragment: HomeFragment? = supportFragmentManager
                .findFragmentById(R.id.fragment_container) as HomeFragment
        if (fragment == null) {
            fragment = HomeFragment.newInstance("")
            fragmentTransaction.add(R.id.fragment_container, fragment)
            fragmentTransaction.commit()
        }

        button_person.setOnClickListener {
            val personFragment = PersonFragment.newInstance("Person")
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, personFragment)
                    .commitAllowingStateLoss()
            slidemenulayout.closeLeftSlide()
        }

        button_open.setOnClickListener {
            startActivity(Intent(this, LeftDrawerActivity::class.java))
        }

        button_home.setOnClickListener {
            val homeFragment = HomeFragment.newInstance("home")
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, homeFragment)
                    .commitAllowingStateLoss()
            slidemenulayout.closeLeftSlide()
        }
    }
}
