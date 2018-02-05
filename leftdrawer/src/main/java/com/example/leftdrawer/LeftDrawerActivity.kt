package com.example.leftdrawer

import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.*
import android.support.v4.view.GravityCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import com.example.leftdrawer.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.view.View
import android.support.design.widget.BottomSheetDialog


class LeftDrawerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
//        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
//                Gravity.LEFT)
        toggle.syncState()
        button_slidemenu.setOnClickListener {
            //startActivity 两种写法
            //            startActivity(Intent(this, SlideMenuActivity::class.java))
            startActivity(Intent(LeftDrawerActivity@ this, SlideMenuActivity::class.java));
        }
//        nav_view.setNavigationItemSelectedListener(this)
        val constraintlayout = findViewById<CoordinatorLayout>(R.id.constraintlayout)
        val bottomSheet = constraintlayout.findViewById<NestedScrollView>(R.id.bottom_sheet)
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {

            }

            override fun onStateChanged(p0: View, p1: Int) {

            }
        })
        behavior.peekHeight = 200

        button_bottomsheet.setOnClickListener { view ->
            if (behavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                behavior.state = BottomSheetBehavior.STATE_HIDDEN
            } else {
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        button_bottomsheetdialog.setOnClickListener { view ->
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.bottomsheetgialog, null)
            dialog.setContentView(view)
            dialog.show()
        }

        button_bottomsheetfragment.setOnClickListener({ view ->

        })
    }

    fun changeFragment(content: String) {
        var transition = supportFragmentManager.beginTransaction()
        transition.replace(0, HomeFragment.newInstance(content))
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
