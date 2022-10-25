package com.example.finalprojectvegan.Model;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.finalprojectvegan.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapTabInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapTabInfo extends Fragment {
    TextView getMapInfoDetailName, getMapInfoDetailAddr, getMapInfoDetailTime,
            getMapInfoDetailCategory, getMapInfoDetailMenu, getMapInfoDetailDayOff;
    ImageView getMapInfoDetailImage;
    CheckBox getMapInfoBookmark;
    String name, addr, time, dayoff, category, menu, pk, id, image;
    String menuFi;

    String arr[];
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MapTabInfo() {
        // Required empty public constructor
    }

    public static MapTabInfo newInstance(String param1, String param2) {
        MapTabInfo fragment = new MapTabInfo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map_tab_info, container, false);

        getMapInfoDetailName = view.findViewById(R.id.map_info_detail_name);
        getMapInfoDetailAddr = view.findViewById(R.id.map_info_detail_addr);
        getMapInfoDetailTime = view.findViewById(R.id.map_info_detail_time);
        getMapInfoDetailCategory = view.findViewById(R.id.map_info_detail_category);
        getMapInfoDetailMenu = view.findViewById(R.id.map_info_detail_menu);
        getMapInfoBookmark = view.findViewById(R.id.favorite_checkbox);
        getMapInfoDetailImage = view.findViewById(R.id.map_info_detail_image);
        getMapInfoDetailDayOff = view.findViewById(R.id.map_info_detail_dayoff);

        Bundle bundle = getArguments();
        name = bundle.getString("name");
        addr = bundle.getString("addr");
        time = bundle.getString("time");
        dayoff = bundle.getString("dayOff");
        category = bundle.getString("category");
        menu = bundle.getString("menuFi");
        id = bundle.getString("userID");
        pk = bundle.getString("userPk");
        image = bundle.getString("image");

        getMapInfoDetailName.setText(name);
        getMapInfoDetailAddr.setText(addr);
        getMapInfoDetailTime.setText(time);
        getMapInfoDetailDayOff.setText(dayoff);
        getMapInfoDetailCategory.setText(category);
        getMapInfoDetailMenu.setText(menu);
        Glide.with(this)
                .load(image)
                .apply(new RequestOptions().transform(new CenterCrop(),
                        new RoundedCorners(10)))
                .into(getMapInfoDetailImage);

        /*
        getMapInfoBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String storeName = name;
                String storeAddr = addr;
                String storeImage =  image;
                String storeTime = time;
                String storeDayOff = dayoff;

                if (getMapInfoBookmark.isChecked()) {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) {
                                    Toast.makeText(getContext(), "북마크 추가", Toast.LENGTH_SHORT).show();
                                    Log.d("bookmark", storeName);
                                    Log.d("bookmark", storeAddr);
                                    Log.d("bookmark", storeImage);
                                    Log.d("bookmark", storeTime);
                                    Log.d("bookmark", storeDayOff);
                                } else {
                                    Toast.makeText(getContext(), "북마크 실패", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    BookmarkRequest bookmarkRequest = new BookmarkRequest(userPK, userID, storeName, storeAddr, storeImage, storeTime, storeDayOff, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    queue.add(bookmarkRequest);
                } else {
                    Toast.makeText(getContext(), "북마크 삭제", Toast.LENGTH_SHORT).show();
                }
            }

        });
        */

        return view;
    }
}