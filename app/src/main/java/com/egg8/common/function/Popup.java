package com.egg8.common.function;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import com.egg8.R;

public class Popup {

    public static Context context;
    public static WebView wv;
    public static Button cancel;
    public static CheckBox checkbox;

    public static void callFunction(Context context, String url) {
        final Dialog dlg = new Dialog(context);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.activity_popup);
        dlg.show();

        wv = (WebView) dlg.findViewById(R.id.webView);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        wv.loadUrl(url);
        wv.setWebChromeClient(new WebChromeClient());
        wv.setWebViewClient(new WebViewClientClass());

        cancel = (Button) dlg.findViewById(R.id.pop_close);
        checkbox = (CheckBox) dlg.findViewById(R.id.checkbox);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });
    }
    public static class WebViewClientClass extends WebViewClient {//페이지 이동
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("check URL",url);
            view.loadUrl(url);
            return true;
        }
    }
}

