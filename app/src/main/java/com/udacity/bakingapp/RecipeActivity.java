package com.udacity.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.utils.Utils;

public class RecipeActivity extends AppCompatActivity implements RecipeFragment.OnStepClickListener {

    public static final String EXTRA_RECIPE = "recipe";

    private static final String STATE_STEP_POS = "step_pos";

    private Recipe mRecipe;
    private int mStepPos = -1;

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
        if (Utils.isTablet(this)) {
            if (!Utils.isLandscape(this)) {
                Fragment recipeStep = getSupportFragmentManager().findFragmentById(R.id.f_recipe_step);
                if (recipeStep != null) {
                    getSupportFragmentManager().beginTransaction()
                            .remove(recipeStep)
                            .commit();
                }
            }
            if (savedInstanceState != null) {
                mStepPos = savedInstanceState.getInt(STATE_STEP_POS, -1);
                if (mStepPos != -1) {
                    onStepClick(mStepPos);
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_STEP_POS, mStepPos);
    }

    @Override
    public void onStepClick(int stepPos) {
        mStepPos = stepPos;
        if (Utils.isTablet(this) && Utils.isLandscape(this)) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.f_recipe_step, RecipeStepFragment.newInstance(mRecipe, mStepPos))
                    .commit();
        } else {
            startActivity(new Intent(this, RecipeStepActivity.class)
                    .putExtra(RecipeStepActivity.EXTRA_RECIPE, mRecipe)
                    .putExtra(RecipeStepActivity.EXTRA_STEP_POS, stepPos));
        }
    }
}
