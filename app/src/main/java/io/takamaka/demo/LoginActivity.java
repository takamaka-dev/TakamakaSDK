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

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        pgsBar = (ProgressBar) findViewById(R.id.pBar);
        walletLoginSubmit = findViewById(R.id.button_login);
        LinearLayout loginWalletForm = findViewById(R.id.wallet_login_form);
        labelError = findViewById(R.id.label_error);
        labelError.setText("");
        walletLoginSubmit.setOnClickListener(v -> {
            List<View> wrongFields = checkFieldsForm(loginWalletForm);
            if (!wrongFields.isEmpty()) {
                highlightWrongForm(wrongFields);
            } else {
                walletName = findViewById(R.id.wallet_name);
                walletPassword = findViewById(R.id.wallet_password);
                initWalletLogin();
            }
        });
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
            SWTracker.setSelectedUWB(uwb);
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