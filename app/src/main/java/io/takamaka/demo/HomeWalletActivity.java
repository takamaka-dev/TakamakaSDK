package io.takamaka.demo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Date;

import io.takamaka.demo.utils.SWTracker;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashAlgorithmNotFoundException;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashEncodeException;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashProviderNotFoundException;
import io.takamaka.sdk.exceptions.wallet.TransactionCanNotBeCreatedException;
import io.takamaka.sdk.exceptions.wallet.WalletException;
import io.takamaka.sdk.transactions.BuilderITB;
import io.takamaka.sdk.transactions.InternalTransactionBean;
import io.takamaka.sdk.transactions.TransactionBean;
import io.takamaka.sdk.utils.IdentiColorHelper;
import io.takamaka.sdk.utils.OauthResponseBean;
import io.takamaka.sdk.utils.TkmSignUtils;
import io.takamaka.sdk.utils.threadSafeUtils.TkmTextUtils;
import io.takamaka.sdk.wallet.TkmWallet;
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
    TextView wellcomeOauth;
    ImageView imageViewIdenticon;
    Button refreshIndex;
    FloatingActionButton oauthLoginButton, oauthSyncAddressButton;
    private String currentUserOauth, resultMesssage;
    private OauthResponseBean orb;
    private Context ctx;
    private String baseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ctx = this;
        wellcomeOauth = findViewById(R.id.wellcome_oauth);

        baseUrl = "https://www.takamaka.io";
        if (SWTracker.getCurrentSetting().equals("test")) {
            baseUrl = "https://testsite.takamaka.org";
        }

        if (SWTracker.i().getIwk() == null) {
            Intent activitySettings = new Intent(getApplicationContext(), MainController.class);
            startActivity(activitySettings);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_wallet);
        setCurrentActivity(this);
        initMenu();
        CallAPI callApi = new CallAPI();
        try {
            System.out.println("IWK instance: " + SWTracker.i().getIwk());
            if (SWTracker.i().getIwk() != null) {
                callApi.execute(SWTracker.i().getBalanceEndpoint().toString(), SWTracker.i().getIwk().getPublicKeyAtIndexURL64(SWTracker.getCurrIndex()));
            }
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

        oauthLoginButton = findViewById(R.id.oauth_login_button);


        oauthSyncAddressButton = findViewById(R.id.oauth_sync_address_button);
        System.out.println(oauthSyncAddressButton);
        if (SWTracker.getAccessToken() == null) {
            oauthSyncAddressButton.hide();
        } else {
            oauthLoginButton.hide();
            OauthAPI oauth = new OauthAPI();
            oauth.execute();
        }

        oauthSyncAddressButton.setOnClickListener(
                view -> {
                    AlertDialog alertDialog = new AlertDialog.Builder(ctx)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Are you sure to sync this address in Takamaka?")
                            .setMessage("")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    OauthAPISyncAddress syncAction = new OauthAPISyncAddress();
                                    syncAction.execute();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();

                }
        );

        refreshIndex.setOnClickListener(
                view -> {
                    SWTracker.setCurrIndex(Integer.parseInt(editTextRefreshIndex.getText().toString().isEmpty() ? "0" : editTextRefreshIndex.getText().toString()));
                    try {
                        labelCurrentAddress.setText(SWTracker.i().getIwk().getPublicKeyAtIndexURL64(SWTracker.getCurrIndex()));
                        try {
                            imageViewIdenticon.setImageDrawable(new BitmapDrawable(getResources(), IdentiColorHelper.identiconMatrixGenerator(SWTracker.i().getIwk().getPublicKeyAtIndexURL64(SWTracker.getCurrIndex()))));
                        } catch (HashEncodeException | HashAlgorithmNotFoundException | HashProviderNotFoundException | WalletException e) {
                            e.printStackTrace();
                        }
                    } catch (WalletException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        labelCurrentAddress.setText("");
                    }
                });

        oauthLoginButton.setOnClickListener(
                view -> {
                    Intent activityTokens = new Intent(getApplicationContext(), OauthLoginActivity.class);
                    startActivityForResult(activityTokens, 0);
                });
    }

    //label_current_address
    @SuppressLint("StaticFieldLeak")
    private class OauthAPISyncAddress extends AsyncTask<String, String, Void> {

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Void aVoid) {
            AlertDialog alertDialog = new AlertDialog.Builder(ctx)
                    .setIcon(android.R.drawable.alert_dark_frame)
                    .setTitle("Alert")
                    .setMessage(resultMesssage)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).show();
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected Void doInBackground(String... strings) {

            OauthResponseBean orb = new OauthResponseBean();
            orb.setAction("new_address");
            orb.setDate(new Date().getTime());
            orb.setEmail(currentUserOauth);

            InternalTransactionBean itb = null;
            System.out.println("Indice corrente: " + SWTracker.getCurrIndex());
            try {
                System.out.println("INDIRIZZO CORRENTE: " + SWTracker.i().getIwk().getPublicKeyAtIndexURL64(SWTracker.getCurrIndex()));
            } catch (WalletException e) {
                e.printStackTrace();
            }
            try {
                itb = BuilderITB.blob(SWTracker.i().getIwk().getPublicKeyAtIndexURL64(SWTracker.getCurrIndex()), null, new Gson().toJson(orb), new Date(0L));
            } catch (WalletException e) {
                e.printStackTrace();
            }
            TransactionBean genericTRA = null;

            try {
                assert itb != null;
                genericTRA = TkmWallet.createGenericTransaction(
                        itb,
                        SWTracker.i().getIwk(),
                        SWTracker.getCurrIndex());
            } catch (TransactionCanNotBeCreatedException e) {
                e.printStackTrace();
            }

            String txJson = TkmTextUtils.toJson(genericTRA);

            System.out.println(txJson);

            String hexJson = TkmSignUtils.fromStringToHexString(txJson);

            System.out.println(hexJson);

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(baseUrl + "/api/oauth/syncaddress/" + hexJson)
                    .method("GET", null)
                    .addHeader("Authorization", "Bearer " + SWTracker.getAccessToken())
                    .build();
            try {
                Response response = client.newCall(request).execute();
                assert response.body() != null;
                String result = response.body().string();
                System.out.println(result);
                Gson g = new Gson();
                orb = g.fromJson(result, OauthResponseBean.class);
                resultMesssage = orb.getMsg();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class OauthAPI extends AsyncTask<String, String, Void> {

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Void aVoid) {
            wellcomeOauth = findViewById(R.id.wellcome_oauth);
            wellcomeOauth.setText("Hi there, " + orb.getUsername());
            currentUserOauth = orb.getUsername();
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected Void doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(baseUrl + "/api/oauth/email")
                    .method("GET", null)
                    .addHeader("Authorization", "Bearer " + SWTracker.getAccessToken())
                    .build();
            try {
                Response response = client.newCall(request).execute();
                assert response.body() != null;
                String result = response.body().string();
                System.out.println(result);
                Gson g = new Gson();
                orb = g.fromJson(result, OauthResponseBean.class);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @SuppressLint("StaticFieldLeak")
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

            tkgValue.setText(TkmTextUtils.formatNumberWithShift(SWTracker.getBb().getGreenBalance(), 2));
            tkrValue.setText(TkmTextUtils.formatNumberWithShift(SWTracker.getBb().getRedBalance(), 2));
            ftkgValue.setText(TkmTextUtils.formatNumberWithShift(SWTracker.getBb().getGreenPenalty(), 2));
            ftkrValue.setText(TkmTextUtils.formatNumberWithShift(SWTracker.getBb().getRedPenalty(), 2));
//            tkgValue.setText(String.valueOf(Double.parseDouble(SWTracker.i().getBb().getGreenBalance()) / Math.pow(10, 9)));
//            tkrValue.setText(String.valueOf(Double.parseDouble(SWTracker.i().getBb().getRedBalance()) / Math.pow(10, 9)));
//            ftkgValue.setText(String.valueOf(Double.parseDouble(SWTracker.i().getBb().getGreenPenalty()) / Math.pow(10, 9)));
//            ftkrValue.setText(String.valueOf(Double.parseDouble(SWTracker.i().getBb().getRedPenalty()) / Math.pow(10, 9)));
            labelValWalletName.setText(SWTracker.i().getNewWalletBean().getName());

        }

        @Override
        protected Void doInBackground(String... params) {
            String urlString = params[0]; // URL to call
            System.out.println("urlString: " + urlString);
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
                System.out.println("vediamo dove fa print");
                Response response = client.newCall(request).execute();
                Gson g = new Gson();
                System.out.println("response qui di seguito");
                assert response.body() != null;
                System.out.println(response.body().toString());
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

