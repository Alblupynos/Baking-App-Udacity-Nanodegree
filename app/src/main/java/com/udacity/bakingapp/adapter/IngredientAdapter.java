package com.udacity.bakingapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientVH> {

    private List<Ingredient> mData;

    public IngredientAdapter(List<Ingredient> data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public IngredientVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_ingredient, viewGroup, false);
        return new IngredientVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientVH ingredientVH, int position) {
        Ingredient ingredient = mData.get(position);
        ingredientVH.name.setText("• " + ingredient.getIngredient());
        ingredientVH.count.setText(ingredient.getQuantity() + " " + ingredient.getMeasure());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class IngredientVH extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name) TextView name;
        @BindView(R.id.tv_count) TextView count;

        public IngredientVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
