package com.codgin.paulo.mesadebar.Service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.UiThread;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.TextView;

import com.codgin.paulo.mesadebar.Control.CalculatorControl;
import com.codgin.paulo.mesadebar.Control.ControlRecuperaMesaFirebase;
import com.codgin.paulo.mesadebar.Control.DialogMesaControl;
import com.codgin.paulo.mesadebar.Helper.MesaReferenciaHelper;
import com.codgin.paulo.mesadebar.Helper.VerificacoesMesaHelper;
import com.codgin.paulo.mesadebar.HomeMesa;
import com.codgin.paulo.mesadebar.ListaMesa;
import com.codgin.paulo.mesadebar.MesaAdapter;
import com.codgin.paulo.mesadebar.Model.Mesa;
import com.codgin.paulo.mesadebar.Model.ModelGetMesa;
import com.codgin.paulo.mesadebar.Model.Pessoa;
import com.codgin.paulo.mesadebar.Model.User;
import com.codgin.paulo.mesadebar.PessoaAdapter;
import com.codgin.paulo.mesadebar.R;
import com.codgin.paulo.mesadebar.RecyclerItemClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paulo on 18/07/2017.
 */

public class FirebaseService {
    private DatabaseReference firebaseReferencia = FirebaseDatabase.getInstance().getReference();
    Mesa mesa = new Mesa();
    public CalculatorControl calculatorControl = new CalculatorControl();
    public PessoaFirebaseService pessoaFirebaseService = new PessoaFirebaseService();

    public  void criarMesaFirebase(String id, Mesa mesa){
        DatabaseReference usuarioReferencia = firebaseReferencia.child("users").child(id).child("mesas").child(mesa.getNome());
        usuarioReferencia.setValue(mesa);
    }
    public void criarUsuarioFirebase(User user){
        DatabaseReference usuarioReferencia = firebaseReferencia.child("users").child(user.getId());
        usuarioReferencia.setValue(user);
    }
    public void addPessoaMesaFirebase(String idUser, String nomeMesa, Pessoa pessoa){
        DatabaseReference usuarioReferencia = firebaseReferencia.child("users")
                                                .child(idUser)
                                                .child("mesas")
                                                .child(nomeMesa)
                                                .child("pessoas")
                                                .child(pessoa.getNome());
        usuarioReferencia.setValue(pessoa);
    }

    public List<String> getUserFirebase(){
        final List<String> idUsers = new ArrayList<>();
        DatabaseReference userReference = firebaseReferencia.child("users");

        userReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(idUsers.size()!=0){
                    idUsers.clear();
                }
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    idUsers.add(postSnapshot.getValue(User.class).getId());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return idUsers;
    }


    public void getMesaFirebase(final ModelGetMesa modelGetMesa){
        DialogMesaControl.loadingMesa(modelGetMesa.getContext());

        final DatabaseReference mesaReferencia = firebaseReferencia.child("users")
                .child(modelGetMesa.getIdUser())
                .child("mesas");

        MesaReferenciaHelper.helperGetMesaFirebase(mesaReferencia,modelGetMesa);

    }


    public void verificaMesaPossuiTip(ModelGetMesa modelGetMesaVerificaTip){
        DatabaseReference mesaReferencia = firebaseReferencia.child("users")
                .child(modelGetMesaVerificaTip.getIdUser())
                .child("mesas")
                .child(modelGetMesaVerificaTip.getNomeMesa())
                .child("hasTip");

        VerificacoesMesaHelper.verificaTipMesa(mesaReferencia,modelGetMesaVerificaTip.getSwitchTip());
    }


