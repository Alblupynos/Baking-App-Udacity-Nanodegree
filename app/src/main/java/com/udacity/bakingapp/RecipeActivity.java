package com.udacity.bakingapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.Step;

public class RecipeActivity extends AppCompatActivity implements RecipeFragment.OnStepClickListener {

    public static final String EXTRA_RECIPE = "recipe";

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        if (getIntent().hasExtra(EXTRA_RECIPE)) {
            mRecipe = getIntent().getParcelableExtra(EXTRA_RECIPE);
            setTitle(mRecipe.getName());
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.f_recipe, RecipeFragment.newInstance(mRecipe))
                        .commit();
            }
        }
    }

    @Override
    public void onStepClick(Step step) {

    }
}
