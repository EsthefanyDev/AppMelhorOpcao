package com.example.melhor_opcao_delivery.Model;

public class UserModel {
        String nomeUsuario;
        String emailUsuario;
        String enderecoUsuario;
        String cpfUsuario;
        String senhaUsuario;

        public UserModel(){
        }

        public UserModel(String nomeUsuario, String emailUsuario, String enderecoUsuario, String cpfUsuario, String senhaUsuario){
            this.nomeUsuario = nomeUsuario;
            this.emailUsuario = emailUsuario;
            this.enderecoUsuario = enderecoUsuario;
            this.cpfUsuario = cpfUsuario;
            this.senhaUsuario = senhaUsuario;
        }
        public String getNomeUsuario(){
            return nomeUsuario;
        }
        public void setNomeUsuario(String nomeUsuario){
            this.nomeUsuario = nomeUsuario;
        }

        public String getEmailUsuario(){
            return emailUsuario;
        }
        public void setEmailUsuario(String emailUsuario){
            this.emailUsuario = emailUsuario;
        }

        public String getEnderecoUsuario(){
            return enderecoUsuario;
        }
        public void setEnderecoUsuario(String enderecoUsuario){
            this.enderecoUsuario= enderecoUsuario;
        }

        public String getCpfUsuario(){
            return cpfUsuario;
        }
        public void setCpfUsuario(String cpfUsuario){
            this.cpfUsuario = cpfUsuario;
        }

        public String getSenhaUsuario(){
            return senhaUsuario;
        }
        public void setSenhaUsuario(String senhaUsuario){
            this.senhaUsuario = senhaUsuario;

    }
}
