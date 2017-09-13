package com.codgin.paulo.mesadebar.Model;

import java.util.List;

/**
 * Created by Paulo on 21/07/2017.
 */

public class Pessoa {
    String nome;
    double total;
    List<Produto> produtos;

    public  Pessoa(){

    }

    public Pessoa(String nome, float total) {
        this.nome = nome;
        this.total = total;
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

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}
