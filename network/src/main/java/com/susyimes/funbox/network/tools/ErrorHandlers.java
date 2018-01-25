

package com.susyimes.funbox.network.tools;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;


import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

import java.net.UnknownHostException;



public class ErrorHandlers {

    private static final String TAG = ErrorHandlers.class.getSimpleName();


    public static void displayError(Context context, String message) {
        if (context == null) {
            return;
        }
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


    public static void displayError(Context context, Throwable throwable) {
        if (context == null) {
            Log.e(TAG, "[300] " + "Context is null");
            return;
        }

        String errorMessage = null;
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            errorMessage = Errors.errorResponse(httpException).error;
        } else if (throwable instanceof UnknownHostException) {
            errorMessage = "no network";
        }
        if (errorMessage != null) {
            displayError(context, errorMessage);
        } else {
            Log.e(TAG, "[301]", throwable);
            displayError(context, Errors.errorMessage(throwable));
        }
    }


    public static Consumer<Throwable> displayErrorConsumer(final Context context) {
        return new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) {
                displayError(context, throwable);
            }
        };
    }
}
