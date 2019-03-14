package com.dttlvdvt.dinhsang.weather;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main3Activity extends AppCompatActivity {
    TextView namecity;
    ListView listView;
    adapter adapter;
    ArrayList<weather> mangthoitier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        overridePendingTransition(R.anim.right,R.anim.outleft);
        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
        get7day(city);
        anhxa();
        namecity.setText("Thành phố: "+city);
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.left,R.anim.outright);
    }
    private void anhxa() {
        namecity = findViewById(R.id.tentp);
        listView = findViewById(R.id.listview);
        mangthoitier = new ArrayList<weather>();
        adapter = new adapter(Main3Activity.this,mangthoitier);
        listView.setAdapter(adapter);
    }

    private void get7day(String data) {
        String url = "https://api.weatherbit.io/v2.0/forecast/daily?city="+data+"&key=a25d3235c3164d22bec247117ec1ee63";
        RequestQueue requestQueue = Volley.newRequestQueue(Main3Activity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArraylist = jsonObject.getJSONArray("data");
                            for (int i=0; i<jsonArraylist.length(); i++){
                                JSONObject jsonObjectday = jsonArraylist.getJSONObject(i);
                                long l = jsonObjectday.getLong("moonrise_ts");
                                Date date = new Date(l*1000);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE dd-MM-yyyy");
                                String ngaythang = simpleDateFormat.format(date);
                                String max =jsonObjectday.getString("max_temp");
                                String min =jsonObjectday.getString("min_temp");
                                JSONObject jsonObjectweather = jsonObjectday.getJSONObject("weather");
                                String trangthai = jsonObjectweather.getString("description");
                                String hinhanh = jsonObjectweather.getString("icon");
                                mangthoitier.add(new weather(ngaythang, trangthai, hinhanh, max, min));

                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
}
