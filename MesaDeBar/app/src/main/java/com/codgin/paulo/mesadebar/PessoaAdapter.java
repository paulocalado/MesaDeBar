package com.codgin.paulo.mesadebar;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codgin.paulo.mesadebar.Model.Pessoa;

import java.util.List;

/**
 * Created by paulocalado on 08/09/17.
 */

public class PessoaAdapter extends RecyclerView.Adapter<PessoaAdapter.PessoaViewHolder>  {
    List<Pessoa> listaPessoa;

    public PessoaAdapter(List<Pessoa> listaPessoa){
        this.listaPessoa = listaPessoa;
    }

    @Override
    public PessoaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itens_lista, parent, false);
        PessoaViewHolder pvh = new PessoaViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PessoaViewHolder holder, int position) {
        holder.txtNomePessoa.setText(listaPessoa.get(position).getNome());
        holder.txtParcialPessoa.setText("Parcial R$: "+String.format("%.2f", listaPessoa.get(position).getTotal()));
    }

    @Override
    public int getItemCount() {
        return listaPessoa.size();
    }

    public static class PessoaViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView txtNomePessoa;
        TextView txtParcialPessoa;

        PessoaViewHolder(View itemView){
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cardListaPessoa);
            txtNomePessoa = (TextView)itemView.findViewById(R.id.txtNomeMesaLista);
            txtParcialPessoa = (TextView)itemView.findViewById(R.id.txtQuantidadePessoasMesaLista);

        }


    }
}
