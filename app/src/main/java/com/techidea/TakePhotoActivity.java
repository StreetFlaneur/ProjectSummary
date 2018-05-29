package com.techidea;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import com.techidea.sgsb.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sam on 2018/5/17.
 */

public class TakePhotoActivity extends AppCompatActivity {

    public static final int FROM_GALLERY = 1001;
    public static final int FROM_DOCUMENT = 1002;
    public static final String TAG = TakePhotoActivity.class.getCanonicalName();

    @BindView(R.id.iv_select)
    ImageView imageViewSelect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.takepgoto_activity);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_from_gallery)
    void pickFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, FROM_GALLERY);
    }


    @OnClick(R.id.tv_from_document)
    void pickFromDocument() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, FROM_DOCUMENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case FROM_GALLERY:
                    Uri uri = data.getData();
                    Log.w(TAG, uri.getAuthority());
                    setImage(uri);
                    Log.w(TAG, getImagePath(uri));
                    break;
                case FROM_DOCUMENT:
                    Uri urii = data.getData();
                    Log.w(TAG, urii.getAuthority());
                    setImage(urii);
                    Log.w(TAG, getFilePathWithDocumentsUri(urii, this));
                    break;
            }
        }
    }


//    7.0
//05-17 08:50:23.535 5381-5381/com.sam.projectsummary W/com.sam.TakePhotoActivity: media
//05-17 08:50:23.564 5381-5381/com.sam.projectsummary W/com.sam.TakePhotoActivity: /storage/emulated/0/Download/3e257de83c1ac59133e1a9212b50933a.jpg
//05-17 08:50:03.618 5381-5381/com.sam.projectsummary W/com.sam.TakePhotoActivity: com.android.providers.media.documents
//05-17 08:50:03.622 5381-5381/com.sam.projectsummary W/com.sam.TakePhotoActivity: empty
// 4.4
//    05-17 08:51:42.904 2918-2918/com.sam.projectsummary W/com.sam.TakePhotoActivity: media
//05-17 08:51:42.914 2918-2918/com.sam.projectsummary W/com.sam.TakePhotoActivity: /storage/sdcard/Download/0149eb5907e5aca801214550866692.png@1280w_1l_2o_100sh.png
//05-17 08:51:45.894 2918-2918/com.sam.projectsummary W/com.sam.TakePhotoActivity: com.android.providers.media.documents
//05-17 08:51:45.954 2918-2918/com.sam.projectsummary W/com.sam.TakePhotoActivity: empty


    //努比亚6.0 该方法没问题

    private String getImagePath(Uri uri) {
        String path = "";
        if (!uri.toString().contains("content://")) {
            return uri.toString();
        }
        String[] proj = {MediaStore.Images.Media.DATA};
        try {
            CursorLoader loader = new CursorLoader(this, uri, proj, null, null, null);
            Cursor cursor = loader.loadInBackground();
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);
            if (!cursor.isClosed())
                cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null == path ? "empty" : path;
    }

    //7.0 相册没问题
//          文件夹 直接报错

    //努比亚6.0 该方法没问题
    private String getPath(Uri uri) {
        String path = "";
        Cursor cursor = getContentResolver().query(
                uri, null, null, null, null);
        if (cursor.moveToFirst()) {
            path = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Images.Media.DATA));
        }
        cursor.close();
        return null == path ? "empty" : path;
    }

    private void setImage(Uri uri) {
        imageViewSelect.setImageURI(uri);
    }


    /**
     * 通过从文件中得到的URI获取文件的路径
     *
     * @param uri
     * @param activity
     * @return Author JPH
     * Date 2016/6/6 0006 20:01
     */

//    4.4


//    com.android.providers.media.documents
//05-17 09:19:39.314 3072-3072/com.sam.projectsummary W/com.sam.TakePhotoActivity:
// /storage/sdcard/Android/data/com.sam.projectsummary/files/Pictures/45fd804c-0349-4ac3-aee1-b079f98813cd.png


//    7.0


//    com.android.providers.media.documents
//05-17 09:19:39.314 3072-3072/com.sam.projectsummary W/com.sam.TakePhotoActivity:
//            /storage/sdcard/Android/data/com.sam.projectsummary/files/Pictures/45fd804c-0349-4ac3-aee1-b079f98813cd.png


    //努比亚 6.0
