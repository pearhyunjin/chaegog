package com.example.finalprojectvegan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterStep2Activity extends AppCompatActivity {

    Button Btn_RegisterSecondToThird;
    TextView textView_select_type;
    CheckBox checkbox_environment, checkbox_animal, checkbox_religion, checkbox_health, checkbox_etc;

    // 뒤로가기 버튼 클릭시 앱 종료
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step2);

        textView_select_type = findViewById(R.id.textView_select_type);

        checkbox_environment = findViewById(R.id.checkbox_environment);
        checkbox_animal =findViewById(R.id.checkbox_animal);
        checkbox_health = findViewById(R.id.checkbox_health);
        checkbox_religion = findViewById(R.id.checkbox_religion);
        checkbox_etc = findViewById(R.id.checkbox_etc);

        checkbox_environment.setOnClickListener(checkboxClickListener);
        checkbox_animal.setOnClickListener(checkboxClickListener);
        checkbox_health.setOnClickListener(checkboxClickListener);
        checkbox_religion.setOnClickListener(checkboxClickListener);
        checkbox_etc.setOnClickListener(checkboxClickListener);

        Btn_RegisterSecondToThird = findViewById(R.id.Btn_RegisterSecondToThird);
        Btn_RegisterSecondToThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userVeganCategory = textView_select_type.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(), "회원 정보 2단계 등록 완료", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterStep2Activity.this, RegisterStep3Activity.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(getApplicationContext(), "회원 정보 2단계 등록 실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                if (textView_select_type == null) {
                    Toast.makeText(getApplicationContext(), "선택해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    profileUpload();
                }

//                profileUpload();
                RegisterStep2Request registerStep2Request = new RegisterStep2Request(userVeganCategory, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterStep2Activity.this);
                queue.add(registerStep2Request);
            }
        });
    }

//    public void onCheckboxClicked(View view) {
//        boolean checked = ((CheckBox) view).isChecked();
//
//        if (checked) {
//            textView_select_type.append(((CheckBox)view).getText());
//        }
//    }


    View.OnClickListener checkboxClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean checked = ((CheckBox) view).isChecked();

            switch (view.getId()) {
                case R.id.checkbox_environment:
                    if (checked) {
                        Toast.makeText(getApplicationContext(), "환경", Toast.LENGTH_SHORT).show();
                        textView_select_type.append(" 환경");
                    } else {

                    }
                    break;
                case R.id.checkbox_animal:
                    if (checked) {
                        Toast.makeText(getApplicationContext(), "동물권", Toast.LENGTH_SHORT).show();
                        textView_select_type.append(" 동물권");
                    } else {

                    }
                    break;
                case R.id.checkbox_health:
                    if (checked) {
                        Toast.makeText(getApplicationContext(), "건강", Toast.LENGTH_SHORT).show();
                        textView_select_type.append(" 건강");
                    } else {

                    }
                    break;
                case R.id.checkbox_religion:
                    if (checked) {
                        Toast.makeText(getApplicationContext(), "종교", Toast.LENGTH_SHORT).show();
                        textView_select_type.append(" 종교");
                    } else {

                    }
                    break;
                case R.id.checkbox_etc:
                    if (checked) {
                        Toast.makeText(getApplicationContext(), "기타", Toast.LENGTH_SHORT).show();
                        textView_select_type.append(" 기타");
                    } else {

                    }
                    break;
            }
        }
    };

    private void profileUpload() {
        String category = ((TextView) findViewById(R.id.textView_select_type)).getText().toString();

        if (category.length() > 0) {
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            UserVeganCategoryInfo userVeganCategoryInfo = new UserVeganCategoryInfo(category);
            if (firebaseUser != null) {
                db.collection("userVeganCategory").document(firebaseUser.getUid()).set(userVeganCategoryInfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(), "회원정보 등록 성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterStep2Activity.this, RegisterStep3Activity.class);
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