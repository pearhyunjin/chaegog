package com.example.finalprojectvegan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder>{
    private Context context;
    private List<BookmarkData> bookmarkList;

    public GalleryAdapter(Context context, List<BookmarkData> bookmarkList) {
        this.context = context;
        this.bookmarkList = bookmarkList;
    }
    @NonNull
    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.gallery_item, parent, false);
        return new GalleryAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull GalleryAdapter.ViewHolder holder, int position) {


        String imgURL = bookmarkList.get(position).getStoreImage();
        Glide.with(holder.itemView)
                .load(imgURL)
                .override(150,150)
                .apply(new RequestOptions().transform(new CenterCrop()))
                .into(holder.image);
    }

    @Override
    public int getItemCount() {

        return bookmarkList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.gallery_item_view);
        }
    }
}