//05-17 17:22:03.036 29681-29681/com.sam.projectsummary W/com.sam.TakePhotoActivity: media
//05-17 17:22:03.358 29681-29681/com.sam.projectsummary W/com.sam.TakePhotoActivity: /storage/emulated/0/DCIM/Camera/IMG_20180517_154357.jpg
//05-17 17:22:10.026 29681-29681/com.sam.projectsummary W/com.sam.TakePhotoActivity: media
//05-17 17:22:10.357 29681-29681/com.sam.projectsummary W/com.sam.TakePhotoActivity: /storage/emulated/0/DCIM/Camera/IMG_20180515_175833.jpg


    public String getFilePathWithDocumentsUri(Uri uri, Activity activity) {
        if (uri == null) {
            Log.e(TAG, "uri is null,activity may have been recovered?");
            return "empty";
        }
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && uri.getPath().contains("document")) {
            File tempFile = getTempFile(activity, uri);
            try {
                inputStreamToFile(activity.getContentResolver().openInputStream(uri), tempFile);
                return tempFile.getPath();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return getFilePathWithUri(uri);
        }
    }

    /**
     * InputStream 转File
     */
    public void inputStreamToFile(InputStream is, File file) {
        if (file == null) {
            Log.i(TAG, "inputStreamToFile:file not be null");
            return;
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024 * 10];
            int i;
            while ((i = is.read(buffer)) != -1) {
                fos.write(buffer, 0, i);
            }
        } catch (IOException e) {
            Log.e(TAG, "InputStream 写入文件出错:" + e.toString());
            return;
        } finally {
            try {
                fos.flush();
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 获取临时文件
     *
     * @param context
     * @param photoUri
     * @return
     */
    public File getTempFile(Activity context, Uri photoUri) {
        String minType = getMimeType(photoUri);
        if (!checkMimeType(minType))
            return null;
        File filesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!filesDir.exists()) filesDir.mkdirs();
        File photoFile = new File(filesDir, UUID.randomUUID().toString() + "." + minType);
        return photoFile;
    }

    /**
     * 检查文件类型是否是图片
     *
     * @param minType
     * @return
     */
    public static boolean checkMimeType(String minType) {
        boolean isPicture = TextUtils.isEmpty(minType) ? false : ".jpg|.gif|.png|.bmp|.jpeg|.webp|".contains(minType.toLowerCase()) ? true : false;
//        if (!isPicture)
//            return false;
        return isPicture;
    }

    /**
     * To find out the extension of required object in given uri
     * Solution by http://stackoverflow.com/a/36514823/1171484
     */
    public String getMimeType(Uri uri) {
        String extension;
        //Check uri format to avoid null
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
            //If scheme is a content
            extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(this.getContentResolver().getType(uri));
            if (TextUtils.isEmpty(extension))
                extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
            if (TextUtils.isEmpty(extension))
                extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(this.getContentResolver().getType(uri));
        }
        if (TextUtils.isEmpty(extension)) {
            extension = getMimeTypeByFileName(getFileWithUri(uri).getName());
        }
        return extension;
    }

    public String getMimeTypeByFileName(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."), fileName.length());
    }


    /**
     * 通过URI获取文件的路径
     *
     * @param uri
     * @return Author JPH
     * Date 2016/6/6 0006 20:01
     */
    public String getFilePathWithUri(Uri uri) {
        if (uri == null) {
            Log.w(TAG, "uri is null,activity may have been recovered?");
            return "empty";
        }
        File picture = getFileWithUri(uri);
        String picturePath = picture == null ? null : picture.getPath();
        if (TextUtils.isEmpty(picturePath))
            return "empty";
        if (!checkMimeType(getMimeType(uri)))
            return "empty";
        return picturePath;
    }

    /**
     * 通过URI获取文件
     *
     * @param uri
     * @return Author JPH
     * Date 2016/10/25
     */
    public File getFileWithUri(Uri uri) {
        String picturePath = null;
        String scheme = uri.getScheme();
        if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = this.getContentResolver().query(uri,
                    filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            if (columnIndex >= 0) {
                picturePath = cursor.getString(columnIndex);  //获取照片路径
            } else if (TextUtils.equals(uri.getAuthority(), getFileProviderName())) {
                picturePath = parseOwnUri(uri);
            }
            cursor.close();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            picturePath = uri.getPath();
        }
        return TextUtils.isEmpty(picturePath) ? null : new File(picturePath);
    }

    /**
     * 将TakePhoto 提供的Uri 解析出文件绝对路径
     *
     * @param uri
     * @return
     */
    public String parseOwnUri(Uri uri) {
        if (uri == null) return null;
        String path;
        if (TextUtils.equals(uri.getAuthority(), getFileProviderName())) {
            path = new File(uri.getPath().replace("camera_photos/", "")).getAbsolutePath();
        } else {
            path = uri.getPath();
        }
        return path;
    }


    public String getFileProviderName() {
        return this.getPackageName() + ".fileprovider";
    }


}
