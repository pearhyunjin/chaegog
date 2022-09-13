package com.example.finalprojectvegan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterStep4Activity extends AppCompatActivity {

    Button Btn_RegisterFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step4);

        Btn_RegisterFinish = findViewById(R.id.Btn_RegisterFinish);
        Btn_RegisterFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterStep4Activity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}