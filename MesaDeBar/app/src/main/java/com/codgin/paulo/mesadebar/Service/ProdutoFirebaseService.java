package com.codgin.paulo.mesadebar.Service;

import com.codgin.paulo.mesadebar.Control.CalculatorControl;
import com.codgin.paulo.mesadebar.Model.Pessoa;
import com.codgin.paulo.mesadebar.Model.Produto;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        FirebaseService mesaFirebase = new FirebaseService();


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



}


