package vn.uits.bllutooth_android;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Copyright © 2017 BAP CO., LTD
 * Created by PHUQUY on 3/1/18.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.StudentHolder> {

    private List<String> mList;

    public RecycleViewAdapter(List<String> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewhoder_item, null);
        return new StudentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentHolder holder, int position) {
        holder.mTvName.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * view holder
     */
    public class StudentHolder extends RecyclerView.ViewHolder {

        private TextView mTvName;

        public StudentHolder(View itemView) {
            super(itemView);
            mTvName = itemView.findViewById(R.id.mTvName);
        }
    }
}
