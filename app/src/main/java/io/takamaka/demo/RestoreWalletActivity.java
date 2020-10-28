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
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import javax.crypto.NoSuchPaddingException;

import io.takamaka.demo.utils.SWTracker;
import io.takamaka.sdk.exceptions.wallet.UnlockWalletException;
import io.takamaka.sdk.exceptions.wallet.WalletException;
import io.takamaka.sdk.main.defaults.DefaultInitParameters;
import io.takamaka.sdk.utils.FileHelper;
import io.takamaka.sdk.wallet.InstanceWalletKeyStoreBCED25519;
import io.takamaka.sdk.wallet.InstanceWalletKeystoreInterface;
import io.takamaka.sdk.wallet.NewWalletBean;
import io.takamaka.sdk.wallet.WalletHelper;

import static io.takamaka.sdk.globalContext.KeyContexts.WalletCypher.Ed25519BC;

public class RestoreWalletActivity extends MainController {

    Button walletRestoreSubmit;
    protected ProgressBar pgsBar;

    protected Context context;

    TextView restoreWalletName, restoreWalletPassword, textWords;

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    private String walletName, password, words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_wallet);
        restoreWalletName = findViewById(R.id.restore_wallet_name);
        restoreWalletPassword = findViewById(R.id.inputRestorePasswordText);
        textWords = findViewById(R.id.restore_wallet_words);
        pgsBar = findViewById(R.id.pBar);
        initMenu();
        initFormRestoreWallet();
        setCurrentActivity(this);
    }

    public void initFormRestoreWallet() {
        walletRestoreSubmit = findViewById(R.id.wallet_restore_btn_submit);
        LinearLayout restoreWalletForm = findViewById(R.id.restore_wallet_form);
        walletRestoreSubmit.setOnClickListener(v -> {
            List<View> wrongFields = checkFieldsForm(restoreWalletForm);
            if (!wrongFields.isEmpty()) {
                highlightWrongForm(wrongFields);
            } else {
                setWalletName(restoreWalletName.getText().toString());
                setPassword(restoreWalletPassword.getText().toString());
                setWords(textWords.getText().toString());
                initWalletRestore();
            }
        });


    }

    public void initWalletRestore() {
        RestoreWalletTask rwt = new RestoreWalletTask();
        rwt.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class RestoreWalletTask extends AsyncTask<Void, Void, Void> {
        protected void onPreExecute() {
            pgsBar.setVisibility(View.VISIBLE);
        }

        protected void onProgressUpdate(Void... progress) {
            pgsBar.setVisibility(View.VISIBLE);
        }

        protected void onPostExecute(Void result) {
            pgsBar.setVisibility(View.INVISIBLE);
            try {
                System.out.println("Wallet creato: " + SWTracker.i().getIwk().getPublicKeyAtIndexURL64(0));
            } catch (WalletException e) {
                e.printStackTrace();
            }

            NewWalletBean nwb = new NewWalletBean();
            nwb.setName(getWalletName());
            nwb.setPassword(getPassword().toCharArray());

            SWTracker.i().setNwb(nwb);

            Intent activity2Intent = new Intent(getApplicationContext(), HomeWalletActivity.class);
            startActivity(activity2Intent);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Path importWalletFromWords = null;
            List<String> words = Arrays.asList(getWords().split(" "));
            InstanceWalletKeystoreInterface iwk = null;
            try {
                importWalletFromWords = WalletHelper.importKeyFromWords(words, FileHelper.getDefaultWalletDirectoryPath(), getWalletName(), Ed25519BC, getPassword());
            } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException e) {
                e.printStackTrace();
            }

            String currentWalletName = getWalletName() + DefaultInitParameters.WALLET_EXTENSION;
            Path currentWalletPath = Paths.get(FileHelper.getDefaultWalletDirectoryPath().toString(), currentWalletName);

            try {
                FileHelper.copy(importWalletFromWords, currentWalletPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                iwk = new InstanceWalletKeyStoreBCED25519(getWalletName(), password);
                SWTracker.i().setIwk(iwk);
            } catch (UnlockWalletException e) {
                e.printStackTrace();
            }


            System.out.println("Done!");

            return null;
        }
    }

}