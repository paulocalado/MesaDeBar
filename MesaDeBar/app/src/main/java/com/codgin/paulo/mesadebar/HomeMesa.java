package com.codgin.paulo.mesadebar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.codgin.paulo.mesadebar.Fragments.FinalizarFragment;
import com.codgin.paulo.mesadebar.Fragments.ListaPessoaFragment;
import com.codgin.paulo.mesadebar.Fragments.ProdutoFragment;

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
                            .replace(R.id.content, listaPessoaFragment, "listaPessoaFragment")
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.navigation_dashboard:
                    ProdutoFragment produtoFragment = new ProdutoFragment();
                    bundle.putString("idUser", idUser);
                    bundle.putString("nomeMesa", nomeMesa);
                    produtoFragment.setArguments(bundle);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, produtoFragment, "produtoFragment")
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.navigation_notifications:
                    FinalizarFragment finalizarFragment = new FinalizarFragment();
                    bundle.putString("idUser", idUser);
                    bundle.putString("nomeMesa", nomeMesa);
                    finalizarFragment.setArguments(bundle);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, finalizarFragment, "listaPessoaFragment")
                            .addToBackStack(null)
                            .commit();
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
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(nomeMesa);

        actionBar.setDisplayHomeAsUpEnabled(true);


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        finish();

    }

}
