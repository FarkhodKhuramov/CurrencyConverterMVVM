package com.example.currencyconverter.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.currencyconverter.R;
import com.example.currencyconverter.adapter.RateAdapter;
import com.example.currencyconverter.data.CurrencyWrapper;
import com.example.currencyconverter.viewmodel.MainActivityViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView result = findViewById(R.id.tv_result);
        Button refresh = findViewById(R.id.btn_refresh);

        RecyclerView rvRates = findViewById(R.id.rv_rates);
        RateAdapter rateAdapter = new RateAdapter();

        MainActivityViewModel viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
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

        refresh.setOnClickListener((View v) -> {
            viewModel.requestCurrency();
        });
    }

}