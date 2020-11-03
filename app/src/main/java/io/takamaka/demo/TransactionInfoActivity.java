package io.takamaka.demo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import io.takamaka.demo.utils.SWTracker;

public class TransactionInfoActivity extends MainController {

    private TextView textViewResult;
    private Button buttonGoHome;
    private Button buttonNewTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_info);

        textViewResult = findViewById(R.id.textViewTransactionStatus);
        buttonGoHome = findViewById(R.id.button_go_home);
        buttonNewTransaction = findViewById(R.id.button_new_transaction);

        //action button go home  linked
        buttonGoHome.setOnClickListener(e -> {
            Intent activityHome = new Intent(getApplicationContext(), HomeWalletActivity.class);
            startActivity(activityHome);

        });
        //action button transaction  linked
        buttonNewTransaction.setOnClickListener( e -> {
            Intent activitySendToken = new Intent(getApplicationContext(), SendTokenActivity.class);
            startActivity(activitySendToken);
        });


        if(SWTracker.i().isTransactionResult() == true){

            textViewResult.setText("Transaction Successfully Transmitted");
            textViewResult.setTextColor(Color.GREEN);
        }else{

            textViewResult.setText("Transaction Failed");
            textViewResult.setTextColor(Color.RED);

        }
   }


}
