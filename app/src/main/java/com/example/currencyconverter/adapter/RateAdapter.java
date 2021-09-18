package com.example.currencyconverter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.currencyconverter.R;
import com.example.currencyconverter.data.Valute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RateAdapter extends RecyclerView.Adapter<RateAdapter.ViewHolder> {
    Map<String, Valute> data;
    List<Valute> dataList;

    public RateAdapter() {
    }

    public RateAdapter(Map<String, Valute> data) {
        setData(data);
    }

    public RateAdapter(List<Valute> data) {
        setData(data);
    }

    public void setData(Map<String, Valute> data){
        dataList = new ArrayList<>();
        for(Map.Entry<String, Valute> entry : data.entrySet()){
            dataList.add(entry.getValue());
        }
        notifyDataSetChanged();
    }

    public void setData(List<Valute> data){
        dataList = new ArrayList<>();
        this.dataList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rate, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Valute valute = dataList.get(position);
        String text = valute.getNominal().toString() + " " +
                valute.getCharCode() + " (" +
                valute.getName() + ") " +
                valute.getValue() + " RUB";

        holder.mTextView.setText(text);
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_cur_info);
        }
    }
}
