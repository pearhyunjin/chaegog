package com.example.finalprojectvegan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BookmarkActivity extends AppCompatActivity {
    RecyclerView bmRecyclerView;
    BookmarkAdapter bmAdapter;
    ArrayList<RestaurantArrayList> items = new ArrayList<RestaurantArrayList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        bmRecyclerView = findViewById(R.id.BookmarkRecyclerView);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        bmRecyclerView.setLayoutManager(layoutManager);

        bmAdapter = new BookmarkAdapter();
        bmRecyclerView.setAdapter(bmAdapter);

        ThreadProc();
    }

    private void ThreadProc() {
        new Thread() {
            @Override
            public void run() {
                String str, receiveMsg = "";
                String urlStr = "http://mygomhosting.dothome.co.kr/restaurant.php";
                try {
                    URL url = new URL(urlStr);
                    // HttpURLConnection 객체 만듦
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

                    if (conn.getResponseCode() == conn.HTTP_OK) {
                        InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                        BufferedReader reader = new BufferedReader(tmp);
                        StringBuffer buffer = new StringBuffer();
                        while ((str = reader.readLine()) != null) {
                            buffer.append(str);
                        }
                        receiveMsg = buffer.toString();
                        reader.close();

                        Bundle bun = new Bundle();
                        bun.putString("jsonGap", receiveMsg);
                        Message msg = handler.obtainMessage();
                        msg.setData(bun);
                        handler.sendMessage(msg);
                    } else {
                        Log.i("b1a2", "통신 결과 : " + conn.getResponseCode() + "에러");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void jsonParsing(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray restArr = jsonObject.getJSONArray("restaurant");

            for(int i = 0; i < restArr.length(); i++) {
                JSONObject restObj = restArr.getJSONObject(i);

                RestaurantArrayList rest = new RestaurantArrayList();
                // 여기서 name에 들어가는 String은  php 파일과 동일해야함.
                rest.setName(restObj.getString("name"));
                rest.setAddress(restObj.getString("address"));
                rest.setImage(restObj.getString("image"));


                bmAdapter.addItem(rest);
            }
            bmAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSON Parsing Error",e.getMessage());
        }
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Bundle bun = msg.getData();
            String str = bun.getString("jsonGap");

            jsonParsing(str);
            return true;
        }
    });
}