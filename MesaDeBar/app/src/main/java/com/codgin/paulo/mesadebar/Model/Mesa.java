package com.codgin.paulo.mesadebar.Model;

import java.util.List;
import java.util.Map;

/**
 * Created by Paulo on 17/07/2017.
 */

public class Mesa {
    String nome;
    int quantidadePessoas;
    float total;
    float totalComGorjeta;
    boolean hasTip;
    Map<String, Pessoa> pessoas;
    public Mesa(){

    }

    public Mesa(String nome, int quantidadePessoas, float total, float totalComGorjeta, boolean hasTip) {
        this.nome = nome;
        this.quantidadePessoas = quantidadePessoas;
        this.total = total;
        this.totalComGorjeta = totalComGorjeta;
        this.hasTip = hasTip;
    }

    public Map<String, Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(Map<String, Pessoa> pessoas) {
        this.pessoas = pessoas;
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

    public float getTotalComGorjeta() {
        return totalComGorjeta;
    }

    public void setTotalComGorjeta(float totalComGorjeta) {
        this.totalComGorjeta = totalComGorjeta;
    }

    public boolean isHasTip() {
        return hasTip;
    }

    public void setHasTip(boolean hasTip) {
        this.hasTip = hasTip;
    }
}
