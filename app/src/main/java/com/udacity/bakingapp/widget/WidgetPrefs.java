package com.udacity.bakingapp.widget;

import android.content.Context;

import com.udacity.bakingapp.model.Recipe;

public class WidgetPrefs {

    private static final String PREFS_NAME = "com.udacity.bakingapp.IngredientsWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    private static final String PREF_TITLE = "_title";
    private static final String PREF_ID = "_id";

    public static void saveRecipe(Context context, int appWidgetId, Recipe recipe) {
        context.getSharedPreferences(PREFS_NAME, 0).edit()
                .putString(PREF_PREFIX_KEY + appWidgetId + PREF_TITLE, recipe.getName())
                .putLong(PREF_PREFIX_KEY + appWidgetId + PREF_ID, recipe.getId())
                .apply();
    }

    public static String loadRecipeName(Context context, int appWidgetId) {
        return context.getSharedPreferences(PREFS_NAME, 0)
                .getString(PREF_PREFIX_KEY + appWidgetId + PREF_TITLE, "");
    }

    public static long loadRecipeId(Context context, int appWidgetId) {
        return context.getSharedPreferences(PREFS_NAME, 0)
                .getLong(PREF_PREFIX_KEY + appWidgetId + PREF_ID, -1);
    }

    public static void deleteRecipe(Context context, int appWidgetId) {
        context.getSharedPreferences(PREFS_NAME, 0).edit()
                .remove(PREF_PREFIX_KEY + appWidgetId + PREF_TITLE)
                .remove(PREF_PREFIX_KEY + appWidgetId + PREF_ID)
                .apply();
    }
}
