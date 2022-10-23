package com.example.finalprojectvegan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class FragMypage extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FragMypage() {
        // Required empty public constructor
    }

    public static FragMypage newInstance(String param1, String param2) {
        FragMypage fragment = new FragMypage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static FragMypage newInstance() {
        return new FragMypage();
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
        View view =  inflater.inflate(R.layout.fragment_frag_mypage, container, false);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<WritePostInfo> postList = new ArrayList<>();

                            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                            String uid = firebaseUser.getUid();

                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Log.d("success", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                                postList.add(new WritePostInfo(
                                        documentSnapshot.getData().get("title").toString(),
                                        documentSnapshot.getData().get("contents").toString(),
                                        documentSnapshot.getData().get("publisher").toString(),
                                        documentSnapshot.getData().get("imagePath").toString(),
                                        new Date(documentSnapshot.getDate("createdAt").getTime())));

//                                if (documentSnapshot.getData().get("publisher").toString() == uid) {
//                                    RecyclerView recyclerView = view.findViewById(R.id.mypage_recyclerView);
//                                    recyclerView.setHasFixedSize(true);
//                                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//                                    RecyclerView.Adapter mAdapter = new MypageAdapter(getActivity(), postList);
//                                    recyclerView.setAdapter(mAdapter);
//                                }
                            }

                            RecyclerView recyclerView = view.findViewById(R.id.mypage_recyclerView);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                            RecyclerView.Adapter mAdapter = new MypageAdapter(getActivity(), postList);
                            recyclerView.setAdapter(mAdapter);

                        } else {
                            Log.d("error", "Error getting documents", task.getException());
                        }
                    }
                });

        return view;
    }
}