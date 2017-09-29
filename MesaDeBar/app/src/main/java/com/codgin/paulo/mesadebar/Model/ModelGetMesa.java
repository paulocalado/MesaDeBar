package com.codgin.paulo.mesadebar.Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by paulocalado on 29/09/17.
 */

public class ModelGetMesa {
    String idUser;
    Context context;
    RecyclerView rvListaMesa;
    DatabaseReference mesaReferencia;

    public ModelGetMesa(String idUser, Context context, RecyclerView rvListaMesa, DatabaseReference mesaReferencia) {
        this.idUser = idUser;
        this.context = context;
        this.rvListaMesa = rvListaMesa;
        this.mesaReferencia = mesaReferencia;
    }

    public ModelGetMesa(String idUser, Context context, RecyclerView rvListaMesa) {
        this.idUser = idUser;
        this.context = context;
        this.rvListaMesa = rvListaMesa;
    }

    public DatabaseReference getMesaReferencia() {
        return mesaReferencia;
    }

    public void setMesaReferencia(DatabaseReference mesaReferencia) {
        this.mesaReferencia = mesaReferencia;
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
