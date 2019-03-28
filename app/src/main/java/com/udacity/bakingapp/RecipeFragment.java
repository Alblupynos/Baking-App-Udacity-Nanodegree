package com.udacity.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.bakingapp.adapter.IngredientAdapter;
import com.udacity.bakingapp.adapter.StepAdapter;
import com.udacity.bakingapp.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeFragment extends Fragment implements StepAdapter.OnClickHandler {

    private static final String ARG_RECIPE = "recipe";

    private Recipe mRecipe;

    private OnStepClickListener mListener;

    @BindView(R.id.rv_ingredients) RecyclerView rvIngredients;
    @BindView(R.id.rv_steps) RecyclerView rvSteps;

    public RecipeFragment() {
    }

    public static RecipeFragment newInstance(Recipe recipe) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(ARG_RECIPE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, view);

        if (mRecipe != null) {
            rvIngredients.setLayoutManager(new LinearLayoutManager(getContext()));
            rvIngredients.setAdapter(new IngredientAdapter(mRecipe.getIngredients()));
            rvSteps.setLayoutManager(new LinearLayoutManager(getContext()));
            rvSteps.setAdapter(new StepAdapter(mRecipe.getSteps(), this));
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStepClickListener) {
            mListener = (OnStepClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(int stepPos) {
        if (mListener != null) {
            mListener.onStepClick(stepPos);
        }
    }

    public interface OnStepClickListener {
        void onStepClick(int stepPos);
    }
}
