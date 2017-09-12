package com.codgin.paulo.mesadebar.Model;

/**
 * Created by Paulo on 21/07/2017.
 */

public class Produto {
    String nome;
    double valor;
    int quantidade;
    public Produto(){}

    public Produto(String nome, double valor, int quantidade) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    public Produto(String nome, float valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
