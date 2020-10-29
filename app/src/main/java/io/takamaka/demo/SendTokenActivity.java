package io.takamaka.demo;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import io.takamaka.demo.utils.SWTracker;
import io.takamaka.demo.utils.WalletFXHelper;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashAlgorithmNotFoundException;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashEncodeException;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashProviderNotFoundException;
import io.takamaka.sdk.exceptions.wallet.WalletException;
import io.takamaka.sdk.utils.IdentiColorHelper;

public class SendTokenActivity extends MainController {

    private String fromAddress, toAddress;

    EditText inputFromAddressText, inputToAddressText, inputTextNumberTkg, inputTextNumberTkr, inputTextMessage, textEsito;
    ImageView imageViewFrom, imageViewTo;
    Button inputButtonVerify, inputButtonSendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            fromAddress = SWTracker.i().getIwk().getPublicKeyAtIndexURL64(SWTracker.i().getCurrIndex());
            toAddress = SWTracker.i().getIwk().getPublicKeyAtIndexURL64(WalletFXHelper.generateRandomNumber());
        } catch (WalletException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
            fromAddress = "";
            toAddress = "";
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_token);
        initMenu();
        setCurrentActivity(this);
        try {
            initSendTokenForm();
        } catch (WalletException | HashEncodeException | HashAlgorithmNotFoundException | HashProviderNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initSendTokenForm() throws WalletException, HashEncodeException, HashAlgorithmNotFoundException, HashProviderNotFoundException {
        inputFromAddressText = findViewById(R.id.input_from_address_text);
        inputFromAddressText.setEnabled(false);
        inputToAddressText = findViewById(R.id.input_to_address_text);
        inputTextNumberTkg = findViewById(R.id.input_text_number_tkg);
        inputTextNumberTkr = findViewById(R.id.input_text_number_tkr);
        inputTextMessage = findViewById(R.id.input_text_message_optional);
        textEsito = findViewById(R.id.text_esito_optional);
        imageViewFrom = findViewById(R.id.image_view_from);
        imageViewTo = findViewById(R.id.image_view_to);
        inputButtonVerify = findViewById(R.id.input_button_verify);
        inputButtonSendToken = findViewById(R.id.input_send_token);

        inputButtonSendToken.setEnabled(false);
        inputButtonSendToken.setEnabled(false);

        List<LinearLayout> forms = new ArrayList<>();

        LinearLayout formToAddress = findViewById(R.id.form_to_address);
        LinearLayout formToken = findViewById(R.id.form_send_token);
        LinearLayout formTokenCurrencies = findViewById(R.id.form_send_token_currencies);

        forms.add(formToAddress);
        forms.add(formToken);
        forms.add(formTokenCurrencies);

        inputFromAddressText.setText(fromAddress);

        inputTextMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputButtonSendToken.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputTextNumberTkr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputButtonSendToken.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputTextNumberTkg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputButtonSendToken.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputToAddressText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() == 44) {
                    try {
                        imageViewTo.setImageDrawable(new BitmapDrawable(getResources(), IdentiColorHelper.identiconMatrixGenerator(s.toString())));
                    } catch (HashEncodeException | HashAlgorithmNotFoundException | HashProviderNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                inputButtonSendToken.setEnabled(false);
            }
        });

        imageViewFrom.setImageDrawable(new BitmapDrawable(getResources(), IdentiColorHelper.identiconMatrixGenerator(fromAddress)));
        inputToAddressText.setText(toAddress);
        imageViewTo.setImageDrawable(new BitmapDrawable(getResources(), IdentiColorHelper.identiconMatrixGenerator(toAddress)));

        inputButtonVerify.setOnClickListener(v -> {
            System.out.println("Clicked! verify");
            List<View> wrongFields = new ArrayList<>();
            forms.forEach(singleForm -> {
                wrongFields.addAll(checkFieldsForm(singleForm));
            });

            wrongFields.forEach(e -> {
                System.out.println(getResources().getResourceEntryName(e.getId()));
            });

            System.out.println("wrong fields size: " + wrongFields.size());

            if (!wrongFields.isEmpty()) {
                highlightWrongForm(wrongFields);
            } else {
                inputButtonSendToken.setEnabled(true);
            }
        });
    }
}