package io.takamaka.demo;

import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends MainController {

    /* An instance of this class will be registered as a JavaScript interface */
    class MyJavaScriptInterface
    {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html)
        {
            System.out.println("PORNOOOO: " + html);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        setCurrentActivity(this);
        initWebView();
    }

    private void initWebView() {
        String webviewUrl = getIntent().getExtras().getString("url");
        WebView webviewObj = findViewById(R.id.webview);

        webviewObj.getSettings().setJavaScriptEnabled(true);

        /* Register a new JavaScript interface called HTMLOUT */
        webviewObj.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");

        /* WebViewClient must be set BEFORE calling loadUrl! */
        webviewObj.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                System.out.println("URL CHIAMATA: " + url);
                /* This call inject JavaScript into the page which just finished loading. */
                webviewObj.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            }
        });
        webviewObj.loadUrl(webviewUrl);

    }




}