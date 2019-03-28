package com.udacity.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.udacity.bakingapp.model.Recipe;

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
    public void onStepClick(int stepPos) {
        startActivity(new Intent(this, RecipeStepActivity.class)
                .putExtra(RecipeStepActivity.EXTRA_RECIPE, mRecipe)
                .putExtra(RecipeStepActivity.EXTRA_STEP_POS, stepPos));
    }
}
