package io.takamaka.demo;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

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

    TextView inputFromAddressText, inputToAddressText, inputTextNumberTkg, inputTextNumberTkr, textEsito;
    ImageView imageViewFrom, imageViewTo;
    Button inputButtonVerify, inputButtonSendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            fromAddress = SWTracker.i().getIwk().getPublicKeyAtIndexURL64(SWTracker.i().getCurrIndex());
            toAddress = SWTracker.i().getIwk().getPublicKeyAtIndexURL64(WalletFXHelper.generateRandomNumber());
        } catch (WalletException e) {
            e.printStackTrace();
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
        textEsito = findViewById(R.id.text_esito_optional);
        imageViewFrom = findViewById(R.id.image_view_from);
        imageViewTo = findViewById(R.id.image_view_to);
        inputButtonVerify = findViewById(R.id.input_button_verify);
        inputButtonSendToken = findViewById(R.id.input_send_token);

        inputButtonSendToken.setEnabled(false);

        ConstraintLayout formSendToken = findViewById(R.id.form_send_token);

        inputFromAddressText.setText(fromAddress);
        imageViewFrom.setImageDrawable(new BitmapDrawable(getResources(), IdentiColorHelper.identiconMatrixGenerator(fromAddress)));
        inputToAddressText.setText(toAddress);
        imageViewTo.setImageDrawable(new BitmapDrawable(getResources(), IdentiColorHelper.identiconMatrixGenerator(toAddress)));

        inputButtonVerify.setOnClickListener(v -> {
            List<View> wrongFields = checkFieldsForm(formSendToken);
            wrongFields.forEach(e -> {
                System.out.println(e);
            });
            if (!wrongFields.isEmpty()) {
                highlightWrongForm(wrongFields);
            } else {
                inputButtonSendToken.setEnabled(true);
            }
        });
    }
}