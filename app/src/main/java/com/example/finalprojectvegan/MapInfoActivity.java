package com.example.finalprojectvegan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

public class MapInfoActivity extends AppCompatActivity {
    TextView getMapInfoDetailName, getMapInfoDetailAddr, getMapInfoDetailTime,
    getMapInfoDetailCategory, getGetMapInfoDetailMenu;
    String name, addr, time, category,menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_info);

        getMapInfoDetailName = findViewById(R.id.map_info_detail_name);
        getMapInfoDetailAddr = findViewById(R.id.map_info_detail_addr);
        getMapInfoDetailTime = findViewById(R.id.map_info_detail_time);
        getMapInfoDetailCategory = findViewById(R.id.map_info_detail_category);
        getGetMapInfoDetailMenu = findViewById(R.id.map_info_detail_menu);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        addr = intent.getStringExtra("addr");
        time = intent.getStringExtra("time");
        category = intent.getStringExtra("category");
        menu = intent.getStringExtra("menu");

        getMapInfoDetailName.setText(name);
        getMapInfoDetailAddr.setText(addr);
        getMapInfoDetailTime.setText(time);
        getMapInfoDetailCategory.setText(category);
        getGetMapInfoDetailMenu.setText(menu);
    }
}