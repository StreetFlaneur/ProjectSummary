package com.example.leftdrawer.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView


import com.example.leftdrawer.R
import com.sam.library.widget.GradationScrollView

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by sam on 2018/1/31.
 */

class HomeFragment : BaseFragment() {

    @BindView(R.id.textview_content)
    internal var textViewContent: TextView? = null
    @BindView(R.id.scrollview)
    internal var gradationScrollView: GradationScrollView? = null
    @BindView(R.id.button_menu)
    internal var buttonMenu: Button? = null
    @BindView(R.id.layout_top)
    internal var layoutTop: LinearLayout? = null
    @BindView(R.id.layoutchange_menu_suspension)
    internal var layoutSuspension: LinearLayout? = null
    @BindView(R.id.layout_content)
    internal var layoutContent: LinearLayout? = null
    @BindView(R.id.layout_button_menu)//承载悬浮菜单高度要和菜单高度一致。才能保证平滑效果
    internal var layoutButtonMenu: LinearLayout? = null

    private var topHeight: Int = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_home, container, false)
        ButterKnife.bind(this, rootView)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textViewContent!!.text = "Home"
        gradationScrollView!!.setScrollViewListener(GradationScrollView.ScrollViewListener { x, y, oldl, oldt ->
            topHeight = layoutButtonMenu!!.top

            Log.i("Height", topHeight.toString())
            //显示悬浮按钮layout top
            if (y > 0 && y >= topHeight) {
                //当menu的顶部到达页面顶部时候，显示
                if (View.VISIBLE == layoutSuspension!!.visibility) {
                    return@ScrollViewListener
                }
                layoutSuspension!!.visibility = View.VISIBLE
                //再增加一个条件
            } else if (y <= topHeight) {
                //当menu的顶部和悬浮的menu顶部重合时，隐藏
                //代码走到这里，只要小于topHeight 肯定小于topHeight加上一个值
                Log.i("SCRLL", "y" + y)
                if (View.GONE == layoutSuspension!!.visibility) {
                    return@ScrollViewListener
                }
                layoutSuspension!!.visibility = View.GONE
            }
            Log.i("SCROLLMENU", "y: " + y + " topHeight: " + topHeight + " menuHeight:" + layoutButtonMenu!!.height)
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
