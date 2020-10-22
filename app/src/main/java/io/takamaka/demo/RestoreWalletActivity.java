package io.takamaka.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

public class RestoreWalletActivity extends MainController {

    Button walletRestoreSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_wallet);
        initMenu();
        initFormRestoreWallet();
    }

    public void initFormRestoreWallet() {
        walletRestoreSubmit = findViewById(R.id.wallet_restore_btn_submit);
        LinearLayout restoreWalletForm = findViewById(R.id.restore_wallet_form);
        walletRestoreSubmit.setOnClickListener(v -> {
            List<View> wrongFields = checkFieldsForm(restoreWalletForm);
            if (!wrongFields.isEmpty()) {
                highlightWrongForm(wrongFields);
            }
        });



        initWalletRestore();
    }

    public void initWalletRestore() {

    }

}