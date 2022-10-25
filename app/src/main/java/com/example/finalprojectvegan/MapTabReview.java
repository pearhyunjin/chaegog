package com.example.finalprojectvegan;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.finalprojectvegan.Adapter.ReviewAdapter;
import com.example.finalprojectvegan.Model.WriteReviewInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapTabReview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapTabReview extends Fragment {
    private FirebaseUser firebaseUser;
    String name;
    String restName;
    String ee;
    int cc=0;
    private HashMap<String, Object> hashMap = new HashMap<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MapTabReview() {
        // Required empty public constructor
    }

    public static MapTabReview newInstance(String param1, String param2) {
        MapTabReview fragment = new MapTabReview();
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
        View view = inflater.inflate(R.layout.fragment_map_tab_review, container, false);

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


                                    //String aa = documentSnapshot.getData().get("rating").toString();
                                    //String aa = ratings.get().toString();
                                    //float bb = Float.parseFloat(aa);
                                    //float cc = bb / documentSnapshot.getData().size();
                                    //Log.d("ratingbar",  "별점 "+ aa + "점");
                                    //Log.d("ratingbar",  documentSnapshot.getData().get("name").toString());
//                                    bb += bb;
//                                    for(int i=0; i < documentSnapshot.getData().size(); i++) {
//                                        if (ratings.equals(name)) {
//                                            cc += 1;
//
//                                            Log.d("cc는", cc + "");
//
//                                        }
//                                        break;
//                                    }
//                                    Log.d("ratingbar", "bb는" + bb + "");
//                                    float rr = bb / cc;
//
//                                    ee = "평균 별점 "+ rr + "점";

                                }
//                                if (ee != null) {
//                                    Log.d("ratingbar", ee);
//                                }


                                    RecyclerView recyclerView = view.findViewById(R.id.review_recyclerview);
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                                    RecyclerView.Adapter mAdapter = new ReviewAdapter(getActivity(), postList);
                                    recyclerView.setAdapter(mAdapter);

                            } else {
                                Log.d("error", "Error getting documents", task.getException());
                            }
                        }
                    });



        TextView review_button = view.findViewById(R.id.review_button);
        review_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),WriteReviewActivity.class); //fragment라서 activity intent와는 다른 방식
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });

        return view;
    }
}