package com.codgin.paulo.mesadebar;

import android.app.Application;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_produto, parent, false);
        ProdutoViewHolder pvh = new ProdutoViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ProdutoViewHolder holder, int position) {

        holder.txtNomeProduto.setText(listaProduto.get(position).getNome());
        holder.txtPrecoProduto.setText(holder.txtNomeProduto.getContext().getResources().getString(R.string.txt_preco_produto)
                +String.format("%.2f", listaProduto.get(position).getValor()));
        holder.txtQuantidadeProduto.setText(holder.txtQuantidadeProduto.getContext().getResources().getString(R.string.txt_quantidade_produto)
                +String.valueOf(listaProduto.get(position).getQuantidade()));
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
