package com.yftech.h5demo.activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yftech.h5demo.R;
import com.yftech.h5demo.fragments.Blank1Fragment;
import com.yftech.h5demo.fragments.Blank2Fragment;
import com.yftech.h5demo.fragments.Blank3Fragment;
import com.yftech.h5demo.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity {

    private WebView mWeb = null;
    private LinearLayout ll_root;
    private EditText et_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWebView();
    }

    //初始化WebView
    private void initWebView() {
        ll_root = (LinearLayout) findViewById(R.id.ll_root);
        et_user = (EditText) findViewById(R.id.et_user);

        //动态创建一个WebView对象并添加到LinearLayout中
        mWeb = new WebView(getApplication());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWeb.setLayoutParams(params);
        ll_root.addView(mWeb);
        //不跳转到其他浏览器
        mWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings settings = mWeb.getSettings();
        //支持JS
        settings.setJavaScriptEnabled(true);
        //加载本地html文件
        mWeb.loadUrl("file:///android_asset/map_test4.html");
        mWeb.addJavascriptInterface(new JSInterface(),"Android");
    }

    //按钮的点击事件
    public void click(View view){
        //java调用JS方法
        mWeb.loadUrl("javascript:javaCallJs(" + "'" + et_user.getText().toString()+"'"+")");
    }

    public void clickAsync(View  view){
        Intent intent = new Intent(this,AsyncActivity.class);
        startActivity(intent);
    }
    public  void clickMap(View view){
        Intent intent = new Intent(this, com.yftech.h5demo.map.MainActivity.class);
        startActivity(intent);
    }

    public void clickJ6Test(View view){
        Intent intent = new Intent(this,J6Test1Activity.class);
        startActivity(intent);
    }

    public  void clickVideo(View view){
        Intent intent = new Intent(this,VideoActivity.class);
        startActivity(intent);
    }

    //在页面销毁的时候将webView移除
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ll_root.removeView(mWeb);
        mWeb.stopLoading();
        mWeb.removeAllViews();
        mWeb.destroy();
        mWeb = null;
    }

    private class JSInterface {
        //JS需要调用的方法
        @JavascriptInterface
        public void showToast(String arg){
            Toast.makeText(MainActivity.this,arg,Toast.LENGTH_SHORT).show();
        }
    }
}
