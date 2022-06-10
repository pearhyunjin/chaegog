package com.example.finalprojectvegan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterStep2Activity extends AppCompatActivity {

    Button Btn_RegisterSecondToThird;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step2);

        Btn_RegisterSecondToThird = findViewById(R.id.Btn_RegisterSecondToThird);
        Btn_RegisterSecondToThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterStep2Activity.this, RegisterStep3Activity.class);
                startActivity(intent);
            }
        });
    }
}