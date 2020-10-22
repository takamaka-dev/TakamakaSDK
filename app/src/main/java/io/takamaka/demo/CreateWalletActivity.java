package io.takamaka.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CreateWalletActivity extends MainController {

    Button walletCreateSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);
        initMenu();
        initFormCreateWallet();
    }

    public void initFormCreateWallet() {
        walletCreateSubmit = findViewById(R.id.wallet_create_submit);
        LinearLayout createWalletForm = findViewById(R.id.create_wallet_form);
        walletCreateSubmit.setOnClickListener(v -> {
            List<View> wrongFields = checkFieldsForm(createWalletForm);
            if (!wrongFields.isEmpty()) {
                highlightWrongForm(wrongFields);
            }
        });
        initWalletCreation();
    }

    public void initWalletCreation() {

    }

}