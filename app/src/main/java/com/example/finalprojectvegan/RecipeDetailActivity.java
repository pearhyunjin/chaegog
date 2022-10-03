package com.example.finalprojectvegan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class RecipeDetailActivity extends AppCompatActivity {
    private Intent intent;
    TextView getRecipeName, getRecipeType, getRecipeWay, getRecipeIngre,
            getRecipeManual01, getRecipeManual02, getRecipeManual03, getRecipeManual04,
            getRecipeManual05, getRecipeManual06;
    ImageView getRecipeImage, getRecipeManualImg01, getRecipeManualImg02, getRecipeManualImg03,
            getRecipeManualImg04, getRecipeManualImg05, getRecipeManualImg06;
    String name, image, type, way, ingre, manual01, manual_image01, manual02, manual_image02,
            manual03, manual_image03, manual04, manual_image04, manual05, manual_image05, manual06, manual_image06;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        getRecipeName = findViewById(R.id.recipe_detail_name);
        getRecipeImage = findViewById(R.id.recipe_detail_image);
        getRecipeType = findViewById(R.id.recipe_detail_type);
        getRecipeWay = findViewById(R.id.recipe_detail_way);
        getRecipeIngre = findViewById(R.id.recipe_ingredient);
        getRecipeManualImg01 = findViewById(R.id.recipe_manual_image01);
        getRecipeManual01 = findViewById(R.id.recipe_manual01);
        getRecipeManualImg02 = findViewById(R.id.recipe_manual_image02);
        getRecipeManual02 = findViewById(R.id.recipe_manual02);
        getRecipeManualImg03 = findViewById(R.id.recipe_manual_image03);
        getRecipeManual03 = findViewById(R.id.recipe_manual03);
//        getRecipeManualImg04 = findViewById(R.id.recipe_manual_image04);
//        getRecipeManual04 = findViewById(R.id.recipe_manual04);
//        getRecipeManualImg05 = findViewById(R.id.recipe_manual_image05);
//        getRecipeManual05 = findViewById(R.id.recipe_manual05);
//        getRecipeManualImg06 = findViewById(R.id.recipe_manual_image06);
//        getRecipeManual06 = findViewById(R.id.recipe_manual06);

    }

    @Override
    protected void onResume() {
        super.onResume();

        intent = getIntent();
        name = intent.getStringExtra("name");
        image = intent.getStringExtra("image");
        type = intent.getStringExtra("type");
        way = intent.getStringExtra("way");
        ingre = intent.getStringExtra("ingre");

        manual_image01 = intent.getStringExtra("m_Image01");
        manual01 = intent.getStringExtra("manual01");
        manual_image02 = intent.getStringExtra("m_Image02");
        manual02 = intent.getStringExtra("manual02");
        manual_image01 = intent.getStringExtra("m_Image03");
        manual01 = intent.getStringExtra("manual03");

        getRecipeName.setText(name);
        Glide.with(this)
                .load(image)
                .apply(new RequestOptions().transform(new CenterCrop(),
                        new RoundedCorners(10)))
                .into(getRecipeImage);
        Glide.with(this)
                .load(manual_image01)
                .apply(new RequestOptions().transform(new CenterCrop(),
                        new RoundedCorners(10)))
                .into(getRecipeManualImg01);
        getRecipeType.setText(type);
        getRecipeWay.setText(way);
        getRecipeIngre.setText(ingre);
        getRecipeManual01.setText(manual01);
        getRecipeManual02.setText(manual02);
        getRecipeManual03.setText(manual03);
    }
}