package com.techidea.sgsb.douban;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techidea.sgsb.R;

/**
 * Created by sam on 2018/5/28.
 */

public class DoubanBooksFragment extends Fragment
        implements DoubanBooksContact.View {

    public static Fragment newInstance() {
        DoubanBooksFragment doubanBooksFragment = new DoubanBooksFragment();
        return doubanBooksFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.doubanbooks_fragment, container, false);
        return rootView;
    }


}
