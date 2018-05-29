package com.library.leftdrawer.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.library.leftdrawer.R
import com.techidea.library.widget.GradationScrollView

import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by sam on 2018/1/31.
 */

class HomeFragment : BaseFragment() {

    private var topHeight: Int = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_home, container, false)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textview_content.text = "Home"
        scrollview.setScrollViewListener(GradationScrollView.ScrollViewListener { x, y, oldl, oldt ->
            topHeight = layout_button_menu.top

            Log.i("Height", topHeight.toString())
            //显示悬浮按钮layout top
            if (y > 0 && y >= topHeight) {
                //当menu的顶部到达页面顶部时候，显示
                if (View.VISIBLE == layoutchange_menu_suspension!!.visibility) {
                    return@ScrollViewListener
                }
                layoutchange_menu_suspension.visibility = View.VISIBLE
                //再增加一个条件
            } else if (y <= topHeight) {
                //当menu的顶部和悬浮的menu顶部重合时，隐藏
                //代码走到这里，只要小于topHeight 肯定小于topHeight加上一个值
                Log.i("SCRLL", "y" + y)
                if (View.GONE == layoutchange_menu_suspension.visibility) {
                    return@ScrollViewListener
                }
                layoutchange_menu_suspension.visibility = View.GONE
            }
        })
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        // TODO: 2018/1/31 页面数据重新加载
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
    }

    companion object {

        fun newInstance(content: String): HomeFragment {
            return HomeFragment()
        }
    }
}
