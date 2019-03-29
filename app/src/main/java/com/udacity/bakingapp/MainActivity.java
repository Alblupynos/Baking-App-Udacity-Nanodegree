package com.udacity.bakingapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.OnClickHandler {

    private MainViewModel mViewModel;
    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    @BindView(R.id.tv_error_message_display) TextView errorMessageDisplay;
    @BindView(R.id.pb_loading_indicator) ProgressBar loadingIndicator;

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
        });
    }

    @Override
    public void onClick(Recipe recipe) {
        startActivity(new Intent(this, RecipeActivity.class)
                .putExtra(RecipeActivity.EXTRA_RECIPE, recipe));
    }
}
