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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.takamaka.demo.utils.SWTracker;
import io.takamaka.demo.utils.WalletFXHelper;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashAlgorithmNotFoundException;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashEncodeException;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashProviderNotFoundException;
import io.takamaka.sdk.exceptions.wallet.TransactionCanNotBeCreatedException;
import io.takamaka.sdk.exceptions.wallet.WalletException;
import io.takamaka.sdk.transactions.InternalTransactionBean;
import io.takamaka.sdk.transactions.TransactionBean;
import io.takamaka.sdk.transactions.fee.FeeBean;
import io.takamaka.sdk.transactions.fee.TransactionFeeCalculator;
import io.takamaka.sdk.utils.IdentiColorHelper;
import io.takamaka.sdk.utils.threadSafeUtils.TkmTextUtils;
import io.takamaka.sdk.wallet.TkmWallet;
import io.takamaka.sdk.wallet.TransactionBox;

import static io.takamaka.sdk.globalContext.KeyContexts.TransactionType.PAY;
import static io.takamaka.sdk.main.defaults.TransactionGenerator.getTransactionBean;

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
        textEsito.setEnabled(false);
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

            if (wrongFields.size() == 1 && getResources().getResourceEntryName(wrongFields.get(0).getId()).contains("input_text_number_tk")) {
                inputTextNumberTkg.setError(null);
                inputTextNumberTkr.setError(null);
                wrongFields.clear();
            }

            System.out.println("wrong fields size: " + wrongFields.size());

            if (!wrongFields.isEmpty()) {
                highlightWrongForm(wrongFields);
            } else {
                InternalTransactionBean itb = null;
                try {
                    itb = getTransactionBean(
                            PAY,
                            SWTracker.i().getIwk().getPublicKeyAtIndexURL64(SWTracker.i().getCurrentAddressNumber()),
                            inputToAddressText.getText().toString(),
                            new BigInteger(inputTextNumberTkg.getText().toString().isEmpty() ? "0" : inputTextNumberTkg.getText().toString()),
                            new BigInteger(inputTextNumberTkr.getText().toString().equals("") ? "0" : inputTextNumberTkr.getText().toString()),
                            inputTextMessage.getText().toString(),
                            new Date((new Date()).getTime() + 60000L* 5) ,
                            0, 0
                    );
                } catch (WalletException e) {
                    e.printStackTrace();
                }
                TransactionBean genericTRA = null;

                System.out.println("Internal transaction bean: " + itb);

                try {
                    genericTRA = TkmWallet.createGenericTransaction(
                            itb,
                            SWTracker.i().getIwk(),
                            SWTracker.i().getCurrentAddressNumber());
                } catch (TransactionCanNotBeCreatedException e) {
                    e.printStackTrace();
                }

                String txJson = TkmTextUtils.toJson(genericTRA);
                TransactionBox tbox = TkmWallet.verifyTransactionIntegrity(txJson);

                System.out.println("TBOXONE:" + tbox.getItb().getFrom());

                FeeBean feeBean = TransactionFeeCalculator.getFeeBean(tbox);
                if (feeBean == null) {
                    textEsito.setError("Unexcepted validation error");

                } else {
                    String cpu = feeBean.getCpu().toString();
                    String disk = feeBean.getDisk().toString();
                    String mem = feeBean.getMemory().toString();
                    textEsito.setText("CPU: " + cpu + " MEM: " + mem + " DISK: " + disk);
                    inputButtonSendToken.setEnabled(true);
                }
            }
        });



    }
}