package org.maxcrone.news;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    private String[] mDataset;

    public NewsListAdapter() {
        mDataset = new String[10];
        mDataset[0] = "Impeach Trump: why Republicans, not Democrats are talking up the prospect";
        mDataset[1] = "Sex, lies and tabloids: hush payments to women that spell danger for Trump";
        mDataset[2] = "Tokyo fears losing a part of its soul as world’s biggest fish market moves";
        mDataset[3] = "Pope in Ireland: Francis Meets Church Abuse Victims";
        mDataset[4] = "An N.Y.U. Sexual-Harassment Case Has Spurred a Necessary Conversation About #MeToo";
        mDataset[5] = "Impeach Trump: why Republicans, not Democrats are talking up the prospect";
        mDataset[6] = "Sex, lies and tabloids: hush payments to women that spell danger for Trump";
        mDataset[7] = "Tokyo fears losing a part of its soul as world’s biggest fish market moves";
        mDataset[8] = "Pope in Ireland: Francis Meets Church Abuse Victims";
        mDataset[9] = "An N.Y.U. Sexual-Harassment Case Has Spurred a Necessary Conversation About #MeToo";
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
        viewHolder.mTitle.setText(mDataset[i]);
        viewHolder.mSrc.setText("The Guardian");
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
