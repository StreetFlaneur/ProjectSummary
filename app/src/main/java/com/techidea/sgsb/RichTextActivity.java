package com.techidea.sgsb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.zzhoujay.richtext.RichText;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zc on 2017/9/18.
 */

public class RichTextActivity extends AppCompatActivity {

    @BindView(R.id.textview_richtext)
    TextView textViewRichText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richtext);
        ButterKnife.bind(this);
        String html = "<p>BoBo小熊纯棉布艺粉色蓝色婴儿童圈圈音乐手摇铃</p><p><img width=\"100%\"" +
                " src=\"http://sgsb.3tichina.com/sgsb/upload/ueditor/image/20170918144339/15057170192380659589355.jpg\" " +
                "alt=\"IMG_0009.jpg\" title=\"15057170192380659589355.jpg\" /></p>";
//        CharSequence charSequence = Html.fromHtml(html, new Html.ImageGetter() {
//            @Override
//            public Drawable getDrawable(String source) {
//                Drawable drawable = null;
//                try {
//                    InputStream is = new DefaultHttpClient().execute(new HttpGet(source))
//                            .getEntity().getContent();
//                    Bitmap bitmap = BitmapFactory.decodeStream(is);
//                    drawable = new BitmapDrawable(bitmap);
//                    drawable.setBounds(0, 0, 300, 500);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return drawable;
//            }
//        }, null);
//        textViewRichText.setText(charSequence);
        RichText.from(html).autoPlay(true).singleLoad(false).into(textViewRichText);

    }
}
