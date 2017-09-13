package com.codgin.paulo.mesadebar.Service;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.codgin.paulo.mesadebar.Model.Pessoa;
import com.codgin.paulo.mesadebar.PessoaAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulocalado on 11/09/17.
 */

public class PessoaFirebaseService {
    private  DatabaseReference firebaseReferencia = FirebaseDatabase.getInstance().getReference();

    public void addPessoaMesaFirebase(String idUser, String nomeMesa, Pessoa pessoa){
        DatabaseReference usuarioReferencia = firebaseReferencia.child("users")
                .child(idUser)
                .child("mesas")
                .child(nomeMesa)
                .child("pessoas")
                .child(pessoa.getNome());
        usuarioReferencia.setValue(pessoa);
    }

    public List<Pessoa> getPessoaFirebase(final String idUser, final String nomeMesa, final RecyclerView rvListaPessoa, final Context context){
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
                                                                .child("quantidadePessoas");

        pessoaReferencia.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(listaPessoa.size()!=0){
                    listaPessoa.clear();
                }
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    listaPessoa.add(postSnapshot.getValue(Pessoa.class));
                }
                final PessoaAdapter adapter = new PessoaAdapter(listaPessoa);
                LinearLayoutManager llm = new LinearLayoutManager(context);
                rvListaPessoa.setLayoutManager(llm);
                rvListaPessoa.setAdapter(adapter);
                mesaReferencia.setValue(listaPessoa.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return listaPessoa;
    }

    public  List<Pessoa> getListPessoaFirebase(final String idUser, final  String nomeMesa){
        final List<Pessoa> listaPessoa = new ArrayList<>();
        DatabaseReference pessoaReferencia = firebaseReferencia.child("users")
                .child(idUser)
                .child("mesas")
                .child(nomeMesa)
                .child("pessoas");

        pessoaReferencia.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(listaPessoa.size()!=0){
                    listaPessoa.clear();
                }
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    listaPessoa.add(postSnapshot.getValue(Pessoa.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return listaPessoa;
    }
}
