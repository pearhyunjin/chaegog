package com.example.finalprojectvegan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalprojectvegan.Adapter.GalleryAdapter;
import com.example.finalprojectvegan.Model.WriteReviewInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapTabPhoto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapTabPhoto extends Fragment {
    RecyclerView recyclerView;
    GalleryAdapter adapter;
    GridLayoutManager gridLayoutManager;
    String name;

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

        Bundle bundle = getArguments();
        name = bundle.getString("name");


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("review")
                .whereEqualTo("name", name) // "name" 필드의 값이 클릭한 식당 이름과 같아야 불러옴
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<WriteReviewInfo> postList = new ArrayList<>();
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Log.d("success_review", documentSnapshot.getId() + " => " + documentSnapshot.getData());

                                postList.add(new WriteReviewInfo(
                                        documentSnapshot.getData().get("rating").toString(),
                                        documentSnapshot.getData().get("name").toString(),
                                        documentSnapshot.getData().get("review").toString(),
                                        documentSnapshot.getData().get("publisher").toString(),
                                        documentSnapshot.getData().get("imagePath1").toString(),
                                        new Date(documentSnapshot.getDate("createdAt").getTime())));
                            }
                            gridLayoutManager = new GridLayoutManager(getContext(), 3);
                            recyclerView = view.findViewById(R.id.recyclerGridView);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(gridLayoutManager);

                            adapter = new GalleryAdapter(getActivity(), postList);
                            recyclerView.setAdapter(adapter);

                        } else {
                            Log.d("error", "Error getting documents", task.getException());
                        }
                    }
                });
        return view;
    }
}