package io.takamaka.demo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchProviderException;

import javax.crypto.NoSuchPaddingException;

import io.takamaka.demo.utils.SWTracker;
import io.takamaka.demo.utils.WalletFXHelper;
import io.takamaka.sdk.exceptions.wallet.UnlockWalletException;
import io.takamaka.sdk.wallet.WalletHelper;

public class SettingsActivity extends MainController {

    Spinner spinnerButton;
    Button btnShowWords;
    TextView wordsArea;
    String words = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initMenu();
        setCurrentActivity(this);

        spinnerButton = findViewById(R.id.spinner_button);
        btnShowWords = findViewById(R.id.btn_show_words);
        wordsArea = findViewById(R.id.words_area_text);

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

        btnShowWords.setOnClickListener(
                view -> {
                    Path walletPath = WalletFXHelper.getWalletPath();
                    String walletPassword = String.valueOf(SWTracker.i().getNwb().getPassword());

                    try {
                        words = WalletHelper.readKeyFile(walletPath, walletPassword).getWords();
                        wordsArea.setText(words);
                    } catch (FileNotFoundException | NoSuchProviderException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | UnlockWalletException e) {
                        e.printStackTrace();
                    }


                }
        );

    }


}