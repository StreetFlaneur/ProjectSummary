package com.techidea.sgsb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.techidea.library.widget.AvatarView;
import com.techidea.sgsb.constant.Constants;
import com.techidea.sgsb.widget.ResizeTransGlide;
import com.techidea.sgsb.widget.ResizeTranbsformation;

import agency.tango.android.avatarview.IImageLoader;
import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarviewglide.GlideLoader;
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
    @BindView(R.id.imageview_avatar)
    ImageView imageViewAvatar;

    @BindView(R.id.avatarview)
    AvatarView avatarView;

    @BindView(R.id.avatarview1)
    AvatarView avatarView1;

    @BindView(R.id.avatartview_1)
    agency.tango.android.avatarview.views.AvatarView avatarView01;

    private String avatarUrl = "http://app3titest.metro.com.cn:8080/metro/metro/ShowImg/31387";

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
    IImageLoader iImageLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circleimage);
        ButterKnife.bind(this);
        iImageLoader = new PicassoLoader();
//        initCacheData();
        initView();

    }

    private void initCacheData() {

//        CacheLoaderManager.getInstance().saveString("images", imageContent, 60 * 24);
//
//        String conten = CacheLoaderManager.getInstance().loadString("images");
//        if (conten.equalsIgnoreCase(imageContent)) {
//            Log.i("CACHE", "success");
//        }
    }

    private void initView() {
//        Glide.with(this)
//                .load(Constants.IMAGE01)
//                .into(imageView01);
//
//        Glide.with(this)
//                .load(Constants.IMAGE02)
//                .into(imageView02);
//
//        Glide.with(this)
//                .load(Constants.IMAGE03)
//                .into(imageView03);
//
        Glide.with(this)
                .load(Constants.IMAGE06)
                .transform(new ResizeTranbsformation(this))
                .into(imageViewAvatar);

        ResizeTransGlide resizeTransGlide = new ResizeTransGlide(this);
//        Glide.with(this)
//                .load(Constants.IMAGE06)
//                .transform(new ResizeTransGlide(this))
//                .into(avatarView);
        iImageLoader = new GlideLoader();
        iImageLoader.loadImage(avatarView01, Constants.IMAGE06, "");
      /*  Glide.with(this)
                .load(Constants.IMAGE06)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .transform(new BitmapTransformation(this) {
                    @Override
                    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
                        if (toTransform == null) return null;
                        // TODO this could be acquired from the pool too
                        Log.i("Source", toTransform.getWidth() + " " + toTransform.getHeight());
                        Matrix matrix = new Matrix();
                        matrix.postScale(3f, 3f); //长和宽放大缩小的比例
                        Bitmap resizeBmp = Bitmap.createBitmap(toTransform, 0, 0, toTransform.getWidth(), toTransform.getHeight(), matrix, true);
                        Log.i("Resize", resizeBmp.getWidth() + " " + resizeBmp.getHeight());
                        return resizeBmp;
                    }

                    @Override
                    public String getId() {
                        return "sizeIdd";
                    }
                })
                .into(avatarView);
*/
//        SimpleDraweeView circleImage01 = findViewById(R.id.imageviewCircle);
//        circleImage01.setAspectRatio(1.0f);
//        Uri uri = Uri.parse(Constants.IMAGE06);
//        circleImage01.setImageURI(uri);


//        Picasso.with(this)
//                .load(Constants.IMAGE06)
//                .resize(100, 100)
//                .into(avatarView);

//        Glide.with(this)
//                .load(Constants.IMAGE06)
//                .into(avatarView1);
    }

}
