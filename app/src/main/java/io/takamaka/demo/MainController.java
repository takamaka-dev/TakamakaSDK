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
import java.util.ArrayList;
import java.util.List;

import io.takamaka.sdk.utils.FileHelper;
import io.takamaka.sdk.wallet.InstanceWalletKeystoreInterface;

public class MainController extends AppCompatActivity {

    protected InstanceWalletKeystoreInterface iwk;

    public InstanceWalletKeystoreInterface getIwk() {
        return iwk;
    }

    public void setIwk(InstanceWalletKeystoreInterface iwk) {
        this.iwk = iwk;
    }

    FloatingActionButton takamakaButton, loginButton, tokensButton, createWalletFab, restoreWalletFab;

    Boolean isAllFabsVisible;

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
        loginButton.setVisibility(View.GONE);
        tokensButton.setVisibility(View.GONE);
        createWalletFab.setVisibility(View.GONE);
        restoreWalletFab.setVisibility(View.GONE);

        isAllFabsVisible = false;

        takamakaButton.setOnClickListener(
                view -> {
                    if (!isAllFabsVisible) {
                        loginButton.show();
                        tokensButton.show();
                        createWalletFab.show();
                        restoreWalletFab.show();
                        isAllFabsVisible = true;
                    } else {
                        loginButton.hide();
                        tokensButton.hide();
                        createWalletFab.hide();
                        restoreWalletFab.hide();
                        isAllFabsVisible = false;
                    }
                });

        tokensButton.setOnClickListener(
                view -> {
                    Intent activity2Intent = new Intent(getApplicationContext(), SendTokenActivity.class);
                    startActivity(activity2Intent);
                });

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