package com.example.finalprojectvegan;

import android.content.Context;
import android.content.Intent;
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
    private List<RecipeData> rList;
    private Intent intent;

    public RecipeAdapter(Context context, List<RecipeData> rList) {
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
        holder.name.setText(rList.get(position).getRecipeName());
        holder.type.setText(rList.get(position).getRecipeType());
        holder.way.setText(rList.get(position).getRecipeWay());

        String recipeName = rList.get(position).getRecipeName();
        String recipeType = rList.get(position).getRecipeType();
        String recipeWay = rList.get(position).getRecipeWay();
        String recipeIngre = rList.get(position).getRecipeIngredient();

        // 레시피 과정
        String manualImg01 = rList.get(position).getManualImage01();
        String manual01 = rList.get(position).getManual01();
        String manualImg02 = rList.get(position).getManualImage02();
        String manual02 = rList.get(position).getManual02();
        String manualImg03 = rList.get(position).getManualImage03();
        String manual03 = rList.get(position).getManual03();
        String manualImg04 = rList.get(position).getManualImage04();
        String manual04 = rList.get(position).getManual04();
        String manualImg05 = rList.get(position).getManualImage05();
        String manual05 = rList.get(position).getManual05();
        String manualImg06 = rList.get(position).getManualImage06();
        String manual06 = rList.get(position).getManual06();

        String recipeImgURL = rList.get(position).getRecipeImageMAIN();
        Glide.with(holder.itemView)
                .load(recipeImgURL)
                .override(400,200)
                .apply(new RequestOptions().transform(new CenterCrop(),
                        new RoundedCorners(20)))
                .into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(view.getContext(), RecipeDetailActivity.class);
                intent.putExtra("position", holder.getAdapterPosition());
                intent.putExtra("name", recipeName);
                intent.putExtra("image", recipeImgURL);
                intent.putExtra("type", recipeType);
                intent.putExtra("way", recipeWay);
                intent.putExtra("ingre", recipeIngre);
                // 레시피 과정
                intent.putExtra("m_Image01", manualImg01);
                intent.putExtra("manual01", manual01);
                intent.putExtra("m_Image02", manualImg02);
                intent.putExtra("manual02", manual02);
                intent.putExtra("m_Image03", manualImg03);
                intent.putExtra("manual03", manual03);
                intent.putExtra("m_Image04", manualImg04);
                intent.putExtra("manual04", manual04);
                intent.putExtra("m_Image05", manualImg05);
                intent.putExtra("manual05", manual05);
                intent.putExtra("m_Image06", manualImg06);
                intent.putExtra("manual06", manual06);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return rList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView type;
        TextView way;
        TextView name;
        ImageView image, image2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            type = (TextView)itemView.findViewById(R.id.recipe_type);
            way = (TextView)itemView.findViewById(R.id.recipe_way);
            name = (TextView)itemView.findViewById(R.id.recipe_name);
            image = (ImageView)itemView.findViewById(R.id.recipe_image);
            image2 = (ImageView)itemView.findViewById(R.id.recipe_detail_image);

        }
    }
}
