package io.takamaka.demo;

import android.os.Bundle;

public class SettingsActivity extends MainController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initMenu();
        setCurrentActivity(this);
    }
}