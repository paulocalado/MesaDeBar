package com.codgin.paulo.mesadebar.Service;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codgin.paulo.mesadebar.Control.CalculatorControl;
import com.codgin.paulo.mesadebar.Model.Pessoa;
import com.codgin.paulo.mesadebar.Model.Produto;
import com.codgin.paulo.mesadebar.ProdutoAdapter;
import com.codgin.paulo.mesadebar.RecyclerItemClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Paulo on 12/09/2017.
 */

public class ProdutoFirebaseService {
    private DatabaseReference firebaseReferencia = FirebaseDatabase.getInstance().getReference();
    public DialogService dialogService = new DialogService();
    CalculatorControl calculatorControl = new CalculatorControl();

    public void addProduto(String nomeProduto, double valorProduto,
                           String idUser, String nomeMesa, int qtd, List<Pessoa> listaPessoas,
                           List<Pessoa> listaPessoasComplemento){
        DatabaseReference produtoReferencia = firebaseReferencia.child("users")
                                                .child(idUser)
                                                .child("mesas")
                                                .child(nomeMesa)
                                                .child("produtosMesa")
                                                .child(nomeProduto);
        DatabaseReference hasTipReferencia = firebaseReferencia.child("users")
                                                                .child(idUser)
                                                                .child("mesas")
                                                                .child(nomeMesa)
                                                                .child("hasTip");

        Produto produto = new Produto(nomeProduto, valorProduto, qtd);


        double totalPorPessoa = calculatorControl.dividePorPessoa(valorProduto, qtd, listaPessoas.size());
        double totalPessoa = 0;

        for(Pessoa pessoa: listaPessoas){
            DatabaseReference pessoaReferencia = firebaseReferencia.child("users")
                                                                    .child(idUser)
                                                                    .child("mesas")
                                                                    .child(nomeMesa)
                                                                    .child("pessoas")
                                                                    .child(pessoa.getNome())
                                                                    .child("produtosPessoa");

            DatabaseReference pessoaTotalReferencia = firebaseReferencia.child("users")
                                                                        .child(idUser)
                                                                        .child("mesas")
                                                                        .child(nomeMesa)
                                                                        .child("pessoas")
                                                                        .child(pessoa.getNome())
                                                                        .child("total");
            calculosAddProduto(pessoaReferencia, pessoaTotalReferencia, pessoa, totalPorPessoa, produto, totalPessoa);

        }

        produto.setValor(valorProduto);
        produtoReferencia.setValue(produto);

        FirebaseService firebaseService = new FirebaseService();
        firebaseService.setTotalMesaFirebase(idUser,nomeMesa);

    }


    public void calculosAddProduto(DatabaseReference pessoaReferencia, DatabaseReference pessoaTotalReferencia,
                                   Pessoa pessoa, double totalPorPessoa, Produto produto, double totalPessoa ){


        totalPessoa = pessoa.getTotal() + totalPorPessoa;
        HashMap<String, Produto> mapProdutos = new HashMap<String, Produto>();

        if(pessoa.getProdutos()!= null){
            mapProdutos = pessoa.getProdutos();
        }

        pessoa.setTotal(totalPessoa);
        produto.setValor(totalPorPessoa);
        mapProdutos.put(produto.getNome(),produto);
        pessoa.setProdutos(mapProdutos);
        pessoaReferencia.setValue(mapProdutos);
        pessoaTotalReferencia.setValue(totalPessoa);

    }


