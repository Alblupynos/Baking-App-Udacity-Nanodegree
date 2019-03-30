package com.udacity.bakingapp;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.Step;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipeStepActivityTest {

    @Rule
    public ActivityTestRule<RecipeStepActivity> mActivityTestRule = new ActivityTestRule<>(RecipeStepActivity.class, false, false);

    private final String name = "Recipe";
    private final int ingredientCount = 5;
    private final int stepCount = 8;
    private final int stepPos = 1;
    private Recipe recipe;

    @Before
    public void createRecipe() {
        List<Ingredient> ingredients = new ArrayList<>();
        List<Step> steps = new ArrayList<>();
        for (int i = 0; i < ingredientCount; i++) {
            ingredients.add(new Ingredient(i, "UNIT", "Ingredient " + i));
        }
        for (int i = 0; i < stepCount; i++) {
            steps.add(new Step(i, "Short Description", "Long Description", "", ""));
        }
        recipe = new Recipe(0, name, ingredients, steps, 8, "");
    }

    private void launchActivity() {
        mActivityTestRule.launchActivity(new Intent()
                .putExtra(RecipeStepActivity.EXTRA_RECIPE, recipe)
                .putExtra(RecipeStepActivity.EXTRA_STEP_POS, stepPos));
    }

    @Test
    public void testRecipeStep() {
        launchActivity();
        // Check that title is displayed
        onView(withText(recipe.getSteps().get(stepPos).getShortDescription()))
                .check(matches(isDisplayed()));
        onView(withId(R.id.tv_description))
                .check(matches(withText(recipe.getSteps().get(stepPos).getDescription())));
    }
}
