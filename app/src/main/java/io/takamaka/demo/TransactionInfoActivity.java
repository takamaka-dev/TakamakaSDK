package io.takamaka.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import io.takamaka.demo.utils.SWTracker;

public class TransactionInfoActivity extends MainController {

    private TextView textViewResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_info);

        textViewResult = findViewById(R.id.textViewTransactionStatus);
        if(SWTracker.i().isTransactionResult() == true){

            textViewResult.setText("Transaction Successfully Transmitted");
            textViewResult.setTextColor(Color.GREEN);
        }else{

            textViewResult.setText("Transaction Failed");
            textViewResult.setTextColor(Color.RED);

        }
   }


}
