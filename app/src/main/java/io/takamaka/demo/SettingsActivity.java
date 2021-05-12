package io.takamaka.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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




        /*radioButtonProduction = findViewById(R.id.radioButtonProduction);
        radioButtonTest = findViewById(R.id.radioButtonTest);
        radioGroup = findViewById(R.id.radioGroup);
        if (SWTracker.i().getCurrentSetting().equals("prod")) {
            radioGroup.check(radioButtonProduction.getId());
        } else if (SWTracker.i().getCurrentSetting().equals("test")) {
            radioGroup.check(radioButtonTest.getId());
        }



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
*/

        spinnerButton = findViewById(R.id.spinner_button);

        spinnerButton.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().contains("Test")) {
                    SWTracker.i().setCurrentSetting("test");
                } else if (parent.getItemAtPosition(position).toString().contains("Prod")) {
                    SWTracker.i().setCurrentSetting("prod");
                }
                SWTracker.setAccessToken(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


}