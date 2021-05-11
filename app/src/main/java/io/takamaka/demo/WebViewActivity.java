package io.takamaka.demo;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.gson.Gson;

import java.io.IOException;

import io.takamaka.demo.utils.SWTracker;
import io.takamaka.sdk.utils.threadSafeUtils.TkmTextUtils;
import io.takamaka.sdk.wallet.beans.BalanceBean;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WebViewActivity extends MainController {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        setCurrentActivity(this);
        try {
            initWebView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initWebView() throws IOException {
        CallAPI callApi = new CallAPI();
        callApi.execute("ciao");
    }

    private class CallAPI extends AsyncTask<String, String, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }

        @Override
        protected Void doInBackground(String... params) {


            return null;
        }
    }

}