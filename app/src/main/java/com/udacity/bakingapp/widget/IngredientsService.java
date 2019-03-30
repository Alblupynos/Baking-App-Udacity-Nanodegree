package com.udacity.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class IngredientsService extends RemoteViewsService {

    public static final String EXTRA_RECIPE_ID = "recipe_id";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        long recipeId = intent.getLongExtra(EXTRA_RECIPE_ID, -1);
        return new IngredientsRemoteViewFactory(getApplicationContext(), recipeId);
    }
}
