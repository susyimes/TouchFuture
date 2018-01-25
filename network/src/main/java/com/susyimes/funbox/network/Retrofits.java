package com.susyimes.funbox.network;

import android.content.Context;

/**
 * Created by Susyimes on 2018/1/23.
 */

public class Retrofits {
    private volatile static HuobiAPI SINGLETON;

    public static HuobiAPI getService(Context context) {
        if (SINGLETON == null) {
            synchronized (HuobiAPI.class) {
                if (SINGLETON == null) {
                    SINGLETON = new CleanRetrofit(context.getApplicationContext()).getService();
                }
            }
        }
        return SINGLETON;
    }

}
