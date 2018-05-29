package com.techidea.sgsb.douban;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.techidea.sgsb.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sam on 2018/5/28.
 */

public class DoubanBooksActivity extends AppCompatActivity {

    @BindView(R.id.et_search_keyword)
    EditText etKeyWord;
    @BindView(R.id.rv_books)
    RecyclerView rvBooks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        ButterKnife.bind(this);
    }
}
