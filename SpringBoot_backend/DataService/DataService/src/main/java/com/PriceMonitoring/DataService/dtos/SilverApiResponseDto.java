package com.PriceMonitoring.DataService.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SilverApiResponseDto {
    @JsonProperty("status")
    private boolean status;

    @JsonProperty("price")
    private int price;

    @JsonProperty("city")
    private String city;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("unit_gram")
    private int unit_gram;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
//{
//        "status": true,
//        "price": 265,
//        "city": "Mumbai",
//        "currency": "INR",
//        "unit_gram": 1
//        }