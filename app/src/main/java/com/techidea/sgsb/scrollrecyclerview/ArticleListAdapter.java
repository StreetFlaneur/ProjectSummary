package com.techidea.sgsb.scrollrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techidea.sgsb.R;

import java.util.List;

/**
 * Created by zc on 2017/9/10.
 */

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.NormalViewHolder> {

    private List<ContentItem> contentItems;
    private Context context;

    public ArticleListAdapter(RecyclerView recyclerView, List<ContentItem> contentItems) {
        this.contentItems = contentItems;
        this.context = recyclerView.getContext();
    }

    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(this.context).inflate(R.layout.view_article_item, parent, false);
        return new NormalViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(NormalViewHolder holder, int position) {
        ContentItem contentItem = contentItems.get(position);
        holder.textViewContent.setText(contentItem.getContent());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return contentItems == null ? 0 : contentItems.size();
    }

    static class NormalViewHolder extends RecyclerView.ViewHolder {
        TextView textViewContent;

        public NormalViewHolder(View itemView) {
            super(itemView);
            textViewContent = (TextView) itemView.findViewById(R.id.textview_content_item);
        }
    }
}
