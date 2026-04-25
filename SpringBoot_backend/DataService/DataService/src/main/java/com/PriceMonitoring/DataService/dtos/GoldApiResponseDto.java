package com.PriceMonitoring.DataService.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GoldApiResponseDto {
    private boolean status;

    @JsonProperty("22k")
    private int price22k;

    @JsonProperty("24k")
    private int price24k;

    private String city;

    @JsonProperty("unit_gram")
    private int unitGram;

    public int getPrice24k() {
        return price24k;
    }

    public void setPrice24k(int price24k) {
        this.price24k = price24k;
    }

    public int getPrice22k() {
        return price22k;
    }

    public void setPrice22k(int price22k) {
        this.price22k = price22k;
    }
}
//{
//        "status": true,
//        "22k": 14200,
//        "24k": 14910,
//        "city": "Lucknow",
//        "unit_gram": 1
//        }