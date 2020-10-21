package io.takamaka.demo;

import android.os.Bundle;

public class RestoreWalletActivity extends MainController {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_wallet);
        initMenu();
    }
}