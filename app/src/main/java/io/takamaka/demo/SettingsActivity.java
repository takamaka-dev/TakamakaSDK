package io.takamaka.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import io.takamaka.demo.utils.SWTracker;

public class SettingsActivity extends MainController {

    Spinner spinnerButton;
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
        if (SWTracker.i().getCurrentSetting().equals("prod")) {
            radioGroup.check(radioButtonProduction.getId());
        } else if (SWTracker.i().getCurrentSetting().equals("test")) {
            radioGroup.check(radioButtonTest.getId());
        }

        System.out.println("Current settings: " + SWTracker.i().getCurrentSetting());

        radioButtonProduction.setOnClickListener(
                e -> {
                    SWTracker.i().setCurrentSetting("prod");
                }
        );

        radioButtonTest.setOnClickListener(
                e -> {
                    SWTracker.i().setCurrentSetting("test");
                }
        );


        spinnerButton = findViewById(R.id.spinner_button);

        int childCount = spinnerButton.getChildCount();
        for (int sIndex = 1; sIndex < childCount; sIndex++) {

            View v = spinnerButton.getChildAt(sIndex);
            v.setOnClickListener(e-> {

                if(v.getTag().equals("test_env")){
                    SWTracker.i().setCurrentSetting("test");

                } else if(v.getTag().equals("prod_env")) {
                    SWTracker.i().setCurrentSetting("prod");


                    }

            });

        }
    }


}