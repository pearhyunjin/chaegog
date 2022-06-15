package com.example.finalprojectvegan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterStep1Activity extends AppCompatActivity {

    private EditText edit_register_name, edit_register_id, edit_register_password, edit_register_email, edit_register_phonenum;
    private Button Btn_RegisterFirstToSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step1);

        edit_register_name = findViewById(R.id.edit_register_name);
        edit_register_id = findViewById(R.id.edit_register_id);
        edit_register_password = findViewById(R.id.edit_register_password);
        edit_register_email = findViewById(R.id.edit_register_email);
        edit_register_phonenum = findViewById(R.id.edit_register_phonenum);

        Btn_RegisterFirstToSecond = findViewById(R.id.Btn_RegisterFirstToSecond);
        Btn_RegisterFirstToSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어 있는 값을 get(가져오기)해준다.
                String userName = edit_register_name.getText().toString();
                String userID = edit_register_id.getText().toString();
                String userPassword = edit_register_password.getText().toString();
                String userEmail = edit_register_email.getText().toString();
                int userPhonenum = Integer.parseInt(edit_register_phonenum.getText().toString());

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

                RegisterStep1Request registerStep1Request = new RegisterStep1Request(userName, userID, userPassword, userEmail, userPhonenum, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterStep1Activity.this);
                queue.add(registerStep1Request);

            }
        });
    }
}