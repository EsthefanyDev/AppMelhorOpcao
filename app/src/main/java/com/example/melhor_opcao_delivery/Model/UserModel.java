package com.example.melhor_opcao_delivery.Model;

public class UserModel {
        String nomeUsuario;
        String emailUsuario;
        String enderecoUsuario;
        String telefoneUsuario;
        String senhaUsuario;
        String imgPerfil;
        boolean isAdmin;

    public UserModel() {
        // Este é o construtor padrão exigido pelo Firebase
    }

        public UserModel(String nomeUsuario, String emailUsuario, String enderecoUsuario, String telefoneUsuario, String senhaUsuario, boolean isAdmin){
            this.nomeUsuario = nomeUsuario;
            this.emailUsuario = emailUsuario;
            this.enderecoUsuario = enderecoUsuario;
            this.telefoneUsuario = telefoneUsuario;
            this.senhaUsuario = senhaUsuario;
            this.isAdmin = isAdmin;
        }

    public String getImgPerfil() {
        return imgPerfil;
    }

    public void setImgPerfil(String imgPerfil) {
        this.imgPerfil = imgPerfil;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getEnderecoUsuario() {
        return enderecoUsuario;
    }

    public void setEnderecoUsuario(String enderecoUsuario) {
        this.enderecoUsuario = enderecoUsuario;
    }

    public String getCpfUsuario() {
        return telefoneUsuario;
    }

    public void setCpfUsuario(String cpfUsuario) {
        this.telefoneUsuario = cpfUsuario;
    }

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}

