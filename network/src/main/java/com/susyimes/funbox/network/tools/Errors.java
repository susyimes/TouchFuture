

package com.susyimes.funbox.network.tools;


import com.google.gson.Gson;

import retrofit2.HttpException;
import com.susyimes.funbox.network.Error;

/**
 * @author drakeet
 */
public class Errors {

    public static Error errorResponse(HttpException throwable) {
        return new Gson().fromJson(throwable.response().errorBody().charStream(), Error.class);
    }


    public static String errorMessage(Throwable throwable) {
        try {
            if (throwable instanceof HttpException) {
                return errorResponse((HttpException) throwable).error;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return throwable.getMessage();
    }
}
