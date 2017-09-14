package com.codgin.paulo.mesadebar;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codgin.paulo.mesadebar.Model.Produto;

import java.util.List;

/**
 * Created by paulocalado on 14/09/17.
 */

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {
    List<Produto> listaProduto;

    public ProdutoAdapter(List<Produto> listaProduto){this.listaProduto = listaProduto;}

    @Override
    public ProdutoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ProdutoViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return listaProduto.size();
    }

    public static class ProdutoViewHolder extends RecyclerView.ViewHolder{
        CardView cardListaProduto;
        TextView txtNomeProduto;
        TextView txtPrecoProduto;
        TextView txtQuantidadeProduto;

         ProdutoViewHolder(View itemView){
             super(itemView);
             cardListaProduto = (CardView)itemView.findViewById(R.id.cardListaProduto);
             txtNomeProduto = (TextView)itemView.findViewById(R.id.txtNomeProduto);
             txtPrecoProduto = (TextView)itemView.findViewById(R.id.txtPrecoProduto);
             txtQuantidadeProduto = (TextView)itemView.findViewById(R.id.txtQuantidadeProduto);
         }
    }
}
