package com.example.finalprojectvegan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapTabPhoto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapTabPhoto extends Fragment {
    RecyclerView recyclerView;
    GalleryAdapter adapter;
    GridLayoutManager gridLayoutManager;

    private BookmarkItem bookmarkList;
    private List<BookmarkData> bookmarkInfo;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MapTabPhoto() {
        // Required empty public constructor
    }

    public static MapTabPhoto newInstance(String param1, String param2) {
        MapTabPhoto fragment = new MapTabPhoto();
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
        View view = inflater.inflate(R.layout.fragment_map_tab_photo, container, false);
        bookmarkInfo = new ArrayList<>();
        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView = view.findViewById(R.id.recyclerGridView);
        recyclerView.setLayoutManager(gridLayoutManager);


        BookmarkApiInterface bookmarkApiInterface = NaverMapRequest.getClient().create(BookmarkApiInterface.class);
        Call<BookmarkItem> call = bookmarkApiInterface.getBookmarkData();
        call.enqueue(new Callback<BookmarkItem>() {
            @Override
            public void onResponse(Call<BookmarkItem> call, Response<BookmarkItem> response) {
                bookmarkList = response.body();
                bookmarkInfo = bookmarkList.USER_BOOKMARK;


                adapter = new GalleryAdapter(getContext(), bookmarkInfo);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<BookmarkItem> call, Throwable t) {
                Log.d("bookmark1Fragment", t.toString());
            }

        });
        return view;
    }
}