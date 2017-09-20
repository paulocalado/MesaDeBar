package com.codgin.paulo.mesadebar.Model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Paulo on 21/07/2017.
 */

public class Pessoa {
    String nome;
    double total;
    HashMap<String, Produto> produtosPessoa;

    public  Pessoa(){

    }

    public Pessoa(String nome, float total) {
        this.nome = nome;
        this.total = total;
    }

    public Pessoa(String nome, float total, HashMap<String, Produto> produtosPessoa) {
        this.nome = nome;
        this.total = total;
        this.produtosPessoa = produtosPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public HashMap<String, Produto> getProdutos() {
        return produtosPessoa;
    }

    public void setProdutos(HashMap<String, Produto> produtosPessoa) {
        this.produtosPessoa = produtosPessoa;
    }
}
