package com.codgin.paulo.mesadebar;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.codgin.paulo.mesadebar.Model.Mesa;

import java.util.List;

/**
 * Created by Paulo on 17/07/2017.
 */

public class MesaAdapter extends RecyclerView.Adapter<MesaAdapter.MesaViewHolder> {
    List<Mesa> listaMesas;
    static OnItemClickListener mItemClickListener;
    public MesaAdapter(List<Mesa> listaMesas){
        this.listaMesas = listaMesas;
    }

    @Override
    public MesaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itens_lista, parent, false);
        MesaViewHolder mvh = new MesaViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MesaViewHolder holder, int position) {
        holder.txtNomeMesa.setText(String.valueOf(listaMesas.get(position).getNome()));
        holder.txtQuantidadePessoaMesa.setText(String.valueOf(listaMesas.get(position).getQuantidadePessoas())+" Pessoas");
        holder.txtTotalMesa.setText(holder.txtTotalMesa.getContext().getResources().getString(R.string.total_mesa)+String.format("%.2f", listaMesas.get(position).getTotal()));
    }

    @Override
    public int getItemCount() {
        return listaMesas.size();
    }

    public static class MesaViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView txtNomeMesa;
        TextView txtQuantidadePessoaMesa;
        TextView txtTotalMesa;

        MesaViewHolder(View itemView){
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cardItenListaMesa);
            txtNomeMesa = (TextView)itemView.findViewById(R.id.txtNomeMesaLista);
            txtQuantidadePessoaMesa = (TextView)itemView.findViewById(R.id.txtQuantidadePessoasMesaLista);
            txtTotalMesa = (TextView)itemView.findViewById(R.id.txtTotalMesa);
           //qual itemView.setOnClickListener( itemView);
        }


    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position, String id);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


}
