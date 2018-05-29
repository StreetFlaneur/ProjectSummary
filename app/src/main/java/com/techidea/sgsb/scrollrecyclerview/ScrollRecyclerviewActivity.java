package com.techidea.sgsb.scrollrecyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.techidea.library.Constant;
import com.techidea.sgsb.MockData;
import com.techidea.sgsb.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zc on 2017/9/10.
 */

public class ScrollRecyclerviewActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.imageview_content)
    ImageView imageViewContent;

    private ArticleListAdapter articleListAdapter;
    private List<ContentItem> contentItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_recyclerview);
        ButterKnife.bind(this);
//        initToolbar();
        initView();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("RecyclerviewScroll");
    }

    private void initView() {
        Picasso.with(this)
                .load(Constant.IMAGE_URL_DEEPWORK)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageViewContent);
        contentItems = MockData.getArtilceItems();
        articleListAdapter = new ArticleListAdapter(recyclerView, contentItems);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(articleListAdapter);
    }
}
