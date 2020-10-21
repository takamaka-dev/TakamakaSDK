package io.takamaka.demo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainController extends AppCompatActivity {

    // Make sure to use the FloatingActionButton
    // for all the FABs
    FloatingActionButton takamakaButton, loginButton, tokensButton, createWalletFab, restoreWalletFab;

    // These are taken to make visible and invisible along
    // with FABs
    TextView takamakaButtonText, loginButtonText, createWalletText, restoreWalletText;

    // to check whether sub FAB buttons are visible or not.
    Boolean isAllFabsVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Register all the FABs with their IDs
        // This FAB button is the Parent
        takamakaButton = findViewById(R.id.takamaka_button);
        // FAB button
        loginButton = findViewById(R.id.login_button);
        tokensButton = findViewById(R.id.tokens_button);
        createWalletFab = findViewById(R.id.create_wallet);
        restoreWalletFab = findViewById(R.id.restore_wallet);
        // Also register the action name text, of all the FABs.
        takamakaButtonText = findViewById(R.id.login_button_text);
        loginButtonText = findViewById(R.id.tokens_button_text);
        createWalletText = findViewById(R.id.create_wallet_text);
        restoreWalletText = findViewById(R.id.restore_wallet_text);


        // Now set all the FABs and all the action name
        // texts as GONE
        loginButton.setVisibility(View.GONE);
        tokensButton.setVisibility(View.GONE);
        createWalletFab.setVisibility(View.GONE);
        restoreWalletFab.setVisibility(View.GONE);
        takamakaButtonText.setVisibility(View.GONE);
        loginButtonText.setVisibility(View.GONE);
        createWalletText.setVisibility(View.GONE);
        restoreWalletText.setVisibility(View.GONE);


        // make the boolean variable as false, as all the
        // action name texts and all the sub FABs are invisible
        isAllFabsVisible = false;

        // We will make all the FABs and action name texts
        // visible only when Parent FAB button is clicked So
        // we have to handle the Parent FAB button first, by
        // using setOnClickListener you can see below
        takamakaButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isAllFabsVisible) {

                            // when isAllFabsVisible becomes
                            // true make all the action name
                            // texts and FABs VISIBLE.
                            loginButton.show();
                            tokensButton.show();
                            createWalletFab.show();
                            restoreWalletFab.show();
                            takamakaButtonText.setVisibility(View.VISIBLE);
                            loginButtonText.setVisibility(View.VISIBLE);
                            createWalletText.setVisibility(View.VISIBLE);
                            restoreWalletText.setVisibility(View.VISIBLE);

                            // make the boolean variable true as
                            // we have set the sub FABs
                            // visibility to GONE
                            isAllFabsVisible = true;
                        } else {

                            // when isAllFabsVisible becomes
                            // true make all the action name
                            // texts and FABs GONE.
                            loginButton.hide();
                            tokensButton.hide();
                            createWalletFab.hide();
                            restoreWalletFab.hide();
                            takamakaButtonText.setVisibility(View.GONE);
                            loginButtonText.setVisibility(View.GONE);
                            createWalletText.setVisibility(View.GONE);
                            restoreWalletText.setVisibility(View.GONE);

                            // make the boolean variable false
                            // as we have set the sub FABs
                            // visibility to GONE
                            isAllFabsVisible = false;
                        }
                    }
                });

        // below is the sample action to handle add person
        // FAB. Here it shows simple Toast msg. The Toast
        // will be shown only when they are visible and only
        // when user clicks on them
        tokensButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainController.this, "Send tokens section", Toast.LENGTH_SHORT).show();
                    }
                });

        // below is the sample action to handle add alarm
        // FAB. Here it shows simple Toast msg The Toast
        // will be shown only when they are visible and only
        // when user clicks on them
        loginButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainController.this, "Login section", Toast.LENGTH_SHORT).show();
                    }
                });

        createWalletFab.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainController.this, "Create Wallet section", Toast.LENGTH_SHORT).show();
                    }
                });

        restoreWalletFab.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainController.this, "Restore Wallet section", Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }
}