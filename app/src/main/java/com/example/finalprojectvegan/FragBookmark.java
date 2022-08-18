package com.example.finalprojectvegan;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
public class FragBookmark extends Fragment {
    String[] items = {"식당", "제품", "레시피"};
    Fragment bookmark1;
    Fragment bookmark2;
    Fragment bookmark3;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FragBookmark() {
        // Required empty public constructor
    }

    public static FragBookmark newInstance(String param1, String param2) {
        FragBookmark fragment = new FragBookmark();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_bookmark, container, false);

        bookmark1 = new FragBookmark1();
        bookmark2 = new FragBookmark2();
        bookmark3 = new FragBookmark3();

        Spinner spinner = view.findViewById(R.id.bookmarkSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (items[i]) {
                    case "식당":
                        getChildFragmentManager().beginTransaction().replace(R.id.bookmark_layout, bookmark1).commitAllowingStateLoss();
                        return;
                    case "제품":
                        getChildFragmentManager().beginTransaction().replace(R.id.bookmark_layout, bookmark2).commitAllowingStateLoss();
                        return;
                    case "레시피":
                        getChildFragmentManager().beginTransaction().replace(R.id.bookmark_layout, bookmark3).commitAllowingStateLoss();
                        return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                getChildFragmentManager().beginTransaction() .replace(R.id.main_layout, bookmark1).commitAllowingStateLoss();
                return;
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}