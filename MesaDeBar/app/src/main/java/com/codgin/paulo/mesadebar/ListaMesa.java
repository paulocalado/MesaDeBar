package com.codgin.paulo.mesadebar;

import android.app.ProgressDialog;
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
import com.codgin.paulo.mesadebar.Model.ModelGetMesa;
import com.codgin.paulo.mesadebar.Service.DialogService;
import com.codgin.paulo.mesadebar.Service.FirebaseService;
import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ListaMesa extends AppCompatActivity {

    TextView txtListaMesaActivity;
    Button btnCriarMesaActivity;
    FirebaseService firebaseService = new FirebaseService();
    List<Mesa> listaMesa = new ArrayList<>();
    ModelGetMesa modelGetMesa = null;
    String id;
    RecyclerView rvListaMesa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mesa);
        Intent intentMain = getIntent();

       // FirebaseService firebaseService = new FirebaseService();

        rvListaMesa = (RecyclerView)findViewById(R.id.rvListaMesas);

        final Profile profile = intentMain.getParcelableExtra("profile");
        id = profile.getId();
         List<Mesa> listaMesa = new ArrayList<>();
        if(listaMesa.size()!=0){
            listaMesa.clear();
            modelGetMesa = new ModelGetMesa(profile.getId(), ListaMesa.this, rvListaMesa);
            firebaseService.getMesaFirebase(modelGetMesa);


        }else {
           // listaMesa = firebaseService.getMesaFirebase(profile.getId());
        }

        btnCriarMesaActivity = (Button)findViewById(R.id.btnCriarMesaActivity);
        txtListaMesaActivity = (TextView)findViewById(R.id.txtTituloListaMesaActivity);
        txtListaMesaActivity.setText(profile.getFirstName()+getResources().getString(R.string.titulo_lista_mesas));
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

        modelGetMesa = new ModelGetMesa(id, ListaMesa.this, rvListaMesa);
        firebaseService.getMesaFirebase(modelGetMesa);

    }
    @Override
    public void onBackPressed()
    {
       finish();
    }
}
