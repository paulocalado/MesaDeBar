package com.codgin.paulo.mesadebar.Model;

import java.util.List;

/**
 * Created by Paulo on 17/07/2017.
 */

public class Mesa {
    String nome;
    int quantidadePessoas;
    float total;
    //List<Pessoa> pessoas;
    public Mesa(){

    }

    public Mesa(String nome, int quantidadePessoas, float total) {
        this.nome = nome;
        this.quantidadePessoas = quantidadePessoas;
        this.total = total;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidadePessoas() {
        return quantidadePessoas;
    }

    public void setQuantidadePessoas(int quantidadePessoas) {
        this.quantidadePessoas = quantidadePessoas;
    }
}
