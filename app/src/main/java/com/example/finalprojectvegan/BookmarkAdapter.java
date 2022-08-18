package com.example.finalprojectvegan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder>{
    private Context context;
    private List<RestaurantData> restList;

    public BookmarkAdapter(Context context, List<RestaurantData> restList) {
        this.context = context;
        this.restList = restList;
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
        holder.name.setText(restList.get(position).getName());
        holder.address.setText("" + restList.get(position).getAddress());

        String imgURL = restList.get(position).getImageUrl();
        Glide.with(holder.itemView)
                .load(imgURL)
                .override(300,400)
                .apply(new RequestOptions().transform(new CenterCrop(),
                        new RoundedCorners(20)))
                .into(holder.image);
    }

    @Override
    public int getItemCount() {

        return restList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView address;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.bookmarkNameTv);
            address = (TextView)itemView.findViewById(R.id.bookmarkAddrTv);
            image = (ImageView)itemView.findViewById(R.id.bookmarkImageView);
        }
    }
}
