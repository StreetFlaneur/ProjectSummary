package com.sam.projectsummary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.sam.library.Constant;
import com.sam.library.widget.AvatarView;
import com.sam.projectsummary.constant.Constants;
import com.sam.projectsummary.widget.ResizeTranbsformation;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sam on 2018/1/18.
 */

public class CircleImageViewActivity extends AppCompatActivity {

    @BindView(R.id.imageview01)
    ImageView imageView01;
    @BindView(R.id.imageview02)
    ImageView imageView02;
    @BindView(R.id.imageview03)
    ImageView imageView03;
    @BindView(R.id.imageview04)
    ImageView imageView04;
    @BindView(R.id.imageview05)
    ImageView imageView05;
    @BindView(R.id.imageview06)
    ImageView imageView06;
    @BindView(R.id.imageviewCircle)
    SimpleDraweeView cicleImage;

    @BindView(R.id.avatarview)
    AvatarView avatarView;

    @BindView(R.id.avatarview1)
    AvatarView avatarView1;

    private String imageContent = "{\"http://app3titest.metro.com.cn:8080/metro/metro/ShowImg/31242\":" +
            "\"http://app3titest.metro.com.cn:8080/metro/metro/ShowImg/31242\"," +
            "\"http://app3titest.metro.com.cn:8080/metro/metro/ShowImg/31225\":" +
            "\"http://app3titest.metro.com.cn:8080/metro/metro/ShowImg/31225\"," +
            "\"http://app3titest.metro.com.cn:8080/metro/metro/ShowImg/31226\":" +
            "\"http://app3titest.metro.com.cn:8080/metro/metro/ShowImg/31226\"," +
            "\"http://app3titest.metro.com.cn:8080/metro/metro/ShowImg/31227\":" +
            "\"http://app3titest.metro.com.cn:8080/metro/metro/ShowImg/31227\"," +
            "\"http://app3titest.metro.com.cn:8080/metro/metro/ShowImg/31341\":" +
            "\"http://app3titest.metro.com.cn:8080/metro/metro/ShowImg/31341\"}";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circleimage);
        ButterKnife.bind(this);

        Glide.with(this)
                .load(Constants.IMAGE01)
                .into(imageView01);

        Glide.with(this)
                .load(Constants.IMAGE02)
                .into(imageView02);

        Glide.with(this)
                .load(Constants.IMAGE03)

                .into(imageView03);

        Glide.with(this)
                .load(Constants.IMAGE04)
                .transform(new ResizeTranbsformation(this))
                .into(avatarView1);

        Glide.with(this)
                .load(Constants.IMAGE06)
                .transform(new ResizeTranbsformation(this))
                .into(imageView05);

        SimpleDraweeView  circleImage01 = findViewById(R.id.imageviewCircle);
        circleImage01.setAspectRatio(1.0f);
        Uri uri = Uri.parse(Constants.IMAGE06);
        circleImage01.setImageURI(uri);


        Picasso.with(this)
                .load(Constants.IMAGE06)
                .resize(100,100)
                .into(avatarView);

//        Glide.with(this)
//                .load(Constants.IMAGE06)
//                .into(avatarView1);


    }

}
