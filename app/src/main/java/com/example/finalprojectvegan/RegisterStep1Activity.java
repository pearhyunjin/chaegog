package com.example.finalprojectvegan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegisterStep1Activity extends AppCompatActivity {

    // 변수 선언
    private EditText edit_register_name, edit_register_id, edit_register_password, edit_register_email, edit_register_phonenum, edit_password_check;
    private Button Btn_RegisterFirstToSecond;

    // Firebase 인증, Database 접근, 처리중 화면
    private FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step1);

        edit_register_name = findViewById(R.id.edit_register_name);
//        edit_register_id = findViewById(R.id.edit_register_id);
        edit_register_password = findViewById(R.id.edit_register_password);
        edit_register_email = findViewById(R.id.edit_register_email);
//        edit_register_phonenum = findViewById(R.id.edit_register_phonenum);
        edit_password_check = findViewById(R.id.edit_password_check);
        Btn_RegisterFirstToSecond = findViewById(R.id.Btn_RegisterFirstToSecond);


        firebaseAuth = FirebaseAuth.getInstance(); // 초기화

        Btn_RegisterFirstToSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 처리중 화면 띄우기
                pd = new ProgressDialog(RegisterStep1Activity.this);
                pd.setMessage("Please wait..");
                pd.show();

                // EditText에 현재 입력되어 있는 값을 get(가져오기)해준다.
                String userName = edit_register_name.getText().toString();
//                String userID = edit_register_id.getText().toString();
                String userPassword = edit_register_password.getText().toString();
                String passwordCheck = edit_password_check.getText().toString();
                String userEmail = edit_register_email.getText().toString();
//                int userPhonenum = Integer.parseInt(edit_register_phonenum.getText().toString());


                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword) || TextUtils.isEmpty(passwordCheck)){
                    Toast.makeText(RegisterStep1Activity.this, "모든 양식을 채워주세요!", Toast.LENGTH_SHORT).show();
                } else {
                    if (userPassword.length() < 6){
                        Toast.makeText(RegisterStep1Activity.this, "비밀번호는 최소 6자리 이상으로 해주세요!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (userPassword.equals(passwordCheck)) {
                            register(userName, userEmail, userPassword);
                        } else {
                            Toast.makeText(RegisterStep1Activity.this, "비밀번호를 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                        }
                    }
                }


                // 닷홈 서버와 php 통신하기
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(), "회원 정보 1단계 등록 완료", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterStep1Activity.this, RegisterStep2Activity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "회원 정보 1단계 등록 실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                RegisterStep1Request registerStep1Request = new RegisterStep1Request(userName, userEmail, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterStep1Activity.this);
                queue.add(registerStep1Request);

            }
        });

    }

    private void register (String userName, String userEmail, String userPassword) {
        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(RegisterStep1Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Firebase 아이디 정보 변수에 담기.
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            String id = firebaseUser.getUid();

                            // reference 초기화. child로 "Users"만들고, 그 안에 또 "userid"만든다..?
                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(id);

                            // HashMap 에 유저정보 담는다.
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id", id);
                            hashMap.put("userName", userName.toLowerCase());
                            hashMap.put("userEmail", userEmail);
                            hashMap.put("bio", "");
                            hashMap.put("imageurl", "https://w7.pngwing.com/pngs/858/581/png-transparent-profile-icon-user-computer-icons-system-chinese-wind-title-column-miscellaneous-service-logo.png");

                            // DB에 자료 담기
                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        // 처리중 화면 종료
                                        pd.dismiss();
                                        Intent intent = new Intent(RegisterStep1Activity.this, LoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });

                        } else {

                            // 유저입력 정보가 유효하지 않을경우
                            pd.dismiss();
                            Toast.makeText(RegisterStep1Activity.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}