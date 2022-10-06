package com.example.finalprojectvegan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapTabMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapTabMenu extends Fragment {
    TextView menuTextView;
    String menu;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MenuAdapter adapter;

    private NaverMapItem menuList;
    private List<NaverMapData> menuInfo;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MapTabMenu() {
        // Required empty public constructor
    }

    public static MapTabMenu newInstance(String param1, String param2) {
        MapTabMenu fragment = new MapTabMenu();
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
        View view = inflater.inflate(R.layout.fragment_map_tab_menu, container, false);
        recyclerView = view.findViewById(R.id.menu_recyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        Bundle bundle = getArguments();
        menu = bundle.getString("menu");
        Log.d("MENU", menu);

        String[] menuArr = menu.split(",");

        adapter = new MenuAdapter(menuArr);
        recyclerView.setAdapter(adapter);

        return view;
    }
}