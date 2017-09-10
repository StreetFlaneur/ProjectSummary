package com.sam.projectsummary;

import com.sam.projectsummary.scrollrecyclerview.ContentItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zc on 2017/9/10.
 */

public class MockData {

    public static List<ContentItem> getArtilceItems() {
        List<ContentItem> contentItems = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ContentItem contentItem = new ContentItem();
            contentItem.setContent("文章" + i);
            contentItems.add(contentItem);
        }
        return contentItems;
    }
}
