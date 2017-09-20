package com.codgin.paulo.mesadebar.Service;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.codgin.paulo.mesadebar.Model.Pessoa;
import com.codgin.paulo.mesadebar.Model.Produto;
import com.codgin.paulo.mesadebar.PessoaAdapter;
import com.codgin.paulo.mesadebar.RecyclerItemClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by paulocalado on 11/09/17.
 */

public class PessoaFirebaseService {
    private  DatabaseReference firebaseReferencia = FirebaseDatabase.getInstance().getReference();
    DialogService dialogService = new DialogService();
    ProdutoFirebaseService produtoService = new ProdutoFirebaseService();

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
                rvListaPessoa.addOnItemTouchListener(
                        new RecyclerItemClickListener(context, rvListaPessoa ,new RecyclerItemClickListener.OnItemClickListener() {
                            @Override public void onItemClick(View view, int position) {
                                // do whatever
                                List<Produto> listaProduto = new ArrayList<Produto>();
                                listaProduto = produtoService.getProdutoPessoaFirebase(idUser,nomeMesa, listaPessoa.get(position).getNome());
                                dialogService.dialogProdutoPessoa(nomeMesa,idUser, context, listaProduto);
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


        return listaPessoa;
    }

    public void setTotalPessoaFirebase(final String idUser, final String nomeMesa){
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
                    //TODO tentar arrumar essa porra aqui
                    for(Pessoa pessoa: listaPessoa){
                        double totalPorPessoa = 0;
                        if(pessoa.getProdutos()!= null){
                            for (HashMap.Entry<String, Produto> entry : pessoa.getProdutos().entrySet()) {

                            Produto produto = entry.getValue();
                            totalPorPessoa+= produto.getValor();
                            }
                        }


                        DatabaseReference totalReferencia = firebaseReferencia.child("users")
                                .child(idUser)
                                .child("mesas")
                                .child(nomeMesa)
                                .child("pessoas")
                                .child(pessoa.getNome())
                                .child("total");

                        totalReferencia.setValue(totalPorPessoa);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




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

    public double getTotalPorPessoa(final String idUser,
                                    final String nomeMesa){
        double totalPorPessoa = 0;
       final List<Pessoa> listaPessoa = new ArrayList<>();
        DatabaseReference produtoReferencia = firebaseReferencia.child("users")
                .child(idUser)
                .child("mesas")
                .child(nomeMesa)
                .child("pessoas");


        produtoReferencia.addValueEventListener(new ValueEventListener() {

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



        return totalPorPessoa;
    }

    public void deletaProdutoPessoaFirebase(final String idUser,
                                            final String nomeMesa,
                                            final String nomeProduto,
                                            final List<Pessoa> listaPessoa){

        for(final Pessoa pessoa: listaPessoa){

            DatabaseReference produtoReferencia = firebaseReferencia.child("users")
                    .child(idUser)
                    .child("mesas")
                    .child(nomeMesa)
                    .child("pessoas")
                    .child(pessoa.getNome())
                    .child("produtosPessoa")
                    .child(nomeProduto);

            produtoReferencia.removeValue();
        }

        setTotalPessoaFirebase(idUser, nomeMesa);
    }
}
