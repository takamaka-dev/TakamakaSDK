package io.takamaka.demo;

import android.os.Bundle;

public class SendTokenActivity extends MainController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_token);
        initMenu();
        setCurrentActivity(this);
    }
}