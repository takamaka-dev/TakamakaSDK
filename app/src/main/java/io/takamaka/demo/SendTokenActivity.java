package io.takamaka.demo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.takamaka.demo.utils.SWTracker;
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
import io.takamaka.sdk.utils.TkmSignUtils;
import io.takamaka.sdk.utils.threadSafeUtils.TkmTextUtils;
import io.takamaka.sdk.wallet.TkmWallet;
import io.takamaka.sdk.wallet.TransactionBox;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static io.takamaka.sdk.globalContext.KeyContexts.TransactionType.PAY;
import static io.takamaka.sdk.main.defaults.TransactionGenerator.getTransactionBean;

public class SendTokenActivity extends MainController {

    private String fromAddress, toAddress;
    private TransactionBox tbox;

    public boolean getTxResult() {
        return txResult;
    }

    public void setTxResult(boolean txResult) {
        this.txResult = txResult;
    }

    private boolean txResult;

    EditText inputFromAddressText, inputToAddressText, inputTextNumberTkg, inputTextNumberTkr, inputTextMessage, textEsito;
    ImageView imageViewFrom, imageViewTo;
    Button inputButtonVerify, inputButtonSendToken;
    TextView textFinalSubmitEsito;

    public TransactionBox getTbox() {
        return tbox;
    }

    public void setTbox(TransactionBox tbox) {
        this.tbox = tbox;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            fromAddress = SWTracker.i().getIwk().getPublicKeyAtIndexURL64(SWTracker.getCurrIndex());
            toAddress = "Insert here your destination address";
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
        textFinalSubmitEsito = findViewById(R.id.text_final_submit_esito_optional);

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
            if (inputToAddressText.getText().toString().length() != 44) {
                inputToAddressText.setError("Wrong address");
                return;
            }
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
                    //double inputTextNumberTkgLong = Double.parseDouble(inputTextNumberTkg.getText().toString().isEmpty() ? "0" : inputTextNumberTkg.getText().toString()) * DefaultInitParameters.BIG_INTEGER;
                    //double inputTextNumberTkrLong = Double.parseDouble(inputTextNumberTkr.getText().toString().isEmpty() ? "0" : inputTextNumberTkr.getText().toString()) * DefaultInitParameters.BIG_INTEGER;
                    itb = getTransactionBean(
                            PAY,
                            SWTracker.i().getIwk().getPublicKeyAtIndexURL64(SWTracker.getCurrIndex()),
                            inputToAddressText.getText().toString(),
                            TkmTextUtils.validateBI(inputTextNumberTkg.getText().toString()),
                            TkmTextUtils.validateBI(inputTextNumberTkr.getText().toString()),
                            inputTextMessage.getText().toString(),
                            new Date((new Date()).getTime() + 60000L* 5) ,
                            0, 0
                    );
                } catch (WalletException e) {
                    e.printStackTrace();
                }
                TransactionBean genericTRA = null;

                System.out.println("Internal transaction bean ultimo : " + itb);

                try {
                    genericTRA = TkmWallet.createGenericTransaction(
                            itb,
                            SWTracker.i().getIwk(),
                            SWTracker.getCurrIndex());
                } catch (TransactionCanNotBeCreatedException e) {
                    e.printStackTrace();
                }

                String txJson = TkmTextUtils.toJson(genericTRA);

                TransactionBox tbox = TkmWallet.verifyTransactionIntegrity(txJson);
                setTbox(tbox);

                FeeBean feeBean = TransactionFeeCalculator.getFeeBean(tbox);
                if (feeBean == null) {
                    textEsito.setError("Unexcepted validation error");

                } else {
                    String cpu = feeBean.getCpu().toString();
                    String disk = feeBean.getDisk().toString();
                    String mem = feeBean.getMemory().toString();
                    textEsito.setText("CPU: " + cpu + " MEM: " + mem + " DISK: " + TkmTextUtils.formatNumber(disk));
                    inputButtonSendToken.setEnabled(true);
                }
            }
        });

        inputButtonSendToken.setOnClickListener(e -> {
            DoSubmit ds = new DoSubmit();
            String hexBody = TkmSignUtils.fromStringToHexString(getTbox().getTransactionJson());

            System.out.println("TRX JSON: " + getTbox().getTransactionJson());

            System.out.println("CURRENT TRANSACTION ENDPOINT: " + SWTracker.i().getTransactionEndpoint());
            ds.execute(String.valueOf(SWTracker.i().getTransactionEndpoint()), hexBody);
        });

    }

    protected class DoSubmit extends AsyncTask<String, String, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
          /*  if (getTxResult()) {
                textFinalSubmitEsito.setText("Transaction has been successfully submitted");
            } else {
                textFinalSubmitEsito.setTextColor(Color.RED);
                textFinalSubmitEsito.setText("Transaction Error");
            }
        */
            SWTracker.i().setTransactionResult(getTxResult());

            Intent activityTransactionInfo = new Intent(getApplicationContext(), TransactionInfoActivity.class);
            startActivity(activityTransactionInfo);
        }

        @Override
        protected Void doInBackground(String... params) {
            String urlString = params[0]; // URL to call
            System.out.println("urlString: " + urlString);
            String data = params[1]; //data to post
            System.out.println(data);
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, "tx=" + data);
            Request request = new Request.Builder()
                    .url(urlString)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String responseResult = response.body().string();
                System.out.println("response di pay " + responseResult);
                setTxResult(false);
                if (responseResult.contains("true")) {
                    setTxResult(true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}