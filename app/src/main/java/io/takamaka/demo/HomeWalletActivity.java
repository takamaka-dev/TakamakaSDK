package io.takamaka.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HomeWalletActivity extends MainController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_wallet);
        initMenu();
    }
}