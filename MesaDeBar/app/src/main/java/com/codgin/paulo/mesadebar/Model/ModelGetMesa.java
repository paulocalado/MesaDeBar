package com.codgin.paulo.mesadebar.Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by paulocalado on 29/09/17.
 */

public class ModelGetMesa {
    String idUser;
    String nomeMesa;
    Switch switchTip;
    Context context;
    RecyclerView rvListaMesa;
    TextView txtTotal;
    int gorjeta;

    public ModelGetMesa(final String idUser, final String nomeMesa,
                        final TextView txtTotal,  int gorjeta){
        this.idUser = idUser;
        this.nomeMesa = nomeMesa;
        this.txtTotal = txtTotal;
        this.gorjeta = gorjeta;
    }

    public ModelGetMesa(String idUser, String nomeMesa, Switch switchTip){
        this.idUser = idUser;
        this.nomeMesa = nomeMesa;
        this.switchTip = switchTip;
    }

    public ModelGetMesa(String idUser, Context context, RecyclerView rvListaMesa) {
        this.idUser = idUser;
        this.context = context;
        this.rvListaMesa = rvListaMesa;
    }

    public TextView getTxtTotal() {
        return txtTotal;
    }

    public void setTxtTotal(TextView txtTotal) {
        this.txtTotal = txtTotal;
    }

    public int getGorjeta() {
        return gorjeta;
    }

    public void setGorjeta(int gorjeta) {
        this.gorjeta = gorjeta;
    }

    public String getNomeMesa() {
        return nomeMesa;
    }

    public void setNomeMesa(String nomeMesa) {
        this.nomeMesa = nomeMesa;
    }

    public Switch getSwitchTip() {
        return switchTip;
    }

    public void setSwitchTip(Switch switchTip) {
        this.switchTip = switchTip;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public RecyclerView getRvListaMesa() {
        return rvListaMesa;
    }

    public void setRvListaMesa(RecyclerView rvListaMesa) {
        this.rvListaMesa = rvListaMesa;
    }
}
