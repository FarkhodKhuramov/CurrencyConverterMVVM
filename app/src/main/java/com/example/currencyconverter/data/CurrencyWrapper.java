package com.example.currencyconverter.data;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Map;

public class CurrencyWrapper {
    @SerializedName("Date")
    Date date;
    @SerializedName("PreviousDate")
    Date previousDate;
    @SerializedName("PreviousURL")
    String previousURL;
    @SerializedName("Timestamp")
    Date timestamp;
    @SerializedName("Valute")
    Map<String, Valute> valute;

    public Date getDate() {
        return date;
    }

    public Date getPreviousDate() {
        return previousDate;
    }

    public String getPreviousURL() {
        return previousURL;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Map<String, Valute> getValute() {
        return valute;
    }
}
