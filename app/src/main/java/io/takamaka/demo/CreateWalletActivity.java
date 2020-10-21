package io.takamaka.demo;

import android.os.Bundle;

public class CreateWalletActivity extends MainController {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);
        initMenu();
        initFormCreateWallet();
    }

    private void initFormCreateWallet() {

    }
}