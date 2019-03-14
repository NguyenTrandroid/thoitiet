package com.dttlvdvt.dinhsang.weather;

import android.widget.ImageView;

public class weather {
    public String ngay;
    public String status;
    public String img;
    public String max;
    public String min;

    public weather(String ngay, String status, String img, String max, String min) {
        this.ngay = ngay;
        this.status = status;
        this.img = img;
        this.max = max;
        this.min = min;
    }
}
