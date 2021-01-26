package com.egg8.ui.supp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.egg8.R;

public class EnrollActivity extends AppCompatActivity {

    private Handler handler;
    private WebView webView;
    private Activity mAc;
    private Context mCon;
    EditText input_address,detailed_address,shop_name;
    Button enroll_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);
        mAc = this;
        mCon = this;
        findId(mAc);
    }

    private void findId(Activity v) {
        input_address = v.findViewById(R.id.input_address);
        detailed_address = v.findViewById(R.id.input_detailed_address);
        shop_name = v.findViewById(R.id.input_shop_name);
        enroll_in = v.findViewById(R.id.btn_enroll_in);
        webView = v.findViewById(R.id.webView_address);
        listenerEvent();
    }

    private void listenerEvent(){
        input_address.setOnClickListener(clickListener);
    }

    private void webViewSettings(){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.addJavascriptInterface(new AndroidBridge(), "TestApp");
    }

    public void init_webView(String url) {
        webViewSettings();
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                WebView newWebView = new WebView(EnrollActivity.this);
                WebSettings webSettings = newWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);

                final Dialog dialog = new Dialog(EnrollActivity.this);
                dialog.setContentView(newWebView);
                dialog.getWindow().getAttributes( ).width= LinearLayout.LayoutParams.MATCH_PARENT;
                dialog.getWindow().getAttributes().height=LinearLayout.LayoutParams.MATCH_PARENT;
                dialog.show();

                newWebView.setWebChromeClient(new WebChromeClient() {
                    @Override
                    public void onCloseWindow(WebView window) {
                        dialog.dismiss();
                    }
                });
                ((WebView.WebViewTransport) resultMsg.obj).setWebView(newWebView);
                resultMsg.sendToTarget();
                return true;
            }
        });
        webView.loadUrl(url);
    }
    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    input_address.setText(String.format("(%s) %s %s", arg1, arg2, arg3));
                }
            });
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.input_address :
                    init_webView("http://222.100.239.140:8888/map/kakaoMap");
                    handler = new Handler();
                    break;
            }
        }
    };
}