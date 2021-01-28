package com.egg8.Builder;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class DialogBuilder {
    public static AlertDialog createDialog(Context context, int layout){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        setView(builder, context, layout);
        AlertDialog dialog = builder.create();
        return dialog;
    }

    public static View setView(AlertDialog.Builder builder, Context context, int layout){
        View v = LayoutInflater.from(context).inflate(layout, null, false);
        builder.setView(v);
        return v;
    }
}
