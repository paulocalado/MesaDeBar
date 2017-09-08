package com.codgin.paulo.mesadebar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.codgin.paulo.mesadebar.Fragments.ListaPessoaFragment;

public class HomeMesa extends AppCompatActivity {

    private TextView mTextMessage;
    Bundle bundle = new Bundle();
    String idUser, nomeMesa;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    ListaPessoaFragment listaPessoaFragment = new ListaPessoaFragment();
                    bundle.putString("idUser", idUser);
                    bundle.putString("nomeMesa", nomeMesa);
                    listaPessoaFragment.setArguments(bundle);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, listaPessoaFragment, "produtoFragment")
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:

                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_mesa);

        Intent intentListaMesa = getIntent();
        idUser = intentListaMesa.getStringExtra("idUser");
        nomeMesa = intentListaMesa.getStringExtra("nomeMesa");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ListaPessoaFragment listaPessoaFragment = new ListaPessoaFragment();
        bundle.putString("idUser", idUser);
        bundle.putString("nomeMesa", nomeMesa);
        listaPessoaFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, listaPessoaFragment, "produtoFragment")
                .addToBackStack(null)
                .commit();
    }

}
