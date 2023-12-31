package com.example.finalprojectvegan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalprojectvegan.Adapter.RecipeAdapter;
import com.example.finalprojectvegan.Model.RecipeData;
import com.example.finalprojectvegan.Model.RecipeItem;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragHomeRecipe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragHomeRecipe extends Fragment {
    RecyclerView recyclerView;
    RecipeAdapter adapter;
    private RecipeItem recipeList;
    private List<RecipeData> recipeInfo;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragHome_recipe.
     */
    // TODO: Rename and change types and number of parameters
    public static FragHomeRecipe newInstance(String param1, String param2) throws IOException, ParserConfigurationException, SAXException {
        FragHomeRecipe fragment = new FragHomeRecipe();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static FragHomeRecipe newInstance() {
        return new FragHomeRecipe();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_home_recipe, container, false);


        recipeInfo = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recipe_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecipeApiInterface recipeApiInterface = NaverMapRequest.getClient().create(RecipeApiInterface.class);
        Call<RecipeItem> call = recipeApiInterface.getRecipeData();
        call.enqueue(new Callback<RecipeItem>() {
            @Override
            public void onResponse(Call<RecipeItem> call, Response<RecipeItem> response) {
                recipeList = response.body();
                recipeInfo = recipeList.RECIPE;



                adapter = new RecipeAdapter(getContext(), recipeInfo);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<RecipeItem> call, Throwable t) {
                Log.d("HomeRecipeFragment", t.toString());
            }

        });
        return view;
    }
}