package com.example.currencyconverter.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.currencyconverter.R;
import com.example.currencyconverter.adapter.RateAdapter;
import com.example.currencyconverter.data.CurrencyWrapper;
import com.example.currencyconverter.viewmodel.MainActivityViewModel;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText sumToConvert = findViewById(R.id.et_sum_to_convert);
        TextView sumResult = findViewById(R.id.tv_result);
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

        btnRefresh.setOnClickListener((View v) -> {
            viewModel.requestCurrency();
        });

        btnConvert.setOnClickListener((View v) -> {
            Float sum = Float.valueOf(sumToConvert.getText().toString());
            Float result = viewModel.convert(sum, "USD");
            sumResult.setText(String.format(Locale.getDefault(), "%.4f", result));
        });

    }

}