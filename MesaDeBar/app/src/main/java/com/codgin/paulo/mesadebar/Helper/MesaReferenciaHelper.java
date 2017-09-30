package com.codgin.paulo.mesadebar.Helper;

import com.codgin.paulo.mesadebar.Control.CalculatorControl;
import com.codgin.paulo.mesadebar.Control.ControlRecuperaMesaFirebase;
import com.codgin.paulo.mesadebar.Model.Mesa;
import com.codgin.paulo.mesadebar.Model.ModelGetMesa;
import com.codgin.paulo.mesadebar.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by paulocalado on 29/09/17.
 */

public class MesaReferenciaHelper {

    public static void helperGetMesaFirebase(DatabaseReference mesaReferencia, final ModelGetMesa modelGetMesa){
        final List<Mesa> listaMesas = new ArrayList<>();

        mesaReferencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(listaMesas.size()!=0){
                    listaMesas.clear();
                }
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    listaMesas.add(postSnapshot.getValue(Mesa.class));
                }
                ControlRecuperaMesaFirebase.setLayoutGetMesaFirebase(listaMesas,modelGetMesa);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    public static void helperAddGorjetaMesa(ModelGetMesa modelGetMesa, DatabaseReference mesaReferencia,
                                            double totalDaMesa){

        CalculatorControl calculatorControl = new CalculatorControl();
        Map updateDataMesa = new HashMap();

        double valorGorjeta = calculatorControl.addGorjeta(totalDaMesa,modelGetMesa.getGorjeta());

        updateDataMesa.put("totalComGorjeta",totalDaMesa+valorGorjeta);
        updateDataMesa.put("hasTip", true);
        updateDataMesa.put("valorGorjeta", valorGorjeta);

        mesaReferencia.updateChildren(updateDataMesa);

        modelGetMesa.getTxtTotal().setText(modelGetMesa.getTxtTotal().getContext().getResources().getString(R.string.total_mesa)+String.format("%.2f",totalDaMesa+valorGorjeta));


    }

}
