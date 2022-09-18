package com.example.finalprojectvegan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragBookmark1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragBookmark1 extends Fragment {
    RecyclerView recyclerView;
    BookmarkAdapter adapter;

    private BookmarkItem bookmarkList;
    private List<BookmarkData> bookmarkInfo;

    CheckBox BookmarkCheckbox;
    TextView BmNameTv, BmAddrTv;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragBookmark1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragBookmark1.
     */
    // TODO: Rename and change types and number of parameters
    public static FragBookmark1 newInstance(String param1, String param2) {
        FragBookmark1 fragment = new FragBookmark1();
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
        View view = inflater.inflate(R.layout.fragment_frag_bookmark1, container, false);
        BookmarkCheckbox = view.findViewById(R.id.bookmarkFavorite);
        BmNameTv = view.findViewById(R.id.bookmarkNameTv);

        bookmarkInfo = new ArrayList<>();
        recyclerView = view.findViewById(R.id.bookmark1_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        BookmarkApiInterface bookmarkApiInterface = NaverMapRequest.getClient().create(BookmarkApiInterface.class);
        Call<BookmarkItem> call = bookmarkApiInterface.getBookmarkData();
        call.enqueue(new Callback<BookmarkItem>() {
            @Override
            public void onResponse(Call<BookmarkItem> call, Response<BookmarkItem> response) {
                bookmarkList = response.body();
                bookmarkInfo = bookmarkList.USER_BOOKMARK;


                adapter = new BookmarkAdapter(getContext(), bookmarkInfo);
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