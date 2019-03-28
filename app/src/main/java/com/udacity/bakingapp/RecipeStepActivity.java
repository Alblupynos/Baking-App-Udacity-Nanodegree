package com.udacity.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.udacity.bakingapp.model.Recipe;

public class RecipeStepActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE = "recipe";
    public static final String EXTRA_STEP_POS = "step_pos";

    private Recipe mRecipe;
    private int mStepPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_RECIPE)) {
            mRecipe = intent.getParcelableExtra(EXTRA_RECIPE);
            mStepPos = intent.getIntExtra(EXTRA_STEP_POS, 0);
            setTitle(mRecipe.getSteps().get(mStepPos).getShortDescription());
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.f_recipe_step, RecipeStepFragment.newInstance(mRecipe, mStepPos))
                        .commit();
            }
        }
    }
}
