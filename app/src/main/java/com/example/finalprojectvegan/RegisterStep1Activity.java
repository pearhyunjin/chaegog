package com.example.finalprojectvegan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterStep1Activity extends AppCompatActivity {

    Button Btn_RegisterFirstToSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step1);

        Btn_RegisterFirstToSecond = findViewById(R.id.Btn_RegisterFirstToSecond);
        Btn_RegisterFirstToSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterStep1Activity.this, RegisterStep2Activity.class);
                startActivity(intent);
            }
        });
    }
}