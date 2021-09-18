package com.example.currencyconverter.viewmodel;

import android.app.Application;

import com.example.currencyconverter.data.CurrencyWrapper;
import com.example.currencyconverter.data.Valute;
import com.example.currencyconverter.repository.CurrencyRepository;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainActivityViewModel extends AndroidViewModel {
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
        Map<String, Valute> currMap = mCurrencyWrapperLiveData.getValue().getValute();
        if (!currMap.containsKey(ccy) || sumRub == null)
            return null;
        Valute valute = currMap.get(ccy);
        return sumRub / valute.getValue() * valute.getNominal();

    }
}
