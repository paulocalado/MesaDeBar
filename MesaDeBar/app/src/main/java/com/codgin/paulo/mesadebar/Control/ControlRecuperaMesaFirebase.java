package com.codgin.paulo.mesadebar.Control;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.codgin.paulo.mesadebar.HomeMesa;
import com.codgin.paulo.mesadebar.MesaAdapter;
import com.codgin.paulo.mesadebar.Model.Mesa;
import com.codgin.paulo.mesadebar.Model.ModelGetMesa;
import com.codgin.paulo.mesadebar.Model.Pessoa;
import com.codgin.paulo.mesadebar.RecyclerItemClickListener;

import java.util.List;

/**
 * Created by paulocalado on 29/09/17.
 */

public class ControlRecuperaMesaFirebase {

    public static void setLayoutGetMesaFirebase(final List<Mesa> listaMesas,
                                                final ModelGetMesa modelGetMesa){


        final MesaAdapter adapter = new MesaAdapter(listaMesas);
        LinearLayoutManager llm = new LinearLayoutManager(modelGetMesa.getContext());
        modelGetMesa.getRvListaMesa().setLayoutManager(llm);
        modelGetMesa.getRvListaMesa().setAdapter(adapter);
        modelGetMesa.getRvListaMesa().addOnItemTouchListener(
                new RecyclerItemClickListener(modelGetMesa.getContext(),
                        modelGetMesa.getRvListaMesa() ,
                        new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Intent intent = new Intent(modelGetMesa.getContext(), HomeMesa.class);
                        intent.putExtra("nomeMesa", listaMesas.get(position).getNome().toString());
                        intent.putExtra("idUser", modelGetMesa.getIdUser());
                        intent.setClass(modelGetMesa.getContext(), HomeMesa.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        modelGetMesa.getContext().startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }
}