    public void updateProdutoPessoa(final String idUser, final String nomeMesa,
                                    final Produto produto, List<Pessoa> listaPessoa){

        int qtdPessoasComProduto =0;
        for(Pessoa pessoa: listaPessoa){
            HashMap<String, Produto> hashMap = new HashMap<>();
            hashMap = pessoa.getProdutos();
            if(hashMap!=null){
                for(String key : hashMap.keySet()){
                    if(key.equals(produto.getNome())){
                        qtdPessoasComProduto += 1;
                    }
                }
            }
        }

        double totalPorPessoa = calculatorControl.dividePorPessoa(produto.getValor(), produto.getQuantidade(), qtdPessoasComProduto);


        for(Pessoa pessoa: listaPessoa){
            HashMap<String, Produto> map = new HashMap<>();
            map = pessoa.getProdutos();
            if(map != null){
                for(String key: map.keySet()){
                    double totalPessoa = 0;
                    if(key.equals(produto.getNome())){
                        DatabaseReference pessoaReferencia = firebaseReferencia.child("users")
                            .child(idUser)
                            .child("mesas")
                            .child(nomeMesa)
                            .child("pessoas")
                            .child(pessoa.getNome())
                            .child("produtosPessoa");

                        DatabaseReference pessoaTotalReferencia = firebaseReferencia.child("users")
                            .child(idUser)
                            .child("mesas")
                            .child(nomeMesa)
                            .child("pessoas")
                            .child(pessoa.getNome())
                            .child("total");



                        HashMap<String, Produto> mapProdutos = new HashMap<String, Produto>();

                        if(pessoa.getProdutos()!= null){
                             mapProdutos = pessoa.getProdutos();
                        }
                        Produto produtoAux = map.get(key);
                        totalPessoa = pessoa.getTotal() + (totalPorPessoa-produtoAux.getValor());
                        pessoa.setTotal(totalPessoa);
                        produto.setValor(totalPorPessoa);
                        mapProdutos.put(produto.getNome(),produto);
                        pessoa.setProdutos(mapProdutos);
                        pessoaReferencia.setValue(mapProdutos);
                        pessoaTotalReferencia.setValue(totalPessoa);
                }
              }
            }



        }

        FirebaseService firebaseService = new FirebaseService();
        firebaseService.setTotalMesaFirebase(idUser,nomeMesa);
    }

    public void updateProdutoMesa(final String idUser, final String nomeMesa, final String nomeProduto,
                                  final int qtdProduto){
        DatabaseReference produtoReferencia = firebaseReferencia.child("users")
                                                                .child(idUser)
                                                                .child("mesas")
                                                                .child(nomeMesa)
                                                                .child("produtosMesa")
                                                                .child(nomeProduto)
                                                                .child("quantidade");

        produtoReferencia.setValue(qtdProduto);

    }

    public List<Produto> getProductMesaFirebase(final String idUser,
                                                final String nomeMesa,
                                                final RecyclerView rvListaProduto,
                                                final Context context,
                                                final List<Pessoa> listaPessoas,
                                                final TextView textEmpty,
                                                final ImageView imageEmpty){
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
                if(listaProduto.size()==0){
                    textEmpty.setVisibility(View.VISIBLE);
                    imageEmpty.setVisibility(View.VISIBLE);
                    rvListaProduto.setVisibility(View.GONE);
                }else{
                    textEmpty.setVisibility(View.GONE);
                    imageEmpty.setVisibility(View.GONE);
                    rvListaProduto.setVisibility(View.VISIBLE);
                }
                final ProdutoAdapter adapter = new ProdutoAdapter(listaProduto);
                LinearLayoutManager llm = new LinearLayoutManager(context);
                rvListaProduto.setLayoutManager(llm);
                rvListaProduto.setAdapter(adapter);
                rvListaProduto.addOnItemTouchListener(
                        new RecyclerItemClickListener(context, rvListaProduto ,new RecyclerItemClickListener.OnItemClickListener(){
                            @Override public void onItemClick(View view, int position) {
                                // do whatever
                                dialogService.dialogDetalheProduto(nomeMesa, idUser, listaProduto.get(position).getNome(), context, listaPessoas,
                                                                    listaProduto.get(position));
                                Vibrator v = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);

                                v.vibrate(500);
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

    public void deletaProdutoMesaFirebase(final String idUser,
                                            final String nomeMesa,
                                            final String nomeProduto
                                            ){
        DatabaseReference produtoReferencia = firebaseReferencia.child("users")
                                                                .child(idUser)
                                                                .child("mesas")
                                                                .child(nomeMesa)
                                                                .child("produtosMesa")
                                                                .child(nomeProduto);

        produtoReferencia.removeValue();
    }
}


