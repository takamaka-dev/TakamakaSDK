package io.takamaka.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.List;

import io.takamaka.sdk.exceptions.threadSafeUtils.HashAlgorithmNotFoundException;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashEncodeException;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashProviderNotFoundException;
import io.takamaka.sdk.exceptions.wallet.UnlockWalletException;
import io.takamaka.sdk.exceptions.wallet.WalletException;
import io.takamaka.sdk.utils.IdentiColorHelper;
import io.takamaka.sdk.wallet.InstanceWalletKeyStoreBCED25519;
import io.takamaka.sdk.wallet.InstanceWalletKeystoreInterface;

public class CreateWalletActivity extends MainController {

    protected ProgressBar pgsBar;

    protected Context context;

    private InstanceWalletKeystoreInterface iwk;

    public InstanceWalletKeystoreInterface getIwk() {
        return iwk;
    }

    public void setIwk(InstanceWalletKeystoreInterface iwk) {
        this.iwk = iwk;
    }


    Button walletCreateSubmit;
    TextView internalNameField, passwordField;
    ImageView imageViewIdenticon;

    private String internalName, password;

    public String getInternalName() {
        return internalName;
    }

    public void setInternalName(String in) {
        internalName = in;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passwd) {
        password = passwd;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);
        imageViewIdenticon = findViewById(R.id.imageViewIdenticon);
        context = getApplicationContext();
        initMenu();
        initFormCreateWallet();
    }

    public void initFormCreateWallet() {
        pgsBar = (ProgressBar) findViewById(R.id.pBar);
        walletCreateSubmit = findViewById(R.id.create_wallet_submit);
        LinearLayout createWalletForm = findViewById(R.id.create_wallet_form);
        walletCreateSubmit.setOnClickListener(v -> {
            List<View> wrongFields = checkFieldsForm(createWalletForm);
            if (!wrongFields.isEmpty()) {
                highlightWrongForm(wrongFields);
            } else {
                internalNameField = findViewById(R.id.create_wallet_name);
                passwordField = findViewById(R.id.inputPasswordText);
                setInternalName(internalNameField.getText().toString());
                setPassword(passwordField.getText().toString());
                initWalletCreation();
            }
        });
    }

    public void initWalletCreation() {
        //setIwk(new InstanceWalletKeyStoreBCED25519(getInternalName(), getPassword()));
        //System.out.println("Wallet creato: " + getIwk().getPublicKeyAtIndexURL64(0));
        CreateWalletTasks cwt = new CreateWalletTasks();
        cwt.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class CreateWalletTasks extends AsyncTask<Void, Void, Void> {
        protected void onPreExecute() {
            pgsBar.setVisibility(View.VISIBLE);
        }

        protected void onProgressUpdate(Void... progress) {
            pgsBar.setVisibility(View.VISIBLE);
        }

        protected void onPostExecute(Void result) {
            try {
                pgsBar.setVisibility(View.INVISIBLE);
                System.out.println("Wallet creato: " + getIwk().getPublicKeyAtIndexURL64(0));

                imageViewIdenticon.setImageDrawable(new BitmapDrawable(getResources(), IdentiColorHelper.identiconMatrixGenerator(getIwk().getPublicKeyAtIndexURL64(0))));

            } catch (WalletException | HashEncodeException | HashAlgorithmNotFoundException | HashProviderNotFoundException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                setIwk(new InstanceWalletKeyStoreBCED25519(getInternalName(), getPassword()));
            } catch (UnlockWalletException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}