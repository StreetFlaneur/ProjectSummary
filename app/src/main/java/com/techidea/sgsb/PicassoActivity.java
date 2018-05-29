package com.techidea.sgsb;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zc on 2017/9/25.
 */

public class PicassoActivity extends AppCompatActivity {

    @BindView(R.id.imageview_top1)
    ImageView imageViewTop1;
    @BindView(R.id.imageview_top2)
    ImageView imageViewTop2;
    @BindView(R.id.imageview_top3)
    ImageView imageViewTop3;


    private String imageUrl1 = "http://sgsb.3tichina.com/sgsb/appImage/studio/studiolist006.png";
    private String imageUrl2 = "http://sgsb.3tichina.com/sgsb/appImage/studio/studiolist009.jpg";
    private String imageUrl3 = "http://sgsb.3tichina.com/sgsb/appImage/studio/studiolist010.jpg";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso);
        ButterKnife.bind(this);

//        Picasso.with(this).load(imageUrl1)
//                .transform(new Transformation() {
//                    @Override
//                    public Bitmap transform(Bitmap source) {
//                        LogUtil.i("height: " + source.getHeight() + " width: " + source.getWidth());
//                        return source;
//                    }
//
//                    @Override
//                    public String key() {
//                        return "key";
//                    }
//                })
//                .into(imageViewTop1);


        Picasso.with(this)
                .load(imageUrl2)
                .transform(new CropSquareTransformation(48, 48))
                .into(imageViewTop2);

//        Picasso.with(this).load(imageUrl3)
//                .transform(new Transformation() {
//                    @Override
//                    public Bitmap transform(Bitmap source) {
//                        LogUtil.i("height: " + source.getHeight() + " width: " + source.getWidth());
//                        return source;
//                    }
//
//                    @Override
//                    public String key() {
//                        return "3";
//                    }
//                })
//                .into(imageViewTop3);

    }

    private class CropSquareTransformation implements Transformation {

        CropSquareTransformation(int width, int height) {
            this.mHeight = height;
            this.mWidth = width;
        }

        private int mWidth;
        private int mHeight;

        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

//            mWidth = (source.getWidth() - size) / 2;
//            mHeight = (source.getHeight() - size) / 2;

            Bitmap bitmap = Bitmap.createBitmap(source, mWidth, mHeight, size, size);
            if (bitmap != source) {
                source.recycle();
            }
            return bitmap;
        }

        @Override
        public String key() {
            return "CropSquareTransformation(width=" + mWidth + ", height=" + mHeight + ")";
        }
    }
}
