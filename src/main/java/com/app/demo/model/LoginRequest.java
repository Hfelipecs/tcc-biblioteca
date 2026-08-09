package com.app.demo.model;

public class LoginRequest {

    private String Email;
    private String Senha;

    public LoginRequest() {}

    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public String getSenha() {
        return Senha;
    }
    public void setSenha(String senha) {
        Senha = senha;
    }
}