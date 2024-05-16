package com.example.melhor_opcao_delivery.Model;

public class CatModel {
    String cat_nome;
    String cat_imag;
    String tipo;

    public CatModel() {
    }

    public CatModel(String cat_nome, String cat_imag, String tipo) {
        this.cat_nome = cat_nome;
        this.cat_imag = cat_imag;
        this.tipo = tipo;
    }

    public String getCat_nome() {
        return cat_nome;
    }

    public void setCat_nome(String cat_nome) {
        this.cat_nome = cat_nome;
    }

    public String getCat_imag() {
        return cat_imag;
    }

    public void setCat_imag(String cat_imag) {
        this.cat_imag = cat_imag;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
