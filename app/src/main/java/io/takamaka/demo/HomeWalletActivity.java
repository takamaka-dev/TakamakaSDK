package io.takamaka.demo;

import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;

import io.takamaka.demo.utils.SWTracker;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashAlgorithmNotFoundException;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashEncodeException;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashProviderNotFoundException;
import io.takamaka.sdk.exceptions.wallet.WalletException;
import io.takamaka.sdk.utils.IdentiColorHelper;
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
    TextView labelCurrentAddress;
    TextView editTextRefreshIndex;
    ImageView imageViewIdenticon;
    Button refreshIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_wallet);
        setCurrentActivity(this);
        initMenu();
        CallAPI callApi = new CallAPI();
        try {
            callApi.execute(SWTracker.i().getBalanceEndpoint().toString(), SWTracker.i().getIwk().getPublicKeyAtIndexURL64(SWTracker.getCurrIndex()));
        } catch (WalletException e) {
            e.printStackTrace();
        }
        imageViewIdenticon = findViewById(R.id.imageViewIdenticon);
        try {
            imageViewIdenticon.setImageDrawable(new BitmapDrawable(getResources(), IdentiColorHelper.identiconMatrixGenerator(SWTracker.i().getIwk().getPublicKeyAtIndexURL64(SWTracker.getCurrIndex()))));
        } catch (HashEncodeException | HashAlgorithmNotFoundException | HashProviderNotFoundException | WalletException e) {
            e.printStackTrace();
        }
        labelCurrentAddress = findViewById(R.id.label_current_address);
        try {
            labelCurrentAddress.setText(SWTracker.i().getIwk().getPublicKeyAtIndexURL64(SWTracker.getCurrIndex()));
        } catch (WalletException e) {
            e.printStackTrace();
        }
        refreshIndex = findViewById(R.id.button_refresh_index);
        editTextRefreshIndex = findViewById(R.id.edit_text_refresh_index);

        refreshIndex.setOnClickListener(
                view -> {
                    SWTracker.setCurrIndex(Integer.parseInt(editTextRefreshIndex.getText().toString()));
                    try {
                        labelCurrentAddress.setText(SWTracker.i().getIwk().getPublicKeyAtIndexURL64(SWTracker.getCurrIndex()));
                        try {
                            imageViewIdenticon.setImageDrawable(new BitmapDrawable(getResources(), IdentiColorHelper.identiconMatrixGenerator(SWTracker.i().getIwk().getPublicKeyAtIndexURL64(SWTracker.getCurrIndex()))));
                        } catch (HashEncodeException | HashAlgorithmNotFoundException | HashProviderNotFoundException | WalletException e) {
                            e.printStackTrace();
                        }
                    } catch (WalletException e) {
                        e.printStackTrace();
                    }
                });
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

            tkgValue.setText(String.valueOf(Double.parseDouble(SWTracker.getBb().getGreenBalance()) / Math.pow(10, 9)));
            tkrValue.setText(String.valueOf(Double.parseDouble(SWTracker.getBb().getRedBalance()) / Math.pow(10, 9)));
            ftkgValue.setText(String.valueOf(Double.parseDouble(SWTracker.getBb().getGreenPenalty()) / Math.pow(10, 9)));
            ftkrValue.setText(String.valueOf(Double.parseDouble(SWTracker.getBb().getRedPenalty()) / Math.pow(10, 9)));
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

