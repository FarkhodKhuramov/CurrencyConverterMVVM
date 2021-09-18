package com.example.currencyconverter.repository;

import retrofit2.Call;
import retrofit2.http.GET;
import com.example.currencyconverter.data.CurrencyWrapper;

public interface Api {
    @GET("daily_json.js")
    Call<CurrencyWrapper> getCurrency();
}
