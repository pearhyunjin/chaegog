package com.example.finalprojectvegan.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalprojectvegan.R;
import com.example.finalprojectvegan.Model.UserInfo;
import com.example.finalprojectvegan.Model.WritePostInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MypageAdapter extends RecyclerView.Adapter<MypageAdapter.ViewHolder>{

    Fragment fragment;
    private Context context;
    private ArrayList<WritePostInfo> mDataset;

    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    ImageView mypage_item_imageView;
    ImageView imageView_profile2;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;

        public ViewHolder(CardView view) {
            super(view);
            cardView = view;
        }
    }

    public MypageAdapter(Context context, ArrayList<WritePostInfo> myDataset) {
        mDataset = myDataset;
        this.context = context;
    }

    @NonNull
    @Override
    public MypageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            // Name, email address, and profile photo Url
//            String name = user.getDisplayName();
//            String email = user.getEmail();
//            Uri photoUrl = user.getPhotoUrl();
//
//            // Check if user's email is verified
//            boolean emailVerified = user.isEmailVerified();
//
//            // The user's ID, unique to the Firebase project. Do NOT use this value to
//            // authenticate with your backend server, if you have one. Use
//            // FirebaseUser.getIdToken() instead.
//            String uid = user.getUid();
//        }

        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(cardView);
        mypage_item_imageView = cardView.findViewById(R.id.mypage_item_imageView);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        String user = mDataset.get(position).getPublisher();

//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (firebaseUser == user) {
//            // Name, email address, and profile photo Url
//            String name = firebaseUser.getDisplayName();
//            String email = firebaseUser.getEmail();
//            Uri photoUrl = firebaseUser.getPhotoUrl();
//
//            // Check if user's email is verified
//            boolean emailVerified = firebaseUser.isEmailVerified();
//
//            // The user's ID, unique to the Firebase project. Do NOT use this value to
//            // authenticate with your backend server, if you have one. Use
//            // FirebaseUser.getIdToken() instead.
//            String uid = firebaseUser.getUid();
//        }
        CardView cardView = holder.cardView;

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

//                            ArrayList<UserInfo> postUserList = new ArrayList<>();

//                            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            if (firebaseUser != null) {

                                String uid = firebaseUser.getUid();
                                String user = mDataset.get(holder.getAdapterPosition()).getPublisher();

                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                    Log.d("success", documentSnapshot.getId() + " => " + documentSnapshot.getData());

//                                    postUserList.add(new UserInfo(
//                                            documentSnapshot.getData().get("userID").toString(),
//                                            documentSnapshot.getData().get("userEmail").toString(),
//                                            documentSnapshot.getData().get("userPassword").toString()));

//                                    TextView publisherTextView = cardView.findViewById(R.id.mypage_item_publisher);
                                    ImageView imageView_profile2 = cardView.findViewById(R.id.imageView_profile2);
//        publisherTextView.setText(mDataset.get(position).getPublisher());
//                                    String user = mDataset.get(holder.getAdapterPosition()).getPublisher();
//                                publisherTextView.setText(documentSnapshot.getData().get("userID").toString());
                                    if (documentSnapshot.getId().equals(user)) {
                                        if (documentSnapshot.getId().equals(uid)) {
                                            ArrayList<UserInfo> postUserList = new ArrayList<>();

                                            postUserList.add(new UserInfo(
                                                    documentSnapshot.getData().get("userID").toString(),
                                                    documentSnapshot.getData().get("userEmail").toString(),
                                                    documentSnapshot.getData().get("userPassword").toString()));

                                            cardView.setVisibility(View.VISIBLE);
                                            ViewGroup.LayoutParams params = cardView.getLayoutParams();
                                            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                                            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                            cardView.setLayoutParams(params);

//                                            publisherTextView.setText(documentSnapshot.getData().get("userID").toString());

                                            TextView titleTextView = cardView.findViewById(R.id.mypage_item_title);
                                            titleTextView.setText(mDataset.get(holder.getAdapterPosition()).getTitle());

                                            TextView createdAtTextView = cardView.findViewById(R.id.mypage_item_createdAt);
                                            createdAtTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(mDataset.get(holder.getAdapterPosition()).getCreatedAt()));

                                            TextView contentsTextView = cardView.findViewById(R.id.mypage_item_contents);
                                            contentsTextView.setText(mDataset.get(holder.getAdapterPosition()).getContents());

                                            String url = mDataset.get(holder.getAdapterPosition()).getImagePath();

                                            Glide.with(cardView).load(url).override(800, 800).into(mypage_item_imageView);

//                                        loadImage(uid);
                                        } else {
                                            cardView.setVisibility(View.GONE);
                                            ViewGroup.LayoutParams params = cardView.getLayoutParams();
                                            params.height = 0;
                                            params.width = 0;
                                            cardView.setLayoutParams(params);
                                        }
                                    }
