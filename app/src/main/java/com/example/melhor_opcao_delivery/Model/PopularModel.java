package com.example.melhor_opcao_delivery.Model;

public class PopularModel {
    // Strings usadas para armazenar as informações de itens populares
    String tipo; // Tipo do item popular
    String img_url; // URL da imagem do item popular

    // Construtor padrão (sem argumentos)
    public PopularModel() {
    }

    // Construtor parametrizado (com argumentos)
    public PopularModel(String tipo, String img_url) {
        this.tipo = tipo; // Inicializa o tipo do item popular
        this.img_url = img_url; // Inicializa a URL da imagem do item popular
    }

    // Método getter para obter o tipo do item popular
    public String getTipo() {
        return tipo;
    }

    // Método setter para definir o tipo do item popular
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // Método getter para obter a URL da imagem do item popular
    public String getImg_url() {
        return img_url;
    }

    // Método setter para definir a URL da imagem do item popular
    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}

