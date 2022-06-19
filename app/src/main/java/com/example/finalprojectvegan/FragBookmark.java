package com.example.finalprojectvegan;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class FragBookmark extends Fragment {

//    private RecyclerView bmRecyclerView;
//    BookmarkAdapter bmAdapter;
//    private RecyclerView.LayoutManager bmLayoutManager;
//    ArrayList<RestaurantArrayList> items = new ArrayList<RestaurantArrayList>();


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FragBookmark() {
        // Required empty public constructor
    }

    public static FragBookmark newInstance(String param1, String param2) {
        FragBookmark fragment = new FragBookmark();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        bmRecyclerView = view.findViewById(R.id.BookmarkRecyclerView);
//        bmRecyclerView.setHasFixedSize(true);
//        bmAdapter = new BookmarkAdapter();
//
//        bmLayoutManager = new LinearLayoutManager(getActivity());
//        bmRecyclerView.setLayoutManager(bmLayoutManager);
//        bmRecyclerView.setAdapter(bmAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_bookmark, container, false);

//        bmRecyclerView = view.findViewById(R.id.BookmarkRecyclerView);
//        bmRecyclerView.setHasFixedSize(true);
//        bmRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        bmAdapter = new BookmarkAdapter();
//        bmRecyclerView.setAdapter(bmAdapter);
//
//        ((MainActivity)getActivity()).ThreadProc();
//        Context context = view.getContext();


        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ThreadProc();
    }

//    private void prepareData(){
//        items.add(new RestaurantArrayList("바이두부", "서울특별시 용산구 소월로20길 10", "https://mblogthumb-phinf.pstatic.net/MjAyMTA5MDVfMTUx/MDAxNjMwODQ4Mjk4NzA2.duIbb2-RcKGy69d_1ln3FL1rbZQ2c99H5A3MHBhiXCsg.vtmtw_u8oOJ5WiNVjtQ6LxAdRDQ0cAG_YNnPD4SqNxsg.JPEG.bbosil/IMG_6562.jpg?type=w800"));
//        items.add(new RestaurantArrayList("스타일비건", "서울특별시 강남구 선릉로135길 6", "https://mblogthumb-phinf.pstatic.net/MjAyMDA5MDFfMTA2/MDAxNTk4OTUxMjI4NjA0.bCkFreL1VTWJHkJXG7y23GnerUMH0zJ5YumqZPYXvoIg.OeK3tEN2Ka72RZ3_FG_y2hNf706P6qPyaAYCaxI8qHkg.JPEG.mjpiglet/SE-12a70519-b05b-4a6e-9fa5-92e2e096976e.jpg?type=w800"));
//    }
//
//    private void ThreadProc() {
//        new Thread() {
//            @Override
//            public void run() {
//                String str, receiveMsg = "";
//                String urlStr = "http://mygomhosting.dothome.co.kr/restaurant.php";
//                try {
//                    URL url = new URL(urlStr);
//                    // HttpURLConnection 객체 만듦
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//
//                    if (conn.getResponseCode() == conn.HTTP_OK) {
//                        InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
//                        BufferedReader reader = new BufferedReader(tmp);
//                        StringBuffer buffer = new StringBuffer();
//                        while ((str = reader.readLine()) != null) {
//                            buffer.append(str);
//                        }
//                        receiveMsg = buffer.toString();
//                        reader.close();
//
//                        Bundle bun = new Bundle();
//                        bun.putString("jsonGap", receiveMsg);
//                        Message msg = handler.obtainMessage();
//                        msg.setData(bun);
//                        handler.sendMessage(msg);
//                    } else {
//                        Log.i("b1a2", "통신 결과 : " + conn.getResponseCode() + "에러");
//                    }
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
//
//    private void jsonParsing(String json) {
//        try {
//            JSONObject jsonObject = new JSONObject(json);
//            JSONArray restArr = jsonObject.getJSONArray("restaurant");
//
//            for(int i = 0; i < restArr.length(); i++) {
//                JSONObject restObj = restArr.getJSONObject(i);
//
//                RestaurantArrayList rest = new RestaurantArrayList();
//                // 여기서 name에 들어가는 String은  php 파일과 동일해야함.
//                rest.setName(restObj.getString("name"));
//                rest.setAddress(restObj.getString("address"));
//                rest.setImage(restObj.getString("image"));
//
//
//                bmAdapter.addItem(rest);
//            }
//            bmAdapter.notifyDataSetChanged();
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Log.e("JSON Parsing Error",e.getMessage());
//        }
//    }
//
//    Handler handler = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(@NonNull Message msg) {
//            Bundle bun = msg.getData();
//            String str = bun.getString("jsonGap");
//
//            jsonParsing(str);
//            return true;
//        }
//    });

}