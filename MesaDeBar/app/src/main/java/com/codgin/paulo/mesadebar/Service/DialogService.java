package com.codgin.paulo.mesadebar.Service;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Service;
import com.codgin.paulo.mesadebar.HomeMesa;
import com.codgin.paulo.mesadebar.Model.Mesa;
import com.codgin.paulo.mesadebar.Model.Pessoa;
import com.codgin.paulo.mesadebar.R;


/**
 * Created by Paulo on 17/07/2017.
 */

public class DialogService {

    public FirebaseService firebaseService = new FirebaseService();

    public  void dialogAddMesa(final Context context, final String idUser){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(R.string.title_criar_mesa_dialog);
        builder1.setCancelable(false);
        final EditText input = new EditText(context);
        input.setHint(R.string.hint_criar_mesa_dialog);
        builder1.setView(input);
        builder1.setPositiveButton(
                R.string.positive_button,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String nomeMesa = input.getText().toString();
                        Toast.makeText(context, nomeMesa, Toast.LENGTH_LONG).show();
                        Mesa mesa = new Mesa(nomeMesa, 0,0);
                        firebaseService.criarMesaFirebase(idUser, mesa);
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                R.string.negative_button,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void dialogAddPessoa(final Context context, final String idUser, final String nomeMesa){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(R.string.title_criar_mesa_dialog);
        builder1.setCancelable(false);
        final EditText input = new EditText(context);
        input.setHint(R.string.hint_criar_mesa_dialog);
        builder1.setView(input);
        builder1.setPositiveButton(
                R.string.positive_button,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String nomePessoa = input.getText().toString();
                        Pessoa pessoa = new Pessoa(nomePessoa,0);
                        firebaseService.addPessoaMesaFirebase(idUser, nomeMesa, pessoa);
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                R.string.negative_button,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
