package com.codgin.paulo.mesadebar.Model;

import java.util.List;

/**
 * Created by Paulo on 18/07/2017.
 */

public class User {
    String id, nome;
    List<Mesa> mesasUser;

    public User(String id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public User(String id, String nome, List<Mesa> mesasUser) {
        this.id = id;
        this.nome = nome;
        this.mesasUser = mesasUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Mesa> getMesasUser() {
        return mesasUser;
    }

    public void setMesasUser(List<Mesa> mesasUser) {
        this.mesasUser = mesasUser;
    }
}
