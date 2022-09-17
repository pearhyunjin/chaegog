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

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private Context context;
    private List<RecipeItem> rList;

    public RecipeAdapter(Context context, List<RecipeItem> rList) {
        this.context = context;
        this.rList = rList;
    }
    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false);
        return new RecipeAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder holder, int position) {
//        holder.type.setText(rList.get(position).getRcpPat2());
//        holder.name.setText(rList.get(position).getRcpNm());
//
//        String imgURL = rList.get(position).getAttFileNoMk();
//        Glide.with(holder.itemView)
//                .load(imgURL)
//                .override(100,100)
//                .apply(new RequestOptions().transform(new CenterCrop(),
//                        new RoundedCorners(20)))
//                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return rList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView type;
        TextView name;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            type = (TextView)itemView.findViewById(R.id.recipe_type);
            name = (TextView)itemView.findViewById(R.id.recipe_name);
            image = (ImageView)itemView.findViewById(R.id.recipe_image);
        }
    }
}
