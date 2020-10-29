package io.takamaka.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import io.takamaka.demo.utils.SWTracker;

public class ExplorerActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer);
        WebView myWebView = (WebView) findViewById(R.id.webview);



        System.out.println("Explorer url: " + SWTracker.i().getSettings().get(SWTracker.i().getCurrentSetting()).get("explorer_url"));
        myWebView.loadUrl((String) SWTracker.i().getSettings().get(SWTracker.i().getCurrentSetting()).get("explorer_url"));


        myWebView.clearCache(true);
        myWebView.clearHistory();
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setAllowContentAccess(true);
        myWebView.getSettings().setAllowFileAccess(true);
        myWebView.getSettings().setBlockNetworkLoads(false);
        myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        myWebView.getSettings().setAllowFileAccessFromFileURLs(true);
        myWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);

    }
}