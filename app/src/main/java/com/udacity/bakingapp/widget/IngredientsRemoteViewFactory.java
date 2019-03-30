package com.udacity.bakingapp.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.api.AppApis;
import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.Recipe;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class IngredientsRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private long mRecipeId;
    private List<Ingredient> mData;

    public IngredientsRemoteViewFactory(Context context, long recipeId) {
        this.mContext = context;
        this.mRecipeId = recipeId;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        try {
            Response<List<Recipe>> response = AppApis.getInstance().bakingApi().listRecipes().execute();
            if (response.isSuccessful()) {
                for (Recipe recipe: response.body()) {
                    if (recipe.getId() == mRecipeId) {
                        mData = recipe.getIngredients();
                        break;
                    }
                }
            } else {
                mData = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            mData = null;
        }
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.list_item_ingredient_widget);
        Ingredient ingredient = mData.get(position);
        views.setTextViewText(R.id.tv_name, "• " + ingredient.getIngredient());
        views.setTextViewText(R.id.tv_count, ingredient.getQuantity() + " " + ingredient.getMeasure());
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
