package com.susyimes.funbox.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Susyimes on 2018/1/29.
 */

public class CoinData {
//    	"id": 1517155200,
//                "open": 14.380000000000000000,
//                "close": 13.770000000000000000,
//                "low": 13.510000000000000000,
//                "high": 14.750000000000000000,
//                "amount": 1261283.182994073236622508,
//                "vol": 17934169.434645226154632314650000000000000000,
//                "count": 27254
    @SerializedName("low")
    public float low;
    @SerializedName("high")
    public float high;
    @SerializedName("open")
    public float open;
    @SerializedName("close")
    public float close;
}
