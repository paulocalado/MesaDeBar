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
        Produto produto = new Produto(nomeProduto, valorProduto, qtd);
        CalculatorControl calculatorControl = new CalculatorControl();

        double totalPorPessoa = calculatorControl.dividePorPessoa(valorProduto, qtd, listaPessoas.size());

        for(Pessoa pessoa: listaPessoas){
            DatabaseReference pessoaReferencia = firebaseReferencia.child("users")
                                                                    .child(idUser)
                                                                    .child("mesas")
                                                                    .child(nomeMesa)
                                                                    .child("pessoas")
                                                                    .child(pessoa.getNome())
                                                                    .child("produtosPessoa")
                                                                    .child(produto.getNome());
            produto.setValor(totalPorPessoa);
            pessoaReferencia.setValue(produto);
        }

        produtoReferencia.setValue(produto);

    }

}