    public void addGorjetaMesaFirebase(final ModelGetMesa modelGetMesaGorjeta){

        final DatabaseReference totalReferencia = firebaseReferencia.child("users/"+
                modelGetMesaGorjeta.getIdUser()+
                "/mesas"+
                modelGetMesaGorjeta.getNomeMesa()+
                "/total");

        final DatabaseReference mesaReferencia = firebaseReferencia.child("users/"+
                modelGetMesaGorjeta.getIdUser()+
                "/mesas"+
                modelGetMesaGorjeta.getNomeMesa());

        totalReferencia.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String stringTotal = String.valueOf(dataSnapshot.getValue());
                double total = Double.parseDouble(stringTotal);
                MesaReferenciaHelper.helperAddGorjetaMesa(modelGetMesaGorjeta,mesaReferencia,total);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void getTotalMesa(boolean gorjeta, final String idUser, final String nomeMesa, final TextView txtTotal){
        if(gorjeta==false){
            DatabaseReference mesaReferencia = firebaseReferencia.child("users")
                    .child(idUser)
                    .child("mesas")
                    .child(nomeMesa)
                    .child("total");
            mesaReferencia.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String stringTotal = String.valueOf(dataSnapshot.getValue());
                    double total = Double.parseDouble(stringTotal);
                    txtTotal.setText(txtTotal.getContext().getResources().getString(R.string.total_mesa)+String.format("%.2f",total));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else if(gorjeta==true){
            DatabaseReference mesaReferencia = firebaseReferencia.child("users")
                    .child(idUser)
                    .child("mesas")
                    .child(nomeMesa)
                    .child("totalComGorjeta");
            mesaReferencia.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String stringTotal = String.valueOf(dataSnapshot.getValue());
                    double total = Double.parseDouble(stringTotal);
                    txtTotal.setText(txtTotal.getContext().getResources().getString(R.string.total_mesa)+String.format("%.2f",total));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }
    public void setTotalComGorjetaMesaFirebase(){

    }

    public void setTotalMesaFirebase(final String idUser, final String nomeMesa){
        final List<Pessoa> listaPessoa = new ArrayList<>();

        DatabaseReference pessoaReferencia = firebaseReferencia.child("users")
                .child(idUser)
                .child("mesas")
                .child(nomeMesa)
                .child("pessoas");
        final DatabaseReference hasTipReferencia = firebaseReferencia.child("users")
                .child(idUser)
                .child("mesas")
                .child(nomeMesa);
        final DatabaseReference totalComGorjeta = firebaseReferencia.child("users")
                .child(idUser)
                .child("mesas")
                .child(nomeMesa)
                .child("totalComGorjeta");
        final DatabaseReference mesaReferencia = firebaseReferencia.child("users")
                .child(idUser)
                .child("mesas")
                .child(nomeMesa)
                .child("total");
        final DatabaseReference valorGorjeta = firebaseReferencia.child("users")
                .child(idUser)
                .child("mesas")
                .child(nomeMesa)
                .child("valorGorjeta");
        pessoaReferencia.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(listaPessoa.size()!=0){
                    listaPessoa.clear();
                }
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    listaPessoa.add(postSnapshot.getValue(Pessoa.class));
                }
                double totalMesa = 0;
                for(Pessoa pessoa: listaPessoa){
                    totalMesa+=pessoa.getTotal();
                }

                verificaCalculaGorjeta(hasTipReferencia,mesaReferencia,
                        totalComGorjeta,valorGorjeta,totalMesa,
                        listaPessoa,idUser,nomeMesa);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void verificaCalculaGorjeta(DatabaseReference hasTip,
                                       final DatabaseReference mesaReferencia,
                                       final DatabaseReference totalComGorjetaReferencia,
                                       final DatabaseReference valorGorjetaReferencia,
                                       final double totalMesa, final List<Pessoa>listaPessoa,
                                       final String idUser,
                                       final String nomeMesa){

        hasTip.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               boolean hastip =(Boolean) dataSnapshot.getValue();

                if(hastip){
                    valorGorjetaReferencia.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int gorjeta = (Integer)dataSnapshot.getValue();
                            double totalComGorjeta = calculatorControl.addGorjeta(totalMesa, gorjeta);
                            totalComGorjetaReferencia.setValue(totalComGorjeta);

                            double valorGorjetaDinheiro = totalComGorjeta - totalMesa;
                            double valorGorjetaparaAdd = calculatorControl.dividirGorjetaPessoas(valorGorjetaDinheiro,
                                                                                                    listaPessoa.size());

                            pessoaFirebaseService.setTotalPessoaComGorjeta(idUser, nomeMesa,listaPessoa,valorGorjetaparaAdd);


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }else{
                    mesaReferencia.setValue(totalMesa);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
