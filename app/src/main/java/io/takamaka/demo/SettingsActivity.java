package io.takamaka.demo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;

import io.takamaka.demo.utils.SWTracker;

public class SettingsActivity extends MainController {

    Button radioButtonProduction, radioButtonTest;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initMenu();
        setCurrentActivity(this);
        radioButtonProduction = findViewById(R.id.radioButtonProduction);
        radioButtonTest = findViewById(R.id.radioButtonTest);
        radioGroup = findViewById(R.id.radioGroup);
        if (SWTracker.getCurrentSetting().equals("prod")) {
            radioGroup.check(radioButtonProduction.getId());
        } else if (SWTracker.getCurrentSetting().equals("test")) {
            radioGroup.check(radioButtonTest.getId());
        }

        radioButtonProduction.setOnClickListener(
                e -> {
                    SWTracker.setCurrentSetting("prod");
                }
        );

        radioButtonTest.setOnClickListener(
                e -> {
                    SWTracker.setCurrentSetting("test");
                }
        );
    }


}