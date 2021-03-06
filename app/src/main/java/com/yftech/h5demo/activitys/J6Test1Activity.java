package com.yftech.h5demo.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yftech.h5demo.JavascriptInterfaceImpl;
import com.yftech.h5demo.R;

public class J6Test1Activity extends AppCompatActivity {

    private WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_j6_test1);

        webview = (WebView) findViewById(R.id.myWebView);
        // 得到设置属性的对象
        WebSettings webSettings = webview.getSettings();
        // 使能JavaScript
        webSettings.setJavaScriptEnabled(true);
        // 支持中文，否则页面中中文显示乱码
        webSettings.setDefaultTextEncodingName("UTF-8");

        // 传入一个Java对象和一个接口名,在JavaScript代码中用这个接口名代替这个对象,通过接口名调用Android接口的方法
        webview.addJavascriptInterface(new JavascriptInterfaceImpl(this, webview), "Android");

        // WebViewClient 主要帮助WebView处理各种通知、请求事件的
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        // WebChromeClient主要用来辅助WebView处理Javascript的对话框、网站图标、网站标题以及网页加载进度等
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
        // 载入页面：本地html资源文件
//        webview.loadUrl("file:///android_asset/Default.html");
//        webview.loadUrl("https://testdrive-archive.azurewebsites.net/Performance/Galactic/Default.html");
        webview.loadUrl("https://testdrive-archive.azurewebsites.net/Performance/FishBowl/");
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(J6Test1Activity.this,J6Test2Activity.class);
                startActivity(intent);
            }
        });
    }

    //在页面销毁的时候将webView移除
    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.stopLoading();
        webview.removeAllViews();
        webview.destroy();
        webview = null;
    }
}
