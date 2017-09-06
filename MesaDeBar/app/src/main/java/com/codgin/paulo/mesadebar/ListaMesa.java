package com.codgin.paulo.mesadebar;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codgin.paulo.mesadebar.Model.Mesa;
import com.codgin.paulo.mesadebar.Service.DialogService;
import com.codgin.paulo.mesadebar.Service.FirebaseService;
import com.facebook.Profile;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ListaMesa extends AppCompatActivity {

    TextView txtListaMesaActivity;
    Button btnCriarMesaActivity;
    FirebaseService firebaseService = new FirebaseService();
    List<Mesa> listaMesa = new ArrayList<>();
    String id;
    RecyclerView rvListaMesa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mesa);
        FirebaseService firebaseService = new FirebaseService();
        Intent intentMain = getIntent();
        final Profile profile = intentMain.getParcelableExtra("profile");
        id = profile.getId();
        List<Mesa> listaMesa = new ArrayList<>();
        if(listaMesa.size()!=0){
            listaMesa.clear();
            listaMesa = firebaseService.getMesaFirebase(profile.getId());
        }else {
            listaMesa = firebaseService.getMesaFirebase(profile.getId());
        }
        //listaMesa = firebaseService.getMesaFirebase(profile.getId());
        final MesaAdapter adapter = new MesaAdapter(listaMesa);
        final List<Mesa> listAux = listaMesa;

        btnCriarMesaActivity = (Button)findViewById(R.id.btnCriarMesaActivity);
        txtListaMesaActivity = (TextView)findViewById(R.id.txtTituloListaMesaActivity);
         rvListaMesa = (RecyclerView)findViewById(R.id.rvListaMesas);
        txtListaMesaActivity.setText(profile.getFirstName()+getResources().getString(R.string.titulo_lista_mesas));

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rvListaMesa.setLayoutManager(llm);
        rvListaMesa.setAdapter(adapter);
        rvListaMesa.addOnItemTouchListener(
                new RecyclerItemClickListener(this, rvListaMesa ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Intent intent = new Intent(ListaMesa.this, HomeMesa.class);
                        intent.putExtra("nomeMesa", listAux.get(position).getNome().toString());
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


        btnCriarMesaActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogService dialogService = new DialogService();
                dialogService.dialogAddMesa(ListaMesa.this, profile.getId());

            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        Toast.makeText(this, "on resume", Toast.LENGTH_SHORT).show();
        listaMesa.clear();
        listaMesa = firebaseService.getMesaFirebase(id);
        final MesaAdapter adapter = new MesaAdapter(listaMesa);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rvListaMesa.setLayoutManager(llm);
        rvListaMesa.setAdapter(adapter);
    }

}
