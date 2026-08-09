package com.app.demo.model;

public class LoginResponse {

    private String Nome;
    private String Email;
    private String Role;

    public LoginResponse() {}

    public LoginResponse(String nome, String email, String role) {
        this.Nome = nome;
        this.Email = email;
        this.Role = role;
    }

    public String getNome() {
        return Nome;
    }
    public void setNome(String nome) {
        Nome = nome;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public String getRole() {
        return Role;
    }
    public void setRole(String role) {
        Role = role;
    }
}