package com.example.finalprojectvegan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder>{
    private Context context;
    private List<BookmarkData> bookmarkList;
    private String loginUserID;

    public BookmarkAdapter(Context context, List<BookmarkData> restList, String loginUserID) {
        this.context = context;
        this.bookmarkList = restList;
        this.loginUserID = loginUserID;
    }
    @NonNull
    @Override
    public BookmarkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.bookmark1_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.onBind(dataList.get(position));
        for(int i=0; i < bookmarkList.size(); i++){
            if(bookmarkList.get(position).getUserID().equals(loginUserID)){
                holder.name.setText(bookmarkList.get(position).getStoreName());
                holder.address.setText("" + bookmarkList.get(position).getStoreAddr());
            }else{
                Log.d("bookmark", "실패");
            }
        }

//            LoginID = ((MapInfoActivity)MapInfoActivity.context).id;
//        if (LoginID.equals(bookmarkList.get(position).getUserID())){
//            holder.name.setText(bookmarkList.get(position).getStoreName());
//            holder.address.setText("" + bookmarkList.get(position).getStoreAddr());
//        }else{
//            holder.name.setText("실패");
//            holder.address.setText("실패");
//            Log.d("bookmark", LoginID);
//        }


//        String imgURL = bookmarkList.get(position).getStoreImage();
//        Glide.with(holder.itemView)
//                .load(imgURL)
//                .override(300,400)
//                .apply(new RequestOptions().transform(new CenterCrop(),
//                        new RoundedCorners(20)))
//                .into(holder.image);
    }

    @Override
    public int getItemCount() {

        return bookmarkList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView address;
        //ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.bookmarkNameTv);
            address = (TextView)itemView.findViewById(R.id.bookmarkAddrTv);
            //image = (ImageView)itemView.findViewById(R.id.bookmarkImageView);
        }
    }
}
