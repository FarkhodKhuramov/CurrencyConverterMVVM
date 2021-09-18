package com.example.currencyconverter.data;

import com.google.gson.annotations.SerializedName;

public class Valute {
    @SerializedName("ID")
    String id;
    @SerializedName("NumCode")
    String numCode;
    @SerializedName("CharCode")
    String charCode;
    @SerializedName("Nominal")
    Integer nominal;
    @SerializedName("Name")
    String name;
    @SerializedName("Value")
    Float value;
    @SerializedName("Previous")
    Float previous;

    public String getId() {
        return id;
    }

    public String getNumCode() {
        return numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public Integer getNominal() {
        return nominal;
    }

    public String getName() {
        return name;
    }

    public Float getValue() {
        return value;
    }

    public Float getPrevious() {
        return previous;
    }
}
