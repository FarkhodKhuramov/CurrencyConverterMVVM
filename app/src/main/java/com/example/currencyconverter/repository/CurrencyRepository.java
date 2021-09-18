package com.example.currencyconverter.repository;

import android.util.Log;

import com.example.currencyconverter.data.CurrencyWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrencyRepository {
    private static final String TAG = "CurrencyRepository";
    private final Api requestHandler;
    final String baseUrl = "https://www.cbr-xml-daily.ru/";
    MutableLiveData<CurrencyWrapper> mCurrencyWrapperMutableLiveData;

    public CurrencyRepository() {
        mCurrencyWrapperMutableLiveData = new MutableLiveData<>();

        Gson gsonBuilder =new GsonBuilder().setDateFormat("dd.MM.yyyy'T'HH:mm:ss+'Z'").create();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
                .build();

        requestHandler = retrofit.create(Api.class);
        Log.d(TAG, "CurrencyRepository() called");
    }

    public void getCurrency(){
        Call<CurrencyWrapper> call = requestHandler.getCurrency();
        call.enqueue(new Callback<CurrencyWrapper>() {
            @Override
            public void onResponse(@NotNull Call<CurrencyWrapper> call, @NotNull Response<CurrencyWrapper> response) {
                if (response.body() != null)
                    mCurrencyWrapperMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<CurrencyWrapper> call, @NotNull Throwable t) {
                mCurrencyWrapperMutableLiveData.postValue(null);
            }
        });
    }

    public LiveData<CurrencyWrapper> getCurrencyWrapperMutableLiveData() {
        return mCurrencyWrapperMutableLiveData;
    }
}
