package com.egg8.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
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
    EditText input_address,detailed_address,shop_name;
    Button enroll_in;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        /** 오류남 ㅠㅠ 없어도 잘됨 ㅎㅎ
         //input_address 키보드 사출 방지 메소드 (onClick이 원클릭으로 실행됨)
         input_address.setInputType(0);
         InputMethodManager mgr=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
         mgr.showSoftInput(input_address,InputMethodManager.SHOW_IMPLICIT);
         //input_address 키보드 사출 방지 메소드 end
         **/

        input_address=findViewById(R.id.input_address);
        detailed_address=findViewById(R.id.input_detailed_address);
        shop_name=findViewById(R.id.input_shop_name);
        enroll_in=findViewById(R.id.btn_enroll_in);

        // WebView 초기화
        input_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                init_webView();
                handler = new Handler();

            }
        });
    }
    public void init_webView() {
        // WebView 설정
        webView = (WebView) findViewById(R.id.webView_address);

        // JavaScript 허용
        webView.getSettings().setJavaScriptEnabled(true);

        // JavaScript의 window.open 허용
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(true);

        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        webView.addJavascriptInterface(new AndroidBridge(), "TestApp");

        // web client 를 chrome 으로 설정
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

        // webview url load. php 파일 주소
        webView.loadUrl("http://222.100.239.140:8888/map/kakaoMap");
    }
    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    input_address.setText(String.format("(%s) %s %s", arg1, arg2, arg3));

                    // WebView를 초기화 하지않으면 재사용할 수 없음
                    //init_webView();

                }
            });
        }
    }
}