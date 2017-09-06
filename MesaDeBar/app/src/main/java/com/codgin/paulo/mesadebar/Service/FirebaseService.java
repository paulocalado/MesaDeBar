package com.codgin.paulo.mesadebar.Service;

import com.codgin.paulo.mesadebar.Model.Mesa;
import com.codgin.paulo.mesadebar.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paulo on 18/07/2017.
 */

public class FirebaseService {
    private DatabaseReference firebaseReferencia = FirebaseDatabase.getInstance().getReference();
    Mesa mesa = new Mesa();

    public  void criarMesaFirebase(String id, Mesa mesa){
        DatabaseReference usuarioReferencia = firebaseReferencia.child("users").child(id).child("mesas").child(mesa.getNome());
        usuarioReferencia.setValue(mesa);
    }
    public void criarUsuarioFirebase(User user){
        DatabaseReference usuarioReferencia = firebaseReferencia.child("users").child(user.getId());
        usuarioReferencia.setValue(user);
    }

    public List<Mesa> getMesaFirebase(String idUser){
        final List<Mesa> listaMesas = new ArrayList<>();

        DatabaseReference mesaReferencia = firebaseReferencia.child("produtos");
         final Mesa mesaToAdd = new Mesa();
        mesaReferencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        listaMesas.add(postSnapshot.getValue(Mesa.class));
                    }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return listaMesas;
    }
}
