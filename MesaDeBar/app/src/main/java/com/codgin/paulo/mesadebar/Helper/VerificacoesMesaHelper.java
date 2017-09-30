package com.codgin.paulo.mesadebar.Helper;

import android.widget.Switch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by paulocalado on 29/09/17.
 */

public class VerificacoesMesaHelper {

    public static void verificaTipMesa(DatabaseReference tipReferencia, final Switch switchTip){

        tipReferencia.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean hasTip = (Boolean)dataSnapshot.getValue();
                if(hasTip){
                    switchTip.setChecked(true);
                }else{
                    switchTip.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
