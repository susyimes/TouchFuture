package com.susyimes.funbox.touchfuture;

import android.app.Application;

import io.reactivex.plugins.RxJavaPlugins;

import static com.susyimes.funbox.network.tools.ErrorHandlers.displayErrorConsumer;

/**
 * Created by Susyimes on 2018/1/25.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RxJavaPlugins.setErrorHandler(displayErrorConsumer(this));
    }
}
