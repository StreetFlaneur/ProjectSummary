package com.sam.projectsummary.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sam.projectsummary.R;

import butterknife.ButterKnife;

/**
 * Created by sam on 2018/1/23.
 */

public class FragmentUiTest extends Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_ui_test, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
