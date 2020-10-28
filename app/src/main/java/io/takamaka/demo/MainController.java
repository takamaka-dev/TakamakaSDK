package io.takamaka.demo;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.takamaka.demo.utils.SWTracker;
import io.takamaka.sdk.globalContext.FixedParameters;
import io.takamaka.sdk.main.defaults.DefaultInitParameters;
import io.takamaka.sdk.utils.ComboItemSettingsBookmarkUrl;
import io.takamaka.sdk.utils.FileHelper;
import io.takamaka.sdk.wallet.InstanceWalletKeystoreInterface;
import io.takamaka.sdk.wallet.NewWalletBean;

public class MainController extends AppCompatActivity {
    FloatingActionButton takamakaButton, loginButton, tokensButton, createWalletFab, restoreWalletFab, settingsButton;

    Boolean isAllFabsVisible;

    protected AppCompatActivity getCurrentActivity() {
        return currentActivity;
    }

    protected void setCurrentActivity(AppCompatActivity currentActivity) {
        this.currentActivity = currentActivity;
    }

    AppCompatActivity currentActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMenu();
        Context context = new ContextWrapper(getApplicationContext());
        System.out.println("Path dove poter scrivere: " + context.getExternalFilesDir(null));
        try {
            FileHelper.initProjectFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentActivity = this;
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
        takamakaButton = findViewById(R.id.takamaka_button);
        loginButton = findViewById(R.id.login_button);
        tokensButton = findViewById(R.id.tokens_button);
        createWalletFab = findViewById(R.id.create_wallet);
        restoreWalletFab = findViewById(R.id.restore_wallet);
        settingsButton = findViewById(R.id.settings_button);
        loginButton.setVisibility(View.GONE);
        tokensButton.setVisibility(View.GONE);
        createWalletFab.setVisibility(View.GONE);
        restoreWalletFab.setVisibility(View.GONE);
        settingsButton.setVisibility(View.GONE);

        isAllFabsVisible = false;

        takamakaButton.setOnClickListener(
                view -> {
                    if (!isAllFabsVisible) {
                        if (!(getCurrentActivity() instanceof LoginActivity)) {
                            loginButton.show();
                        }

                        if (logged()) {
                            tokensButton.show();
                            if (getCurrentActivity() instanceof SendTokenActivity) {
                                tokensButton.hide();
                            }
                            settingsButton.show();
                            if (getCurrentActivity() instanceof SettingsActivity) {
                                settingsButton.hide();
                            }
                        }

                        if (!(getCurrentActivity() instanceof CreateWalletActivity)) {
                            createWalletFab.show();
                        }

                        if (!(getCurrentActivity() instanceof RestoreWalletActivity)) {
                            restoreWalletFab.show();
                        }

                        isAllFabsVisible = true;
                    } else {
                        loginButton.hide();
                        tokensButton.hide();
                        createWalletFab.hide();
                        restoreWalletFab.hide();
                        settingsButton.hide();
                        isAllFabsVisible = false;
                    }
                });

        tokensButton.setOnClickListener(
                view -> {
                    Intent activityTokens = new Intent(getApplicationContext(), SendTokenActivity.class);
                    startActivity(activityTokens);
                });

        loginButton.setOnClickListener(
                view -> {
                    Intent activityLogin = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(activityLogin);
                });

        createWalletFab.setOnClickListener(
                v -> {
                    Intent activityCreate = new Intent(getApplicationContext(), CreateWalletActivity.class);
                    startActivity(activityCreate);
                });

        restoreWalletFab.setOnClickListener(
                v -> {
                    Intent activityRestore = new Intent(getApplicationContext(), RestoreWalletActivity.class);
                    startActivity(activityRestore);
                }
        );

        settingsButton.setOnClickListener(
                v -> {
                    Intent activitySettings = new Intent(getApplicationContext(), SettingsActivity.class);
                    startActivity(activitySettings);
                }
        );

    }

    public boolean logged() {
        return SWTracker.i().getIwk() != null;
    }

}