//        if (user == FirebaseAuth.getInstance().getCurrentUser().toString()) {
//            publisherTextView.setText(user);
//            Log.d("user", user);
//        }
                                }
                            }

                        } else {
                            Log.d("error", "Error getting documents", task.getException());
                        }
                    }
                });

//        CardView cardView = holder.cardView;
//
//        TextView publisherTextView = cardView.findViewById(R.id.homefeed_item_publisher);
////        publisherTextView.setText(mDataset.get(position).getPublisher());
//        String user = mDataset.get(position).getPublisher();
////        if (user == FirebaseAuth.getInstance().getCurrentUser().toString()) {
////            publisherTextView.setText(user);
////            Log.d("user", user);
////        }

//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        imageView_profile = cardView.findViewById(R.id.imageView_profile);
//
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageReference = storage.getReference();
//        StorageReference pathReference = storageReference.child("users");
//
//        if (pathReference == null) {
//            Toast.makeText(context, "저장소에 사진이 없습니다.", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "저장소에 사진이 있습니다.", Toast.LENGTH_SHORT).show();
//            StorageReference submitProfile = storageReference.child("users/" + firebaseUser.getUid() + "/profileImage.jpg");
//            submitProfile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
////                    Glide.with(cardView).load(uri).into(imageView_profile);
//                    Glide.with(context).load(uri).into(im)
////                    Toast.makeText(context, "사진 출력", Toast.LENGTH_SHORT).show();
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//
//                }
//            });
//        }

//        TextView titleTextView = cardView.findViewById(R.id.homefeed_item_title);
//        titleTextView.setText(mDataset.get(position).getTitle());
//
//        TextView createdAtTextView = cardView.findViewById(R.id.homefeed_item_createdAt);
//        createdAtTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(mDataset.get(position).getCreatedAt()));
//
//        TextView contentsTextView = cardView.findViewById(R.id.homefeed_item_contents);
//        contentsTextView.setText(mDataset.get(position).getContents());
//
//        String url = mDataset.get(position).getImagePath();
//
//        Glide.with(cardView).load(url).override(800, 800).into(homefeed_item_imageView);
////        loadImage();


    }

//    public void loadImage(String a) {
////        imageView_profile = (ImageView) findViewById(R.id.imageView_profile);
//
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        a = firebaseUser.getUid();
//
//        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
//        StorageReference storageReference = firebaseStorage.getReference();
//        StorageReference pathReference = storageReference.child("users");
//        if (pathReference == null) {
//            Toast.makeText(context, "저장소에 사진이 없습니다.", Toast.LENGTH_SHORT).show();
//        } else {
//            StorageReference submitProfile = storageReference.child("users/" + a + "/profileImage.jpg");
//            submitProfile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
//                    Glide.with(context).load(uri).centerCrop().override(300).into(imageView_profile);
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//
//                }
//            });
//        }
//    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

//    public void loadImage() {
//
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
//        StorageReference storageReference = firebaseStorage.getReference();
//        StorageReference pathReference = storageReference.child("posts");
//        if (pathReference == null) {
//
//        } else {
//            StorageReference submitProfile = storageReference.child("posts/" + firebaseUser.getUid() + "/postImage" + System.currentTimeMillis() + ".jpg");
//            submitProfile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
//                    Glide.with(context).load(uri).into(homefeed_item_imageView);
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//
//                }
//            });
//        }
//    }

}
