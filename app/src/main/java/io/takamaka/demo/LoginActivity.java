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

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    LinearLayout walletLoginForm;
    String selectedWalletName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initMenu();
        setCurrentActivity(this);
        initFormLoginWallet();
    }

    private void clearWalletListContent() {
        LinearLayout internalContainerWallets = findViewById(R.id.linear_layout_wallets_container);
        int childCount = internalContainerWallets.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = internalContainerWallets.getChildAt(i);
            if (v.getTag() == null) {
                v.setBackgroundColor(Color.parseColor("#5c82a1"));
            }

        }
    }

    private String getWalletName(LinearLayout l) {
        int childCount = l.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = l.getChildAt(i);
            if (v.getTag() != null && v.getTag().equals("walletNameTag")) {
                TextView tv = (TextView) v;
                return tv.getText().toString();
            }

        }
        return "";
    }

    private List<String> retrieveWalletList() {
        List<String> l = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(FileHelper.getWalletDirectoryPath())) {

            List<String> result = walk.filter(Files::isRegularFile)
                    .map(x -> x.getFileName().toString().split("\\.")[0]).collect(Collectors.toList());

            return result;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return l;
    }

    private void initFormLoginWallet() {
        pgsBar = findViewById(R.id.progressBar);
        pgsBar.setVisibility(View.GONE);
        labelError = findViewById(R.id.label_error);
        labelError.setText("");
        walletLoginForm = findViewById(R.id.wallet_login_form);
        walletPassword = findViewById(R.id.wallet_password);
        walletPassword.setEnabled(false);
        walletLoginSubmit = findViewById(R.id.button_login);
        walletLoginSubmit.setEnabled(false);
        List<String> listOfStoredWallets = retrieveWalletList();

        LinearLayout internalContainerWallets = findViewById(R.id.linear_layout_wallets_container);

        for(String s: listOfStoredWallets) {
            LinearLayout l = new LinearLayout(this);
            l.setOrientation(LinearLayout.HORIZONTAL);
            l.setBackgroundColor(Color.parseColor("#5c82a1"));
            ImageView iv = new ImageView(this);
            iv.setImageResource(R.drawable.wallet_50px_white);
            TextView walletName = new TextView(this);
            walletName.setTag("walletNameTag");
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
            v.setTag("separator");

            internalContainerWallets.addView(l);
            internalContainerWallets.addView(v);

            l.setOnClickListener(e -> {
                clearWalletListContent();
                l.setBackgroundColor(Color.parseColor("#173c5a"));
                walletPassword.setEnabled(true);
                walletLoginSubmit.setEnabled(true);
                selectedWalletName = getWalletName(l);
                System.out.println(selectedWalletName);

            });

        }

        walletLoginSubmit.setOnClickListener(event -> {

            System.out.println("Selected wallet: " + selectedWalletName);
            System.out.println("Password: " + walletPassword.getText().toString());

            List<View> wrongFields = checkFieldsForm(walletLoginForm);
            if (!wrongFields.isEmpty()) {
                highlightWrongForm(wrongFields);
            } else {
                walletPassword = findViewById(R.id.wallet_password);
                initWalletLogin();
            }
        });

        if (listOfStoredWallets.isEmpty()) {
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
            uwb.setInternalName(selectedWalletName);
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
                nwb.setName(selectedWalletName);
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