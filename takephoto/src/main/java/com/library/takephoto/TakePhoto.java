package com.library.takephoto;

import android.net.Uri;

/**
 * Created by sam on 2018/5/17.
 */

public interface TakePhoto {

    void onPickFromGallery();

    void onPickFromGalleryWithCrop(Uri outPutUrim);

    void onPickFromDocuments();

    void onPickFromDocumentsWidthCrop();
}
