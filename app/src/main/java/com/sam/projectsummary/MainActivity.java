package com.sam.projectsummary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sam.projectsummary.scrollrecyclerview.ScrollRecyclerviewActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_scroll_list)
    void scrollListClick() {
        startActivity(new Intent(this, ScrollRecyclerviewActivity.class));
    }
}
