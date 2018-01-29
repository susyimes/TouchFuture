package com.susyimes.funbox.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Susyimes on 2018/1/23.
 */

public class HuobiBaseBean {
    @SerializedName("status")
    public String status;
    @SerializedName("ch")
    public String ch;
    @SerializedName("ts")
    public long ts;
    @SerializedName("data")
    public List<CoinData> data;

}
