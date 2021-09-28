package com.example.currencyconverter.viewmodel;

import android.app.Application;
import android.util.Log;

import com.example.currencyconverter.data.CurrencyWrapper;
import com.example.currencyconverter.data.Valute;
import com.example.currencyconverter.repository.CurrencyRepository;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainActivityViewModel extends AndroidViewModel {
    private static final String TAG = "MainActivityViewModel";
    LiveData<CurrencyWrapper> mCurrencyWrapperLiveData;
    CurrencyRepository currencyRepository;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        currencyRepository = new CurrencyRepository();
        mCurrencyWrapperLiveData = currencyRepository.getCurrencyWrapperMutableLiveData();
    }

    public LiveData<CurrencyWrapper> getCurrencyWrapperLiveData() {
        return mCurrencyWrapperLiveData;
    }

    public void requestCurrency(){
        currencyRepository.getCurrency();
    }

    public Float convert(@NotNull Float sumRub, @NotNull String ccy){
        Log.d(TAG, "convert() called with: sumRub = [" + sumRub + "], ccy = [" + ccy + "]");
        Map<String, Valute> currMap = mCurrencyWrapperLiveData.getValue().getValute();
        if (!currMap.containsKey(ccy) || sumRub == null)
            return null;
        Valute valute = currMap.get(ccy);
        return sumRub / valute.getValue() * valute.getNominal();

    }

    public List<String> getCurrencyList(){
        if (mCurrencyWrapperLiveData.getValue() == null)
            return null;
        List<String> resultList = new ArrayList<>();
        for (Map.Entry<String, Valute> entry : mCurrencyWrapperLiveData.getValue().getValute().entrySet()){
            resultList.add(entry.getKey() + " - " + entry.getValue().getName());
        }
        return resultList;
    }
}
