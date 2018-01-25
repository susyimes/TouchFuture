package com.susyimes.funbox.network;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Susyimes on 2018/1/23.
 */

public interface HuobiAPI {
    @GET("/market/history/kline")
    Flowable<String> base(@Query("symbol") String symbol,@Query("period") String period);
}
