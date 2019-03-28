package com.udacity.bakingapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeStepFragment extends Fragment {

    private static final String ARG_RECIPE = "recipe";
    private static final String ARG_STEP_POS = "step_pos";

    private Recipe mRecipe;
    private int mStepPos;

    @BindView(R.id.tv_description)
    TextView tvDescription;

    public RecipeStepFragment() {
        // Required empty public constructor
    }

    public static RecipeStepFragment newInstance(Recipe recipe, int stepPos) {
        RecipeStepFragment fragment = new RecipeStepFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE, recipe);
        args.putInt(ARG_STEP_POS, stepPos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(ARG_RECIPE);
            mStepPos = getArguments().getInt(ARG_STEP_POS);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        ButterKnife.bind(this, view);
        if (mRecipe != null) {
            Step step = mRecipe.getSteps().get(mStepPos);
            tvDescription.setText(step.getDescription());
        }
        return view;
    }
}
