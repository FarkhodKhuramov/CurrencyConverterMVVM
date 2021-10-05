package com.example.currencyconverter.adapter;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.currencyconverter.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> implements Parcelable {
    List<String> dataList = new ArrayList<>();
    CurrencyAdapterListener mCurrencyAdapterListener;

    public CurrencyAdapter(List<String> dataList, CurrencyAdapterListener listener) {
        setData(dataList);
        setListener(listener);
    }

    public void setData(List<String> dataList){
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public void setListener(CurrencyAdapterListener listener){
        this.mCurrencyAdapterListener = listener;
    }

    protected CurrencyAdapter(Parcel in) {
        dataList = in.createStringArrayList();
    }

    public static final Creator<CurrencyAdapter> CREATOR = new Creator<CurrencyAdapter>() {
        @Override
        public CurrencyAdapter createFromParcel(Parcel in) {
            return new CurrencyAdapter(in);
        }

        @Override
        public CurrencyAdapter[] newArray(int size) {
            return new CurrencyAdapter[size];
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_currency,null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTextView.setText(dataList.get(position));
        holder.mTextView.setOnClickListener((View v)->{
            mCurrencyAdapterListener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(dataList);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_item_currency);
        }
    }

    public interface CurrencyAdapterListener{
        void onItemClick(int position);
    }
}
