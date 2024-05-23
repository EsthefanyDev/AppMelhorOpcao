package com.example.melhor_opcao_delivery.Model;

import java.io.Serializable;

public class CardModel implements Serializable {

    String nomeProduto;
    float precoProduto;
    int quantidadeTT;
    float precoTotal;

    String documnentId;

    public String getDocumnentId() {
        return documnentId;
    }

    public void setDocumnentId(String documnentId) {
        this.documnentId = documnentId;
    }

    public CardModel() {
    }
    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public float getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(float precoProduto) {
        this.precoProduto = precoProduto;
    }

    public int getQuantidadeTT() {
        return quantidadeTT;
    }

    public void setQuantidadeTT(int quantidadeTT) {
        this.quantidadeTT = quantidadeTT;
    }

    public float getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(float precoTotal) {
        this.precoTotal = precoTotal;
    }

    public CardModel(String nomeProduto, float precoProduto, int quantidadeTT, float precoTotal) {
        this.nomeProduto = nomeProduto;
        this.precoProduto = precoProduto;
        this.quantidadeTT = quantidadeTT;
        this.precoTotal = precoTotal;
    }
}
