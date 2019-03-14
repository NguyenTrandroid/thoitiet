package com.dttlvdvt.dinhsang.weather;

import android.app.Dialog;
import android.app.WallpaperInfo;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {
    EditText edtseach;
    ImageButton btnseach;
    Button  btnnextday;
    TextView txttenthanhpho, txttenquocgia, txtnhietdo, txtdoam, txtgio,
            txttrangthai,txtdatetime, txtmay, tenapp, d1, d2, d3, d4, d5, d6, d7;
    ImageView imgicon, iconchitiet;
    String name,kinhdo,vido,muigio,apsuat, binhminh, hoanghon, luongmua,city;
    Dialog thongtinchitiet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        overridePendingTransition(R.anim.right,R.anim.outleft);
        anhxa();
        getdata("ip=auto");
        setfonts();
        click();
    }
    private void click() {
        btnseach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = edtseach.getText().toString().trim();
                if(city.length()!=0){
                    Toast.makeText(Main2Activity.this,"cú pháp: tên thành phố,viết tắt quốc gia",Toast.LENGTH_LONG).show();
                    getdata("city="+city);
                }
                else{
                    Toast.makeText(Main2Activity.this,"vui lòng nhập thành phố",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnnextday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this,Main3Activity.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
        iconchitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ttchitiet();
            }
        });
    }

    private void getdata(final String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(Main2Activity.this);
        String url = "https://api.weatherbit.io/v2.0/current?"+data+"&key=a25d3235c3164d22bec247117ec1ee63";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArraydata = jsonObject.getJSONArray("data");
                            JSONObject jsonObjectweather = jsonArraydata.getJSONObject(0);
                            String tentp = jsonObjectweather.getString("city_name");
                            name=tentp;
                            txttenthanhpho.setText("Thành phố: "+tentp);
                            String tenqg = jsonObjectweather.getString("country_code");
                            txttenquocgia.setText("Quốc gia: "+tenqg);
                            String da = jsonObjectweather.getString("rh");
                            txtdoam.setText(da+"%");
                            String tdg = jsonObjectweather.getString("wind_spd");
                            txtgio.setText(tdg+"m/s");
                            String lm = jsonObjectweather.getString("clouds");
                            txtmay.setText(lm+"%");
                            JSONObject jsonObjecticon = jsonObjectweather.getJSONObject("weather");
                            String icon = jsonObjecticon.getString("icon");
                            Picasso.with(Main2Activity.this).load("https://www.weatherbit.io/static/img/icons/"+icon+".png").into(imgicon);
                            String nd =jsonObjectweather.getString("temp");
                            txtnhietdo.setText(nd+"'C");
                            String tt = jsonObjecticon.getString("description");
                            txttrangthai.setText(tt);
                            long l = jsonObjectweather.getLong("ts");
                            Date date = new Date(l*1000);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE dd-MM-yyyy");
                            String ngaythang = simpleDateFormat.format(date);
                            txtdatetime.setText(ngaythang);
                            kinhdo =jsonObjectweather.getString("lon");
                            vido= jsonObjectweather.getString("lat");
                            muigio = jsonObjectweather.getString("timezone");
                            apsuat = jsonObjectweather.getString("pres");
                            binhminh =jsonObjectweather.getString("wind_cdir");
                            hoanghon =jsonObjectweather.getString("uv");
                            luongmua =jsonObjectweather.getString("precip");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Main2Activity.this,"kiểm tra kết nối mạng của ban không tìm thấy dữ liệu hoặc bạn nhập sai cú pháp" +
                        " (chỉ tìm kiếm thành phố) bạn nên kiểm tra kết nối mạng",Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void setfonts() {
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/VNMUSTI.TTF");
        tenapp.setTypeface(typeface);
    }
    private void anhxa() {
        tenapp = findViewById(R.id.tenapp);
        btnseach = findViewById(R.id.timkiem);
        edtseach = findViewById(R.id.nhapten);
        btnnextday = findViewById(R.id.nextday);
        txttenthanhpho = findViewById(R.id.tenthanhpho);
        txttenquocgia = findViewById(R.id.tenquocgia);
        txtdoam = findViewById(R.id.doam);
        txtgio = findViewById(R.id.gio);
        txtmay = findViewById(R.id.may);
        txtnhietdo = findViewById(R.id.nhietdo);
        txttrangthai = findViewById(R.id.trangthai);
        txtdatetime = findViewById(R.id.day);
        imgicon = findViewById(R.id.imgto);
        iconchitiet = findViewById(R.id.morong);
    }
    private void  ttchitiet(){
        thongtinchitiet = new Dialog(this);
        thongtinchitiet.requestWindowFeature(Window.FEATURE_NO_TITLE);
        thongtinchitiet.setContentView(R.layout.ttct);
        thongtinchitiet.show();

        d1 = thongtinchitiet.findViewById(R.id.d1);
        d2 = thongtinchitiet.findViewById(R.id.d2);
        d3 = thongtinchitiet.findViewById(R.id.d3);
        d4 = thongtinchitiet.findViewById(R.id.d4);
        d5 = thongtinchitiet.findViewById(R.id.d5);
        d6 = thongtinchitiet.findViewById(R.id.d6);
        d7 = thongtinchitiet.findViewById(R.id.d7);

        d1.setText("Kinh độ: "+kinhdo+"'");
        d2.setText("Vĩ độ: "+vido+"'");
        d3.setText("Múi giờ: "+muigio);
        d4.setText("Áp suât: "+apsuat+" mb");
        d5.setText("Hướng gió: "+binhminh);
        d6.setText("Lượng mưa tích tụ: "+luongmua+" mm");
        d7.setText("Chỉ số UV: "+hoanghon+" (0-11+)");
    }

    public void RateButton(View view) {
        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }
    }
}
