package com.codgin.paulo.mesadebar.Service;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Service;
import com.codgin.paulo.mesadebar.HomeMesa;
import com.codgin.paulo.mesadebar.Model.Mesa;
import com.codgin.paulo.mesadebar.Model.Pessoa;
import com.codgin.paulo.mesadebar.Model.Produto;
import com.codgin.paulo.mesadebar.ProdutoAdapter;
import com.codgin.paulo.mesadebar.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Paulo on 17/07/2017.
 */

public class DialogService {

    public FirebaseService firebaseService = new FirebaseService();

    public  void dialogAddMesa(final Context context, final String idUser){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(R.string.title_criar_mesa_dialog);
        builder1.setCancelable(false);
        final EditText input = new EditText(context);
        input.setHint(R.string.hint_criar_mesa_dialog);
        builder1.setView(input);
        builder1.setPositiveButton(
                R.string.positive_button,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String nomeMesa = input.getText().toString();
                        Toast.makeText(context, nomeMesa, Toast.LENGTH_LONG).show();
                        Mesa mesa = new Mesa(nomeMesa, 0,0);
                        firebaseService.criarMesaFirebase(idUser, mesa);
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                R.string.negative_button,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void dialogAddPessoa(final Context context, final String idUser, final String nomeMesa){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(R.string.title_criar_mesa_dialog);
        builder1.setCancelable(false);
        final EditText input = new EditText(context);
        input.setHint(R.string.hint_criar_mesa_dialog);
        builder1.setView(input);
        builder1.setPositiveButton(
                R.string.positive_button,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String nomePessoa = input.getText().toString();
                        Pessoa pessoa = new Pessoa(nomePessoa,0);
                        firebaseService.addPessoaMesaFirebase(idUser, nomeMesa, pessoa);
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                R.string.negative_button,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void dialogAddProduto(final Context context, final String idUser, final String nomeMesa, final List<Pessoa> listaPessoa){

        final List<Pessoa> pessoaListAux = new ArrayList<>();
        PessoaFirebaseService pessoaFirebase = new PessoaFirebaseService();
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_produto);
        dialog.setTitle("Adicione a seu Pedido");

        LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.lista_pessoa_dialog);
        final TextInputLayout edtNomeProduto = (TextInputLayout) dialog.findViewById(R.id.edtDialogNomeProduto);
        final TextInputLayout edtValorProduto = (TextInputLayout)dialog.findViewById(R.id.edit_valor_dialog);
        final TextInputLayout edtQtdProduto = (TextInputLayout)dialog.findViewById(R.id.edit_quantidade_produto);
        Button btnAdicionarProduto = (Button)dialog.findViewById(R.id.btnAdicionarProduto);

        for(final Pessoa pessoa: listaPessoa){
            CheckBox checkBox = new AppCompatCheckBox(context);
            checkBox.setText(pessoa.getNome());

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if(isChecked){
                        pessoaListAux.add(pessoa);
                        listaPessoa.remove(pessoa);
                    }else{
                        pessoaListAux.remove(pessoa);
                    }

                }
            });
            linearLayout.addView(checkBox);
        }

        btnAdicionarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProdutoFirebaseService produtoFirebaseService = new ProdutoFirebaseService();
                produtoFirebaseService.addProduto(edtNomeProduto.getEditText().getText().toString(),
                                                   Double.parseDouble(edtValorProduto.getEditText().getText().toString()),
                                                    idUser,
                                                    nomeMesa,
                                                    Integer.parseInt(edtQtdProduto.getEditText().getText().toString()),
                                                    pessoaListAux,
                                                    listaPessoa);

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void dialogProdutoPessoa(final String nomeMesa, final String idUser, final Context context, final List<Produto> listaProduto){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_produtos_pessoa);

        RecyclerView rvProdutoPessoa = (RecyclerView)dialog.findViewById(R.id.rvDialogPessoaProduto);
        final ProdutoAdapter adapter = new ProdutoAdapter(listaProduto);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        rvProdutoPessoa.setLayoutManager(llm);
        rvProdutoPessoa.setAdapter(adapter);

        dialog.show();

    }

    public void dialogDetalheProduto(Produto produto,
                                     final String nomeMesa,
                                     final String idUser,
                                     Context context){
        final  Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_detalhes_produto);

        TextView txtNomeProduto = (TextView)dialog.findViewById(R.id.txtNomeProdutoDetalhe);
        TextView txtPrecoProduto = (TextView)dialog.findViewById(R.id.txtPrecoDetalhe);
        TextView txtQuantidadeProduto = (TextView)dialog.findViewById(R.id.txtQuantidadeProdutoDetalhe);

        TextView txtUpdateProduto = (TextView)dialog.findViewById(R.id.txtAlterarProduto);

        txtNomeProduto.setText(produto.getNome());
        txtPrecoProduto.setText(String.format("%.2f",produto.getValor()));
        txtQuantidadeProduto.setText(String.valueOf(produto.getQuantidade()));

        dialog.show();
    }

    public void deletaProdutoPessoa(final String nomeMesa,
                                    final String iduser,
                                    final String nomeProduto,
                                    Context context,
                                    final List<Pessoa> pessoaList){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Deletar produto");
        builder.setMessage("Deletar "+nomeProduto+"?");
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ProdutoFirebaseService produtoFirebaseService = new ProdutoFirebaseService();
                PessoaFirebaseService pessoaFirebaseService = new PessoaFirebaseService();
                pessoaFirebaseService.deletaProdutoPessoaFirebase(iduser,nomeMesa,nomeProduto,pessoaList);
                produtoFirebaseService.deletaProdutoMesaFirebase(iduser, nomeMesa, nomeProduto);
            }
        });

        builder.setNegativeButton(R.string.negative_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create().show();
    }

}
