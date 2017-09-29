package com.codgin.paulo.mesadebar.Control;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by paulocalado on 29/09/17.
 */

public class DialogMesaControl {

    public static void loadingMesa(Context context){
        final ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Work ...");
        mProgressDialog.show();
    }
}
