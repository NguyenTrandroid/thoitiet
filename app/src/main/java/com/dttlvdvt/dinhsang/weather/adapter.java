package com.dttlvdvt.dinhsang.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter extends BaseAdapter {
    Context context;
    ArrayList<weather> arrayList;

    public adapter(Context context, ArrayList<weather> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.dong_weather,null);
        weather weather = arrayList.get(i);
        TextView txtngay = view.findViewById(R.id.textngay);
        TextView txtstatus = view.findViewById(R.id.texttrangthai);
        TextView txtmax = view.findViewById(R.id.nhietdomax);
        TextView txtmin = view.findViewById(R.id.nhietdomin);
        ImageView imageView = view.findViewById(R.id.imgnho);

        txtngay.setText(weather.ngay);
        txtstatus.setText(weather.status);
        txtmax.setText(weather.max+"'C");
        txtmin.setText(weather.min+"'C");
        Picasso.with(context).load("https://www.weatherbit.io/static/img/icons/"+weather.img+".png").into(imageView);

        return view;
    }
}
