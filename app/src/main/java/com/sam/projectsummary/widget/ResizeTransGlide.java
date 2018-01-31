package com.sam.projectsummary.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by sam on 2018/1/30.
 */

public class ResizeTransGlide extends BitmapTransformation {

    private Context context;

    public ResizeTransGlide(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return resize(pool, toTransform);
    }

    private Bitmap resize(BitmapPool pool, Bitmap toTransform) {
        if (toTransform == null) return null;
        // TODO this could be acquired from the pool too
        Log.i("Source", toTransform.getWidth() + " " + toTransform.getHeight());
        Matrix matrix = new Matrix();
        matrix.postScale(1.5f, 1.5f); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(toTransform, 0, 0, toTransform.getWidth(), toTransform.getHeight(), matrix, true);
        Log.i("Resize", resizeBmp.getWidth() + " " + resizeBmp.getHeight());
        return resizeBmp;
    }

    @Override
    public String getId() {
        return "circleId";
    }
}
