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
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, "confirm=yes&email=isacco.borsani.old@gmail.com&password=admin7256");
            Request request = new Request.Builder()
                    .url("https://testsite.takamaka.org/oauth/authorize?response_type=code&client_id=dev&redirect_uri=https%3A%2F%2Ftestsite.takamaka.org%3A20443%2Foauth%2Fauthorized&scope=email+address")
                    .method("POST", body)
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Origin", "https://testsite.takamaka.org")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Cache-Control", "max-age=0")
                    .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.72 Safari/537.36")
                    .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                    .addHeader("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"90\", \"Google Chrome\";v=\"90\"")
                    .addHeader("sec-ch-ua-mobile", "?0")
                    .addHeader("Upgrade-Insecure-Requests", "1")


                    .addHeader("Sec-Fetch-Site", "same-origin")
                    .addHeader("Sec-Fetch-Mode", "navigate")
                    .addHeader("Sec-Fetch-User", "?1")
                    .addHeader("Sec-Fetch-Dest", "document")
                    .addHeader("Referer", "https://testsite.takamaka.org/oauth/authorize?response_type=code&client_id=dev&redirect_uri=https%3A%2F%2Ftestsite.takamaka.org%3A20443%2Foauth%2Fauthorized&scope=email+address")
                    .addHeader("Accept-Language", "it-IT,it;q=0.9,en-US;q=0.8,en;q=0.7")
                    .addHeader("Cookie", "session=eyJkZXZfb2F1dGhyZWRpciI6Imh0dHBzOi8vdGVzdHNpdGUudGFrYW1ha2Eub3JnOjIwNDQzL29hdXRoL2F1dGhvcml6ZWQifQ.YI_WUg.m4D93mtf86l462-1eSOMONGaOHU; session=eyJkZXZfdG9rZW4iOnsiIHQiOlsiMmxWVDdHenc1blFONW9uTWNrWkxOMDc1VEFUMHBCIiwiIl19fQ.YI_tUA.rzr4raz5r6_Pe0lZAUhBMv2HaHE")
                    .addHeader("dnt", "1")
                    .addHeader("sec-gpc", "1")
                    .build();
            try {

                Response response = client.newCall(request).execute();
                System.out.println(response.body().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}