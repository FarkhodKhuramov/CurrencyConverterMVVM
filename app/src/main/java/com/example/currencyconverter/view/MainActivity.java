package com.example.currencyconverter.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.currencyconverter.R;
import com.example.currencyconverter.adapter.CurrencyAdapter;
import com.example.currencyconverter.adapter.RateAdapter;
import com.example.currencyconverter.data.CurrencyWrapper;
import com.example.currencyconverter.viewmodel.MainActivityViewModel;

import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText sumToConvert = findViewById(R.id.et_sum_to_convert);
        TextView sumResult = findViewById(R.id.tv_result);
        TextView selectCcy = findViewById(R.id.tv_select_ccy);
        Button btnRefresh = findViewById(R.id.btn_refresh);
        Button btnConvert = findViewById(R.id.btn_convert);

        RecyclerView rvRates = findViewById(R.id.rv_rates);
        RateAdapter rateAdapter = new RateAdapter();

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.init();
        viewModel.getCurrencyWrapperLiveData().observe(this, new Observer<CurrencyWrapper>() {
            @Override
            public void onChanged(CurrencyWrapper currencyWrapper) {
                if (currencyWrapper != null){
                    rateAdapter.setData(currencyWrapper.getValute());
                    Toast.makeText(MainActivity.this, "Data loaded", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Data could not be loaded", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rvRates.setHasFixedSize(true);
        rvRates.setLayoutManager(new LinearLayoutManager(this));
        rvRates.setAdapter(rateAdapter);

        viewModel.requestCurrency();

        // Button Refresh
        btnRefresh.setOnClickListener((View v) -> {
            viewModel.requestCurrency();
        });

        // Button Convert
        btnConvert.setOnClickListener((View v) -> {

            float sum = 0f;
            Float result = 0f;
            if (!sumToConvert.getText().toString().isEmpty()){
                sum = Float.parseFloat(sumToConvert.getText().toString());
                // Отрезаем первые 3 символа, чтобы получить код валюты (RUB, USD,...)
                String ccy = selectCcy.getText().toString().substring(0,3);
                result = viewModel.convert(sum, ccy);
            }
            sumResult.setText(String.format(Locale.getDefault(), "%.4f", result));
        });

        // Currency Select to convert
        selectCcy.setOnClickListener((View v)->{
            List<String> recyclerData = viewModel.getCurrencyList();
            CurrencyAdapter.CurrencyAdapterListener listener = new CurrencyAdapter.CurrencyAdapterListener() {
                @Override
                public void onItemClick(int position) {
                    Log.d(TAG, "onItemClick: position = " + position);
                    if (recyclerData != null)
                        selectCcy.setText(recyclerData.get(position));
//                    currencySelectBottomSheet.dismiss();
                }
            };

            if (recyclerData != null){
                CurrencyAdapter currencyAdapter = new CurrencyAdapter(recyclerData, listener);
                CurrencySelectBottomSheet currencySelectBottomSheet = CurrencySelectBottomSheet.newInstance(currencyAdapter);
                currencySelectBottomSheet.show(getSupportFragmentManager(), "currencySelectBottomSheet");
            }

        });

    }

}