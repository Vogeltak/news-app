package org.maxcrone.news.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.maxcrone.news.R;
import org.maxcrone.news.data.Article;
import org.maxcrone.news.network.NewsApi;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    private Article[] mDataset;

    public NewsListAdapter(Article[] data) {
        mDataset = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Create a new view according to the xml layout for a news article list item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);

        // Use the above view to create a new view holder
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    /* Replace the contents of a view (invoked by layout manager) */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mTitle.setText(mDataset[i].getTitle());
        viewHolder.mSrc.setText(mDataset[i].getSrc());
        //viewHolder.mDate.setText("August 26, 2018");
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView mTitle;
        public TextView mSrc;
        //public TextView mDate;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            mTitle = mView.findViewById(R.id.articleTitle);
            mSrc = mView.findViewById(R.id.articleSource);
            //mDate = mView.findViewById(R.id.articleDate);
        }
    }
}
