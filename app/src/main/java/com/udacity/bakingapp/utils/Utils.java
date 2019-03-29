package com.udacity.bakingapp.utils;

import android.content.Context;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class Utils {

    public static boolean isTablet(Context context) {
        return context.getResources().getConfiguration().smallestScreenWidthDp >= 600;
    }

    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE;
    }

}
