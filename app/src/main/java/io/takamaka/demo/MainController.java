package io.takamaka.demo;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.takamaka.demo.utils.SWTracker;
import io.takamaka.sdk.utils.FileHelper;

public class MainController extends AppCompatActivity {
    FloatingActionButton takamakaButton, loginButton, tokensButton, createWalletFab, restoreWalletFab, settingsButton, homeButton, logoutButton, explorerButton;

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
        SWTracker.i().initSettings();
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
        wrongFields.forEach(v -> ((TextView) v).setError("Field error"));
    }

    protected List<View> checkFieldsForm(ViewGroup form) {
        int childCount = form.getChildCount();
        List<View> wrongFields = new ArrayList<>();
        View passwordField = null;
        String password = "";
        String retypePassword;

        for (int i = 0; i < childCount; i++) {
            View v = form.getChildAt(i);
            if (v instanceof TextView) {
                String idString = v.getResources().getResourceEntryName(v.getId());
                if (idString.contains("optional")) {
                    continue;
                }
                if (((TextView) v).getText().toString().equals("")) {
                    wrongFields.add(v);
                }
                if (idString.equals("input_to_address_text") && ((TextView) v).getText().length() < 44) {
                    wrongFields.add(v);
                }
                if (idString.contains("inputPasswordText")) {
                    password = ((TextView) v).getText().toString();
                    if (password.length() < 8) {
                        wrongFields.add(v);
                    }
                    passwordField = v;
                }
                if (idString.contains("inputPasswordRetypeText")) {
                    retypePassword = ((TextView) v).getText().toString();

                    if (!password.equals(retypePassword) || retypePassword.length() < 8) {
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
        homeButton = findViewById(R.id.home_button);
        logoutButton = findViewById(R.id.logout_button);
        explorerButton = findViewById(R.id.explorer_button);
        loginButton.setVisibility(View.GONE);
        tokensButton.setVisibility(View.GONE);
        createWalletFab.setVisibility(View.GONE);
        restoreWalletFab.setVisibility(View.GONE);
        settingsButton.setVisibility(View.GONE);
        homeButton.setVisibility(View.GONE);
        logoutButton.setVisibility(View.GONE);
        explorerButton.setVisibility(View.GONE);

        isAllFabsVisible = false;

        takamakaButton.setOnClickListener(
                view -> {
                    if (!isAllFabsVisible) {
                        if (!(getCurrentActivity() instanceof LoginActivity)) {
                            loginButton.show();
                        }



                        // login
                        if (logged()) {
                            if (!(getCurrentActivity() instanceof ExplorerActivity)) {
                                explorerButton.show();
                            }
                            logoutButton.show();
                            tokensButton.show();
                            if (getCurrentActivity() instanceof SendTokenActivity) {
                                tokensButton.hide();
                            }
                            settingsButton.show();
                            if (getCurrentActivity() instanceof SettingsActivity) {
                                settingsButton.hide();
                            }

                            System.out.println("Logged");
                            System.out.println(getCurrentActivity() instanceof HomeWalletActivity);
                            if (getCurrentActivity() instanceof HomeWalletActivity) {
                                loginButton.hide();
                                createWalletFab.hide();
                                restoreWalletFab.hide();
                            } else {
                                System.out.println("ciao");
                                homeButton.show();
                            }

                            isAllFabsVisible = true;
                            return;
                        }

                        // Create Wallet

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
                        homeButton.hide();
                        explorerButton.hide();
                        isAllFabsVisible = false;
                    }
                });

        tokensButton.setOnClickListener(
                view -> {
                    Intent activityTokens = new Intent(getApplicationContext(), SendTokenActivity.class);
                    startActivity(activityTokens);
                    System.out.println("Application context: " + getApplicationContext());
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

        homeButton.setOnClickListener(
                v -> {
                    Intent activitySettings = new Intent(getApplicationContext(), HomeWalletActivity.class);
                    startActivity(activitySettings);
                }
        );

        logoutButton.setOnClickListener(
                v -> {
                    SWTracker.i().resetUser();
                    Intent activitySettings = new Intent(getApplicationContext(), MainController.class);
                    startActivity(activitySettings);
                }
        );

        explorerButton.setOnClickListener(
                v -> {
                    Intent activitySettings = new Intent(getApplicationContext(), ExplorerActivity.class);
                    startActivity(activitySettings);
                }
        );

    }

    public boolean logged() {
        return SWTracker.i().getIwk() != null;
    }

}