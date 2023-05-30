package org.example.colecciones;

public class Usuario {
    String username;
    String Nombre;
    String password;
    boolean administrador;
    boolean autor;

    public Usuario(String username, String nombre, String password, boolean administrador, boolean autor) {
        this.username = username;
        Nombre = nombre;
        this.password = password;
        this.administrador = administrador;
        this.autor = autor;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public boolean isAutor() {
        return autor;
    }

    public void setAutor(boolean autor) {
        this.autor = autor;
    }
}
