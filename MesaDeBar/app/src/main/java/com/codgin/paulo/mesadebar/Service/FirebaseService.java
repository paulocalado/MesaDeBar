package com.codgin.paulo.mesadebar.Service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.UiThread;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.TextView;

import com.codgin.paulo.mesadebar.Control.CalculatorControl;
import com.codgin.paulo.mesadebar.HomeMesa;
import com.codgin.paulo.mesadebar.ListaMesa;
import com.codgin.paulo.mesadebar.MesaAdapter;
import com.codgin.paulo.mesadebar.Model.Mesa;
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


    public List<Mesa> getMesaFirebase(final String idUser, final RecyclerView rvListaMesa,  final Context context){
        final List<Mesa> listaMesas = new ArrayList<>();
        
        final ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Work ...");
        mProgressDialog.show();

        final DatabaseReference mesaReferencia = firebaseReferencia.child("users").child(idUser).child("mesas");

        mesaReferencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                    if(listaMesas.size()!=0){
                        listaMesas.clear();
                    }

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        listaMesas.add(postSnapshot.getValue(Mesa.class));

                    }
                    mProgressDialog.dismiss();
                final MesaAdapter adapter = new MesaAdapter(listaMesas);
                LinearLayoutManager llm = new LinearLayoutManager(context);
                rvListaMesa.setLayoutManager(llm);
                rvListaMesa.setAdapter(adapter);
                rvListaMesa.addOnItemTouchListener(
                        new RecyclerItemClickListener(context, rvListaMesa ,new RecyclerItemClickListener.OnItemClickListener() {
                            @Override public void onItemClick(View view, int position) {
                                // do whatever
                                Intent intent = new Intent(context, HomeMesa.class);
                                intent.putExtra("nomeMesa", listaMesas.get(position).getNome().toString());
                                intent.putExtra("idUser", idUser);
                                intent.setClass(context, HomeMesa.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }

                            @Override public void onLongItemClick(View view, int position) {
                                // do whatever
                            }
                        })
                );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return listaMesas;
    }
    public void verificaMesaPossuiTip(final String idUser, final String nomeMesa, final Switch switchTip){
        DatabaseReference mesaReferencia = firebaseReferencia.child("users")
                .child(idUser)
                .child("mesas")
                .child(nomeMesa)
                .child("hasTip");

        mesaReferencia.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean hasTip = (Boolean)dataSnapshot.getValue();
                if(hasTip){
                    switchTip.setChecked(true);
                }else{
                    switchTip.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void addGorjetaMesaFirebase(final String idUser, final String nomeMesa,
                                       final TextView txtTotal, final int gorjeta){


        final DatabaseReference mesaReferencia = firebaseReferencia.child("users")
                .child(idUser)
                .child("mesas")
                .child(nomeMesa)
                .child("total");
        final DatabaseReference gorjetaReferencia = firebaseReferencia.child("users")
                .child(idUser)
                .child("mesas")
                .child(nomeMesa)
                .child("totalComGorjeta");
        final DatabaseReference hasTipReferencia = firebaseReferencia.child("users")
                .child(idUser)
                .child("mesas")
                .child(nomeMesa)
                .child("hasTip");
        final DatabaseReference valorGorjetaReferencia = firebaseReferencia.child("users")
                .child(idUser)
                .child("mesas")
                .child(nomeMesa)
                .child("valorGorjeta");
        mesaReferencia.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String stringTotal = String.valueOf(dataSnapshot.getValue());
                double total = Double.parseDouble(stringTotal);
                double valorGorjeta = calculatorControl.addGorjeta(total,gorjeta);
                gorjetaReferencia.setValue(total+valorGorjeta);
                valorGorjetaReferencia.setValue(valorGorjeta);
                hasTipReferencia.setValue(true);
                txtTotal.setText(txtTotal.getContext().getResources().getString(R.string.total_mesa)+String.format("%.2f",total+valorGorjeta));
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
        final DatabaseReference mesaReferencia = firebaseReferencia.child("users")
                .child(idUser)
                .child("mesas")
                .child(nomeMesa)
                .child("total");
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
                mesaReferencia.setValue(totalMesa);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
