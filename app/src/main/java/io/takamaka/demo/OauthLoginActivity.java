package io.takamaka.demo;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.ClientAuthentication;
import net.openid.appauth.ClientSecretBasic;
import net.openid.appauth.ResponseTypeValues;
import net.openid.appauth.TokenRequest;
import net.openid.appauth.TokenResponse;

public class OauthLoginActivity extends MainController {

    private static final int REQUEST_CODE_AUTH = 0;

    private ClientAuthentication mClientAuthentication;

    protected Context context;

    Button oauthLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauth_login);
        initMenu();
        setCurrentActivity(this);
        initFormLoginOauth();
    }

    private void initFormLoginOauth() {
        AuthorizationServiceConfiguration mServiceConfiguration =
                new AuthorizationServiceConfiguration(
                        Uri.parse("https://testsite.takamaka.org/oauth/authorize"), // Authorization endpoint
                        Uri.parse("https://testsite.takamaka.org/oauth/token")); // Token endpoint

        mClientAuthentication =
                new ClientSecretBasic("dev"); // Client secret
        authorize(mServiceConfiguration);
    }

    private void authorize(AuthorizationServiceConfiguration mServiceConfiguration) {

        AuthorizationRequest.Builder authRequestBuilder =
                new AuthorizationRequest.Builder(
                        mServiceConfiguration, // the authorization service configuration
                        "dev", // the client ID, typically pre-registered and static
                        ResponseTypeValues.CODE, // the response_type value: we want a code
                        Uri.parse("https://testsite.takamaka.org:20443/oauth/authorized")); // the redirect URI to which the auth response is sent




        AuthorizationRequest authRequest = authRequestBuilder
                .setScope("email address")
                .build();




        AuthorizationService service = new AuthorizationService(this);

        Intent intent = service.getAuthorizationRequestIntent(authRequest);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        AuthorizationResponse authResponse = AuthorizationResponse.fromIntent(intent);

        System.out.println("AUTH CODE IS: " + authResponse.authorizationCode.toString());

        if (requestCode != REQUEST_CODE_AUTH) {
            return;
        }

        System.out.println("AUTH CODE IS: " + authResponse.authorizationCode.toString());

        AuthorizationException authException = AuthorizationException.fromIntent(intent);

        AuthState mAuthState = new AuthState(authResponse, authException);

        // Handle authorization response error here

        TokenRequest tokenRequest = authResponse.createTokenExchangeRequest();

        AuthorizationService service = new AuthorizationService(this);

        service.performTokenRequest(tokenRequest, mClientAuthentication,
                new AuthorizationService.TokenResponseCallback() {
                    @Override
                    public void onTokenRequestCompleted(TokenResponse tokenResponse,
                                                        AuthorizationException tokenException) {
                        System.out.println(tokenResponse);
                    }
                });
    }

}