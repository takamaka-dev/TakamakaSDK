package io.takamaka.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import io.takamaka.sdk.exceptions.wallet.WalletException;
import io.takamaka.sdk.wallet.InstanceWalletKeyStoreBCED25519;
import io.takamaka.sdk.wallet.InstanceWalletKeystoreInterface;

public class CreateWalletActivity extends MainController {

    Button walletCreateSubmit;
    TextView internalNameField, passwordField;
    private InstanceWalletKeystoreInterface iwk;
    private String internalName, password;

    public InstanceWalletKeystoreInterface getIwk() {
        return iwk;
    }

    public void setIwk(InstanceWalletKeystoreInterface iwk) {
        this.iwk = iwk;
    }

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
        initMenu();
        initFormCreateWallet();
    }

    public void initFormCreateWallet() {
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
                try {
                    initWalletCreation();
                } catch (WalletException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void initWalletCreation() throws WalletException {
        iwk = new InstanceWalletKeyStoreBCED25519(getInternalName(), getPassword());
        setIwk(iwk);
        System.out.println("Wallet creato: " + getIwk().getPublicKeyAtIndexURL64(0));
    }

}