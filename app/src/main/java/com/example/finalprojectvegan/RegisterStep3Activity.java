package com.example.finalprojectvegan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterStep3Activity extends AppCompatActivity {

    Button Btn_RegisterThirdToFourth;
    RadioGroup radioGroup_veganType;
    RadioButton radio_Vegan, radio_Lacto, radio_Ovo, radio_LactoOvo, radio_Pesco, radio_Pollo, radio_etc;
    TextView textView_select_VeganType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step3);

        textView_select_VeganType = findViewById(R.id.textView_select_VeganType);

        radio_Vegan = findViewById(R.id.radio_Vegan);
        radio_Lacto = findViewById(R.id.radio_Lacto);
        radio_Ovo = findViewById(R.id.radio_Ovo);
        radio_LactoOvo = findViewById(R.id.radio_LactoOvo);
        radio_Pesco = findViewById(R.id.radio_Pesco);
        radio_Pollo = findViewById(R.id.radio_Pollo);
        radio_etc = findViewById(R.id.radio_etc);

        radioGroup_veganType = findViewById(R.id.radioGroup_veganType);

        radioGroup_veganType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radio_Vegan:
                        textView_select_VeganType.setText("비건");
                        break;
                    case R.id.radio_Lacto:
                        textView_select_VeganType.setText("락토");
                        break;
                    case R.id.radio_Ovo:
                        textView_select_VeganType.setText("오보");
                        break;
                    case R.id.radio_LactoOvo:
                        textView_select_VeganType.setText("락토오보");
                        break;
                    case R.id.radio_Pesco:
                        textView_select_VeganType.setText("페스코");
                        break;
                    case R.id.radio_Pollo:
                        textView_select_VeganType.setText("폴로");
                        break;
                    case R.id.radio_etc:
                        textView_select_VeganType.setText("기타");
                        break;
                }
            }
        });


        Btn_RegisterThirdToFourth = findViewById(R.id.Btn_RegisterThirdToFourth);
        Btn_RegisterThirdToFourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileUpload();
            }
        });
    }

    private void profileUpload() {
        String veganType = ((TextView) findViewById(R.id.textView_select_VeganType)).getText().toString();

        if (veganType.length() > 0) {
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            UserVeganTypeInfo userVeganTypeInfo = new UserVeganTypeInfo(veganType);
            if (firebaseUser != null) {
                db.collection("users").document(firebaseUser.getUid()).set(userVeganTypeInfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(), "회원정보 등록 성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterStep3Activity.this, RegisterStep4Activity.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "회원정보 등록 실패", Toast.LENGTH_SHORT).show();
                                Log.d("tag", "Error writing document", e);
                            }
                        });
            }
        }
    }

}