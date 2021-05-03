package io.takamaka.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import io.takamaka.demo.utils.SWTracker;
import io.takamaka.sdk.utils.TokenBean;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OauthLoginActivity extends MainController {

    protected boolean errorLogin = false;

    protected ProgressBar pgsBar;

    protected Context context;

    private TokenBean tb;

    Button oauthLoginButton;
    TextView userName, userPassword, labelError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauth_login);
        initMenu();
        setCurrentActivity(this);
        initFormLoginOauth();
    }

    private void initFormLoginOauth() {
        pgsBar = findViewById(R.id.pBar);
        LinearLayout loginOauthForm = findViewById(R.id.useroauth_login_form);
        labelError = findViewById(R.id.label_error);
        labelError.setText("");

        oauthLoginButton = findViewById(R.id.button_login_oauth);

        oauthLoginButton.setOnClickListener(
                v -> {
                    List<View> wrongFields = checkFieldsForm(loginOauthForm);
                    if (!wrongFields.isEmpty()) {
                        System.out.println(wrongFields);
                        highlightWrongForm(wrongFields);
                    } else {
                        userName = findViewById(R.id.value_user_email);
                        userPassword = findViewById(R.id.value_user_password);
                        initOauthLogin(userName.getText().toString(), userPassword.getText().toString());
                    }
                });
    }

    private void initOauthLogin(String email, String password) {
        CallAPI ca = new CallAPI();
        ca.execute(email, password);
    }

    @SuppressLint("StaticFieldLeak")
    private class CallAPI extends AsyncTask<String, String, Void> {

        protected void onPreExecute() {
            pgsBar.setVisibility(View.VISIBLE);
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Void result) {
            pgsBar.setVisibility(View.INVISIBLE);
            if (errorLogin) {
                labelError.setText("Wrong credentials!");
            }
            SWTracker.setAccessToken(tb.getAccess_token());
            Intent activitySettings = new Intent(getApplicationContext(), HomeWalletActivity.class);
            startActivity(activitySettings);
            //finish();
        }

        @Override
        protected Void doInBackground(String... params) {
            System.out.println("EMAIL: " + params[0]);
            System.out.println("PASSWORD: " + params[1]);
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            System.out.println("confirm=yes&email="+params[0]+"&password="+params[1]);
            RequestBody body = RequestBody.create(mediaType, "confirm=yes&email="+params[0]+"&password="+params[1]);
            //RequestBody body = RequestBody.create(mediaType, "confirm=yes&email=eltonTest@gmail.com&password=11111111");
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
                assert response.body() != null;
                String result = response.body().string();
                System.out.println(result);
                Gson g = new Gson();
                tb = g.fromJson(result, TokenBean.class);
                System.out.println(tb.getAccess_token());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}