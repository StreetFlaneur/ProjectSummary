package com.example.leftdrawer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.leftdrawer.R;
import com.sam.library.widget.GradationScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sam on 2018/1/31.
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.textview_content)
    TextView textViewContent;
    @BindView(R.id.scrollview)
    GradationScrollView gradationScrollView;
    @BindView(R.id.button_menu)
    Button buttonMenu;
    @BindView(R.id.layout_top)
    LinearLayout layoutTop;
    @BindView(R.id.layoutchange_menu_suspension)
    LinearLayout layoutchange_menu_suspension;
    @BindView(R.id.layout_content)
    LinearLayout layoutContent;
    @BindView(R.id.layout_button_menu)//承载悬浮菜单高度要和菜单高度一致。才能保证平滑效果
            LinearLayout layoutButtonMenu;

    public static HomeFragment newInstance(String content) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    private int topHeight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewContent.setText("Home");
        gradationScrollView.setScrollViewListener(new GradationScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(int x, int y, int oldl, int oldt) {
                topHeight = layoutButtonMenu.getTop();

                Log.i("Height", String.valueOf(topHeight));
                //显示悬浮按钮layout top
                if (y > 0 && y >= topHeight) {
                    layoutchange_menu_suspension.setVisibility(View.VISIBLE);
                 /*   if (buttonMenu.getParent() != linearLayoutChangeMenu) {
                        layoutButtonMenu.removeView(buttonMenu);
//                        layoutButtonMenu.setVisibility(View.VISIBLE);
                        linearLayoutChangeMenu.addView(buttonMenu);
//                        linearLayoutChangeMenu.setVisibility(View.GONE);
                    }*/
                    //再增加一个条件
                } else if (y <= topHeight + layoutButtonMenu.getHeight()) {
                    layoutchange_menu_suspension.setVisibility(View.GONE);
                   /* if (buttonMenu.getParent() != layoutButtonMenu) {
                        linearLayoutChangeMenu.removeView(buttonMenu);
                        layoutButtonMenu.addView(buttonMenu);
                    }*/
                }
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        // TODO: 2018/1/31 页面数据重新加载
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
}
