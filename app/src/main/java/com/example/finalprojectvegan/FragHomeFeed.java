package com.example.finalprojectvegan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

public class FragHomeFeed extends Fragment {
    TabLayout tabs;
    FragmentTransaction transaction;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FragHomeFeed() {
        // Required empty public constructor
    }

    public static FragHomeFeed newInstance(String param1, String param2) {
        FragHomeFeed fragment = new FragHomeFeed();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_frag_home_feed, container, false);
        tabs = view.findViewById(R.id.tabs);
        Fragment fragment_homefeed = new FragHomeFeed();
        Fragment fragment_recipe = new FragHomeRecipe();
        Fragment fragment_product = new FragHomeProduct();

        //getChildFragmentManager().beginTransaction().add(R.id.tabContents, fragment_homefeed).commit();

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //transaction = getChildFragmentManager().beginTransaction();
//                switch (tab.getId()){
//                    case R.id.tab_home:
//                        transaction.replace(R.id.tabContents, fragment_homefeed).commit();
//
//                    case R.id.tab_recipe:
//                        transaction.replace(R.id.tabContents, fragment_recipe).commit();
//
//                    case R.id.tab_product:
//                        transaction.replace(R.id.tabContents, fragment_product).commit();
//                }

//                transaction.commit();

                int position = tab.getPosition();
                Fragment selected = null;
                if(position == 1)
                    selected = fragment_recipe;
                else if(position == 2)
                    selected = fragment_product;
                getChildFragmentManager().beginTransaction().replace(R.id.tabContents, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }
}