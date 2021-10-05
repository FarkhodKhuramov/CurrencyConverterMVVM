package com.example.currencyconverter.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.currencyconverter.R;
import com.example.currencyconverter.adapter.CurrencyAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CurrencySelectBottomSheet extends BottomSheetDialogFragment {

    public static final String ARG_PARAM_1 = "arg1";
    private static final String TAG = "CurrencySelectBottomShe";

    public static CurrencySelectBottomSheet newInstance(CurrencyAdapter currencyAdapter){
        CurrencySelectBottomSheet currencySelectBottomSheet = new CurrencySelectBottomSheet();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM_1, currencyAdapter);
        currencySelectBottomSheet.setArguments(args);
        return currencySelectBottomSheet;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_currency, container);
        RecyclerView recyclerView = view.findViewById(R.id.rv_select_ccy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (getArguments()!=null){
            CurrencyAdapter currencyAdapter = getArguments().getParcelable(ARG_PARAM_1);
            recyclerView.setAdapter(currencyAdapter);

        }
        view.setOnClickListener((View v)->{
            dismiss();
        });
        return view;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
