package com.udacity.bakingapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeVH> {

    private List<Recipe> mData;
    private final OnClickHandler mClickHandler;

    public RecipeAdapter(OnClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }

    public void setData(List<Recipe> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_recipe, viewGroup, false);
        return new RecipeVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeVH recipeVH, int position) {
        Recipe recipe = mData.get(position);
        recipeVH.name.setText(recipe.getName());
        recipeVH.ingredients.setText(String.valueOf(recipe.getIngredients().size()));
        recipeVH.steps.setText(String.valueOf(recipe.getSteps().size()));
        recipeVH.servings.setText(String.valueOf(recipe.getServings()));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class RecipeVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_name) TextView name;
        @BindView(R.id.tv_ingredients) TextView ingredients;
        @BindView(R.id.tv_steps) TextView steps;
        @BindView(R.id.tv_servings) TextView servings;

        public RecipeVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onClick(mData.get(getAdapterPosition()));
        }
    }

    public interface OnClickHandler {
        void onClick(Recipe recipe);
    }
}
