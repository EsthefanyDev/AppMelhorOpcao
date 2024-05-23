package com.example.melhor_opcao_delivery.Model;

import java.io.Serializable;

public class ViewAllModel implements Serializable {

    //VARIAVEIS USADAS PARA CADASTRAR OS PRODUTOS NO FIREBASE
    String img_url;
    String nome;
    String descricao;
    String tipo;
    float preco;

    public ViewAllModel() {
    }

    public ViewAllModel(String img_url, String nome, String descricao, String tipo, float preco) {
        this.img_url = img_url;
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
        this.preco = preco;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }
}
