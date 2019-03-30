package com.udacity.bakingapp;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {

    @Rule
    public ActivityTestRule<RecipeActivity> mActivityTestRule = new ActivityTestRule<>(RecipeActivity.class, false, false);

    private final String name = "Recipe";
    private final int ingredientCount = 5;
    private final int stepCount = 8;
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
        mActivityTestRule.launchActivity(new Intent().putExtra(RecipeActivity.EXTRA_RECIPE, recipe));
    }

    @Test
    public void testLoadIngredients() {
        launchActivity();
        // Check adapter item count
        onView(withId(R.id.rv_ingredients))
                .check((view, noViewFoundException) ->
                        assertThat(((RecyclerView) view).getAdapter().getItemCount(), is(ingredientCount)));
    }

    @Test
    public void testLoadSteps() {
        launchActivity();
        // Check adapter item count
        onView(withId(R.id.rv_steps))
                .check((view, noViewFoundException) ->
                        assertThat(((RecyclerView) view).getAdapter().getItemCount(), is(stepCount)));

    }

    @Test
    public void testClickStep() {
        launchActivity();
        onView(withId(R.id.rv_steps))
                .perform(actionOnItemAtPosition(1, click()));
        // Check that title is displayed
        onView(withText(recipe.getSteps().get(1).getDescription()))
                .check(matches(isDisplayed()));
    }
}
