package com.codgin.paulo.mesadebar.Helper;

import com.codgin.paulo.mesadebar.Control.ControlRecuperaMesaFirebase;
import com.codgin.paulo.mesadebar.Model.Mesa;
import com.codgin.paulo.mesadebar.Model.ModelGetMesa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
}
