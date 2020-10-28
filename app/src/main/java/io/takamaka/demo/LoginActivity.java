package io.takamaka.demo;

import android.annotation.SuppressLint;
import android.content.Context;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;


public class LoginActivity extends MainController {

    protected ProgressBar pgsBar;

    protected Context context;

    Button walletLoginSubmit;
    TextView walletName, walletPassword;

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
        walletLoginSubmit = findViewById(R.id.login_button);
        LinearLayout loginWalletForm = findViewById(R.id.wallet_login_form);
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
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String readPassword = walletPassword.getText().toString();
            WalletFXHelperErrorBean loadWallet = WalletFXHelper.loadWallet(readPassword);
            if (loadWallet.isLoaded()) {
                String words = WalletHelper.readKeyFile(WalletFXHelper.getCurrentWalletpath(), readPassword).getWords();
                Perso.setWords(words);
                System.out.println("Parole: "+ words);
                //SWTracker.i().setIwk(iwk);
                System.out.println("Wallet succesfully loaded");
                JFXTabPane mainTabPanel = Perso.i().getMainTabPanel();
                System.out.println("remove trailing from " + mainTabPanel);
                Perso.removeTabArray(Perso.i().getTrailingTabArray(), mainTabPanel, true);
                System.out.println("add tabs to " + mainTabPanel);
                Perso.removeTabArray(Perso.i().getWalletLockedTabArray(), mainTabPanel, false);
                System.out.println("add trailing");
                Perso.removeTabArray(Perso.i().getTrailingTabArray(), mainTabPanel, false);

            } else {

                PopOver popOver = Perso.i().getPopOverWarning(SWInt.i().getMessage("wrong_password"), "\n", SWInt.i().getMessage("retype_password"));
                popOver.setArrowLocation(ArrowLocation.TOP_CENTER);
                popOver.setAnimated(true);
                popOver.show(openApri);
                System.out.println(loadWallet.getError());
                return;
            }

            return null;
        }
    }

}