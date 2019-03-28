package com.udacity.bakingapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepVH> {

    private List<Step> mData;
    private final OnClickHandler mClickHandler;

    public StepAdapter(List<Step> data, OnClickHandler mClickHandler) {
        this.mData = data;
        this.mClickHandler = mClickHandler;
    }

    @NonNull
    @Override
    public StepVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_step, viewGroup, false);
        return new StepVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepVH stepVH, int position) {
        Step step = mData.get(position);
        stepVH.step.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class StepVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_step) TextView step;

        public StepVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onClick(getAdapterPosition());
        }
    }

    public interface OnClickHandler {
        void onClick(int stepPos);
    }
}
