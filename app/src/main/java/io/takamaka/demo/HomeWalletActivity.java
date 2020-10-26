package io.takamaka.demo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;

import io.takamaka.demo.utils.SWTracker;
import io.takamaka.sdk.exceptions.wallet.WalletException;
import io.takamaka.sdk.wallet.beans.BalanceBean;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeWalletActivity extends MainController {

    TextView tkgValue;
    TextView tkrValue;
    TextView ftkgValue;
    TextView ftkrValue;
    TextView labelValWalletName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_wallet);
        initMenu();
        CallAPI callApi = new CallAPI();
        System.out.println("IWK: " + SWTracker.i().getIwk());
        try {
            callApi.execute(SWTracker.i().getBalanceEndpoint().toString(), SWTracker.i().getIwk().getPublicKeyAtIndexURL64(0));
        } catch (WalletException e) {
            e.printStackTrace();
        }
    }

    private class CallAPI extends AsyncTask<String, String, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            tkgValue = findViewById(R.id.label_val_tkg);
            tkrValue = findViewById(R.id.label_val_tkr);
            ftkgValue = findViewById(R.id.label_val_ftkg);
            ftkrValue = findViewById(R.id.label_val_ftkr);
            labelValWalletName = findViewById(R.id.label_val_wallet_name);

            System.out.println("PIPPONEEEEEEEE: " + tkgValue);


            tkgValue.setText((int) SWTracker.i().getBb().getGreenBalance());
            tkrValue.setText((int) SWTracker.i().getBb().getRedBalance());
            ftkgValue.setText((int) SWTracker.i().getBb().getGreenPenalty());
            ftkrValue.setText((int) SWTracker.i().getBb().getRedPenalty());
            labelValWalletName.setText(SWTracker.i().getNewWalletBean().getName());
        }

        @Override
        protected Void doInBackground(String... params) {
            String urlString = params[0]; // URL to call
            String data = params[1]; //data to post
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, "address=" + data);
            Request request = new Request.Builder()
                    .url(urlString)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Gson g = new Gson();
                BalanceBean bb = g.fromJson(response.body().string(), BalanceBean.class);
                System.out.println(bb.getRedBalance());
                SWTracker.setBb(bb);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}

