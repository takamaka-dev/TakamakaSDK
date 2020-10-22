package io.takamaka.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainController extends AppCompatActivity {

    FloatingActionButton takamakaButton, loginButton, tokensButton, createWalletFab, restoreWalletFab;

    Boolean isAllFabsVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMenu();
    }

    protected void highlightWrongForm(List<View> wrongFields) {
        wrongFields.forEach(v-> ((TextView) v).setError("Field error"));
    }

    protected List<View> checkFieldsForm(LinearLayout form) {
        int childCount = form.getChildCount();
        List<View> wrongFields = new ArrayList<>();
        View passwordField = null;
        String password = "";
        String retypePassword;
        for (int i = 0; i < childCount; i++) {
            View v = form.getChildAt(i);
            if (v instanceof TextView) {
                if (((TextView) v).getText().toString().equals("")) {
                    wrongFields.add(v);
                }

                String idString = v.getResources().getResourceEntryName(v.getId()); // widgetA1
                if (idString.contains("inputPasswordText")) {
                    password = ((TextView) v).getText().toString();
                    passwordField = v;
                }
                if (idString.contains("inputPasswordRetypeText")) {
                    retypePassword = ((TextView) v).getText().toString();
                    if (!password.equals(retypePassword)) {
                        wrongFields.add(v);
                        wrongFields.add(passwordField);
                    }
                }
            }
        }

        return wrongFields;
    }

    protected void initMenu() {
        // Register all the FABs with their IDs
        // This FAB button is the Parent
        takamakaButton = findViewById(R.id.takamaka_button);
        // FAB button
        loginButton = findViewById(R.id.login_button);
        tokensButton = findViewById(R.id.tokens_button);
        createWalletFab = findViewById(R.id.create_wallet);
        restoreWalletFab = findViewById(R.id.restore_wallet);
        // Also register the action name text, of all the FABs.


        // Now set all the FABs and all the action name
        // texts as GONE
        loginButton.setVisibility(View.GONE);
        tokensButton.setVisibility(View.GONE);
        createWalletFab.setVisibility(View.GONE);
        restoreWalletFab.setVisibility(View.GONE);


        // make the boolean variable as false, as all the
        // action name texts and all the sub FABs are invisible
        isAllFabsVisible = false;

        // We will make all the FABs and action name texts
        // visible only when Parent FAB button is clicked So
        // we have to handle the Parent FAB button first, by
        // using setOnClickListener you can see below
        takamakaButton.setOnClickListener(
                view -> {
                    if (!isAllFabsVisible) {

                        // when isAllFabsVisible becomes
                        // true make all the action name
                        // texts and FABs VISIBLE.
                        loginButton.show();
                        tokensButton.show();
                        createWalletFab.show();
                        restoreWalletFab.show();


                        // make the boolean variable true as
                        // we have set the sub FABs
                        // visibility to GONE
                        isAllFabsVisible = true;
                    } else {

                        // when isAllFabsVisible becomes
                        // true make all the action name
                        // texts and FABs GONE.
                        loginButton.hide();
                        tokensButton.hide();
                        createWalletFab.hide();
                        restoreWalletFab.hide();

                        // make the boolean variable false
                        // as we have set the sub FABs
                        // visibility to GONE
                        isAllFabsVisible = false;
                    }
                });

        // below is the sample action to handle add person
        // FAB. Here it shows simple Toast msg. The Toast
        // will be shown only when they are visible and only
        // when user clicks on them
        tokensButton.setOnClickListener(
                view -> {
                    Intent activity2Intent = new Intent(getApplicationContext(), SendTokenActivity.class);
                    startActivity(activity2Intent);
                });

        // below is the sample action to handle add alarm
        // FAB. Here it shows simple Toast msg The Toast
        // will be shown only when they are visible and only
        // when user clicks on them
        loginButton.setOnClickListener(
                view -> {
                    Intent activity2Intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(activity2Intent);
                });

        createWalletFab.setOnClickListener(
                v -> {
                    Intent activity2Intent = new Intent(getApplicationContext(), CreateWalletActivity.class);
                    startActivity(activity2Intent);
                });

        restoreWalletFab.setOnClickListener(
                v -> {
                    Intent activity2Intent = new Intent(getApplicationContext(), RestoreWalletActivity.class);
                    startActivity(activity2Intent);
                }
        );
    }
}