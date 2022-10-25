package com.example.finalprojectvegan;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.finalprojectvegan.Model.ProductData;

import java.util.List;

class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    private List<ProductData> pList;

    public ProductAdapter(Context context,List<ProductData> pList) {
        this.context = context;
        this.pList = pList;
    }
    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.company.setText(pList.get(position).getProductCompany());
        holder.name.setText(pList.get(position).getProductName());

        String imgURL = pList.get(position).getProductImage();
        Glide.with(holder.itemView)
                .load(imgURL)
                .override(90,90)
                .apply(new RequestOptions().transform(new CenterCrop(),
                        new RoundedCorners(20)))
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return pList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView company;
        TextView name;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            company = (TextView)itemView.findViewById(R.id.ProductCompanyTv);
            name = (TextView)itemView.findViewById(R.id.ProductNameTv);
            image = (ImageView)itemView.findViewById(R.id.ProductImageView);
        }
    }

}
