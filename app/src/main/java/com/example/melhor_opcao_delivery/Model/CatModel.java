package com.example.melhor_opcao_delivery.Model;

public class CatModel {
    // Strings usadas para armazenar as informações das categorias
    String cat_nome; // Nome da categoria
    String cat_imag; // Imagem da categoria (presumivelmente uma URL ou caminho de arquivo)
    String tipo; // Tipo da categoria

    // Construtor padrão (sem argumentos)
    public CatModel() {
    }

    // Construtor parametrizado (com argumentos)
    public CatModel(String cat_nome, String cat_imag, String tipo) {
        this.cat_nome = cat_nome; // Inicializa o nome da categoria
        this.cat_imag = cat_imag; // Inicializa a imagem da categoria
        this.tipo = tipo; // Inicializa o tipo da categoria
    }

    // Método getter para obter o nome da categoria
    public String getCat_nome() {
        return cat_nome;
    }

    // Método setter para definir o nome da categoria
    public void setCat_nome(String cat_nome) {
        this.cat_nome = cat_nome;
    }

    // Método getter para obter a imagem da categoria
    public String getCat_imag() {
        return cat_imag;
    }

    // Método setter para definir a imagem da categoria
    public void setCat_imag(String cat_imag) {
        this.cat_imag = cat_imag;
    }

    // Método getter para obter o tipo da categoria
    public String getTipo() {
        return tipo;
    }

    // Método setter para definir o tipo da categoria
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

