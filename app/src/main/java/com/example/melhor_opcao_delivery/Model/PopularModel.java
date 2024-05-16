package com.example.melhor_opcao_delivery.Model;

public class PopularModel {
    String name;
    String Descrição;
    String ranque;
    String desconto;
    String tipo;
    String img_url;

    public PopularModel(){
    }
    public PopularModel(String name, String descrição, String ranque, String desconto, String tipo, String img_url){
        this.name = name;
        this.Descrição = descrição;
        this.ranque = ranque;
        this.desconto = desconto;
        this.tipo = tipo;
        this.img_url = img_url;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrição() {
        return Descrição;
    }

    public void setDescrição(String descrição) {
        Descrição = descrição;
    }

    public String getRanque() {
        return ranque;
    }

    public void setRanque(String ranque) {
        this.ranque = ranque;
    }

    public String getDesconto() {
        return desconto;
    }

    public void setDesconto(String desconto) {
        this.desconto = desconto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
