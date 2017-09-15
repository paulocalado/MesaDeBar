package com.codgin.paulo.mesadebar.Service;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.codgin.paulo.mesadebar.Control.CalculatorControl;
import com.codgin.paulo.mesadebar.Model.Pessoa;
import com.codgin.paulo.mesadebar.Model.Produto;
import com.codgin.paulo.mesadebar.ProdutoAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paulo on 12/09/2017.
 */

public class ProdutoFirebaseService {
    private DatabaseReference firebaseReferencia = FirebaseDatabase.getInstance().getReference();

    public void addProduto(String nomeProduto, double valorProduto, String idUser, String nomeMesa, int qtd, List<Pessoa> listaPessoas){
        DatabaseReference produtoReferencia = firebaseReferencia.child("users")
                                                .child(idUser)
                                                .child("mesas")
                                                .child(nomeMesa)
                                                .child("produtosMesa")
                                                .child(nomeProduto);

        Produto produto = new Produto(nomeProduto, valorProduto, qtd);
        CalculatorControl calculatorControl = new CalculatorControl();

        double totalPorPessoa = calculatorControl.dividePorPessoa(valorProduto, qtd, listaPessoas.size());
        double totalPessoa = 0;
        double totalMesa = 0;

        for(Pessoa pessoa: listaPessoas){
            DatabaseReference pessoaReferencia = firebaseReferencia.child("users")
                                                                    .child(idUser)
                                                                    .child("mesas")
                                                                    .child(nomeMesa)
                                                                    .child("pessoas")
                                                                    .child(pessoa.getNome())
                                                                    .child("produtosPessoa")
                                                                    .child(produto.getNome());
            DatabaseReference pessoaTotalReferencia = firebaseReferencia.child("users")
                                                                        .child(idUser)
                                                                        .child("mesas")
                                                                        .child(nomeMesa)
                                                                        .child("pessoas")
                                                                        .child(pessoa.getNome())
                                                                        .child("total");


            totalPessoa = pessoa.getTotal() + totalPorPessoa;
            pessoa.setTotal(totalPessoa);
            totalMesa += pessoa.getTotal();

            produto.setValor(totalPorPessoa);
            pessoaReferencia.setValue(produto);
            pessoaTotalReferencia.setValue(totalPessoa);

        }


        produto.setValor(valorProduto);
        produtoReferencia.setValue(produto);
        produtoReferencia = firebaseReferencia.child("users")
                .child(idUser)
                .child("mesas")
                .child(nomeMesa)
                .child("total");

        produtoReferencia.setValue(totalMesa);

    }

    public List<Produto> getProductMesaFirebase(final String idUser, final String nomeMesa, final RecyclerView rvListaPessoa, final Context context){
        final List<Produto> listaProduto = new ArrayList<>();
        DatabaseReference produtoReferencia = firebaseReferencia.child("users")
                                                                .child(idUser)
                                                                .child("mesas")
                                                                .child(nomeMesa)
                                                                .child("produtosMesa");

        produtoReferencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(listaProduto.size()!=0){
                    listaProduto.clear();
                }
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    listaProduto.add(postSnapshot.getValue(Produto.class));
                }
                final ProdutoAdapter adapter = new ProdutoAdapter(listaProduto);
                LinearLayoutManager llm = new LinearLayoutManager(context);
                rvListaPessoa.setLayoutManager(llm);
                rvListaPessoa.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return listaProduto;
    }

    public List<Produto> getProdutoPessoaFirebase(final String idUser,
                                                  final String nomeMesa,
                                                  final String nomePessoa)
                                                 {
        final List<Produto> listaProduto = new ArrayList<>();
        DatabaseReference produtoReferencia = firebaseReferencia.child("users")
                                                                .child(idUser)
                                                                .child("mesas")
                                                                .child(nomeMesa)
                                                                .child("pessoas")
                                                                .child(nomePessoa)
                                                                .child("produtosPessoa");

        produtoReferencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(listaProduto.size()!=0){
                    listaProduto.clear();
                }
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    listaProduto.add(postSnapshot.getValue(Produto.class));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return listaProduto;
    }
}


