package com.example.finalprojectvegan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterStep3Activity extends AppCompatActivity {

    Button Btn_RegisterThirdToFourth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step3);

        Btn_RegisterThirdToFourth = findViewById(R.id.Btn_RegisterThirdToFourth);
        Btn_RegisterThirdToFourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterStep3Activity.this, RegisterStep4Activity.class);
                startActivity(intent);
            }
        });
    }
}