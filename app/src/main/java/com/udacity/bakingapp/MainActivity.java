package com.udacity.bakingapp;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.udacity.bakingapp.adapter.RecipeAdapter;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.utils.Utils;
import com.udacity.bakingapp.viewmodel.MainViewModel;
import com.udacity.bakingapp.widget.IngredientsWidget;
import com.udacity.bakingapp.widget.WidgetPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.OnClickHandler {

    private MainViewModel mViewModel;
    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    @BindView(R.id.tv_error_message_display) TextView errorMessageDisplay;
    @BindView(R.id.pb_loading_indicator) ProgressBar loadingIndicator;

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Nullable
    private CountingIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new CountingIdlingResource("LoadList");
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        RecipeAdapter recipeAdapter = new RecipeAdapter(this);
        if (Utils.isTablet(this)) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        recyclerView.setAdapter(recipeAdapter);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        getIdlingResource();
        if (mIdlingResource != null) {
            mIdlingResource.increment();
        }
        mViewModel.getRecipes().observe(this, recipes -> {
            loadingIndicator.setVisibility(View.INVISIBLE);
            if (recipes == null) {
                recyclerView.setVisibility(View.INVISIBLE);
                errorMessageDisplay.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                errorMessageDisplay.setVisibility(View.INVISIBLE);
                recipeAdapter.setData(recipes);
            }
            if (mIdlingResource != null) {
                mIdlingResource.decrement();
            }
        });
        // Find the widget id from the intent.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            if (mAppWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                setResult(RESULT_CANCELED);
            }
        }
    }

    @Override
    public void onClick(Recipe recipe) {
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            startActivity(new Intent(this, RecipeActivity.class)
                    .putExtra(RecipeActivity.EXTRA_RECIPE, recipe));
        } else {
            WidgetPrefs.saveRecipe(this, mAppWidgetId, recipe);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            IngredientsWidget.updateAppWidget(this, appWidgetManager, mAppWidgetId);

            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    }
}
