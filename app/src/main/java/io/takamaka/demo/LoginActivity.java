package io.takamaka.demo;

import android.annotation.SuppressLint;
import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import io.takamaka.demo.utils.SWTracker;
import io.takamaka.demo.utils.UserWalletBean;
import io.takamaka.demo.utils.WalletFXHelper;
import io.takamaka.demo.utils.WalletFXHelperErrorBean;
import io.takamaka.sdk.utils.FileHelper;
import io.takamaka.sdk.wallet.NewWalletBean;


public class LoginActivity extends MainController {
    protected boolean errorLogin = false;

    protected ProgressBar pgsBar;

    protected Context context;

    Button walletLoginSubmit;
    TextView walletName, walletPassword, labelError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initMenu();
        setCurrentActivity(this);
        initFormLoginWallet();
    }

    private void initFormLoginWallet() {
        //String[] arr = {"lonely","episode","found","oval","holiday","bargain","gown","expose","indicate","lumber","vast","legal","lamp","narrow","club","west","dose","jazz","crush","mystery","helmet","deliver","banner","tray","guilt"};
        String[] arr = {};
        ScrollView loginWalletForm = findViewById(R.id.wallets_scroll_container);

        LinearLayout internalContainerWallets = findViewById(R.id.linear_layout_wallets_container);

        for(String s: arr) {
            LinearLayout l = new LinearLayout(this);
            l.setOrientation(LinearLayout.HORIZONTAL);
            l.setBackgroundColor(Color.parseColor("#5c82a1"));
            ImageView iv = new ImageView(this);
            iv.setImageResource(R.drawable.wallet_50px_white);
            TextView walletName = new TextView(this);
            walletName.setWidth(260);
            walletName.setTextColor(Color.WHITE);
            walletName.setPadding(20, 0, 0, 0);
            walletName.setTextSize(15);
            walletName.setText(s);

            l.addView(iv);
            l.addView(walletName);
            l.setTextAlignment(LinearLayout.TEXT_ALIGNMENT_CENTER);
            l.setGravity(Gravity.CENTER_VERTICAL);
            l.setGravity(Gravity.CENTER);

            View v = new View(this);
            v.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    5
            ));
            v.setBackgroundColor(Color.parseColor("#B3B3B3"));

            internalContainerWallets.addView(l);
            internalContainerWallets.addView(v);
        }
        if (arr.length == 0) {
            LinearLayout l = new LinearLayout(this);
            l.setOrientation(LinearLayout.HORIZONTAL);
            l.setTextAlignment(LinearLayout.TEXT_ALIGNMENT_CENTER);
            l.setGravity(Gravity.CENTER_VERTICAL);
            l.setGravity(Gravity.CENTER);
            TextView alert = new TextView(this);
            alert.setText("No wallets created yet :(");
            alert.setTextColor(Color.parseColor("#cccccc"));
            alert.setTextSize(15);
            alert.setTypeface(null, Typeface.BOLD);
            l.addView(alert);
            internalContainerWallets.addView(l);
        }


//        pgsBar = (ProgressBar) findViewById(R.id.pBar);
//        walletLoginSubmit = findViewById(R.id.button_login);
//        LinearLayout loginWalletForm = findViewById(R.id.wallet_login_form);
//        labelError = findViewById(R.id.label_error);
//        labelError.setText("");
//        walletLoginSubmit.setOnClickListener(v -> {
//            List<View> wrongFields = checkFieldsForm(loginWalletForm);
//            if (!wrongFields.isEmpty()) {
//                highlightWrongForm(wrongFields);
//            } else {
//                walletName = findViewById(R.id.wallet_name);
//                walletPassword = findViewById(R.id.wallet_password);
//                initWalletLogin();
//            }
//        });
    }

    private void initWalletLogin() {
        errorLogin = false;
        LoginWalletTask cwt = new LoginWalletTask();
        cwt.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class LoginWalletTask extends AsyncTask<Void, Void, Void> {
        protected void onPreExecute() {
            pgsBar.setVisibility(View.VISIBLE);
        }

        protected void onProgressUpdate(Void... progress) {
            pgsBar.setVisibility(View.VISIBLE);
        }

        protected void onPostExecute(Void result) {
            pgsBar.setVisibility(View.INVISIBLE);
            if (errorLogin) {
                labelError.setText("Wrong credentials!");
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String readPassword = walletPassword.getText().toString();
            UserWalletBean uwb = new UserWalletBean();
            uwb.setInternalName(walletName.getText().toString());
            uwb.setWalletmnemonicChars(walletPassword.getText().toString().toCharArray());
            SWTracker.i().setSelectedUWB(uwb);
            WalletFXHelperErrorBean loadWallet = null;
            try {
                loadWallet = WalletFXHelper.loadWallet(readPassword);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("Something went wrong");
            }
            if (loadWallet.isLoaded()) {
                System.out.println("Wallet is loaded");
                NewWalletBean nwb = new NewWalletBean();
                nwb.setName(walletName.getText().toString());
                nwb.setPassword(walletPassword.getText().toString().toCharArray());

                SWTracker.i().setNwb(nwb);

                Intent activity2Intent = new Intent(getApplicationContext(), HomeWalletActivity.class);
                startActivity(activity2Intent);
            } else {
                errorLogin = true;

                System.out.println("Wrong credentials!");
            }

            return null;
        }
    }

}