package org.example.BasedeDatos;

import org.example.colecciones.Articulo;
import org.example.colecciones.Comentario;
import org.example.colecciones.Etiqueta;
import org.example.colecciones.Usuario;

import java.util.ArrayList;

public class BD {
    ArrayList <Usuario> listaUsuario = new ArrayList<>();
    public ArrayList <Comentario> listaComentario = new ArrayList<>();
    public ArrayList <Etiqueta> listaEtiqueta= new ArrayList<>();
    public ArrayList <Articulo> listaArticulo = new ArrayList<>();
    private static BD bd = null;



    public ArrayList<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(ArrayList<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public ArrayList<Comentario> getListaComentario() {
        return listaComentario;
    }

    public void setListaComentario(ArrayList<Comentario> listaComentario) {
        this.listaComentario = listaComentario;
    }

    public ArrayList<Etiqueta> getListaEtiqueta() {
        return listaEtiqueta;
    }

    public void setListaEtiqueta(ArrayList<Etiqueta> listaEtiqueta) {
        listaEtiqueta = listaEtiqueta;
    }

    public ArrayList<Articulo> getListaArticulo() {
        return listaArticulo;
    }

    public void setListaArticulo(ArrayList<Articulo> listaArticulo) {
        listaArticulo = listaArticulo;
    }

    public static BD getInstance() {
        if(bd == null) {
            bd = new BD();
        }
        return bd;
    }
    public Usuario getUsuarioPorUsername(String user){
        return listaUsuario.stream().filter(e -> e.getUsername().equals(user)).findFirst().orElse(null);
    }

    public Articulo getArticuloPorId(long idArticulo){
        return listaArticulo.stream().filter(e -> e.getId() == idArticulo).findFirst().orElse(null);
    }

    public Comentario getComentarioPorId(long idComentario){
        return listaComentario.stream().filter(e -> e.getId() == idComentario).findFirst().orElse(null);
    }

    public Etiqueta getEtiquetaPorId(long idEtiqueta){
        return listaEtiqueta.stream().filter(e -> e.getId() == idEtiqueta).findFirst().orElse(null);
    }
    public Usuario agregarUsuario(Usuario user){
        if(getUsuarioPorUsername(user.getUsername())!=null){
            System.out.println("Este usuario ya esta registrado");
            return null;
        }
        listaUsuario.add(user);
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        imprimirUsuarios();
        return user;

    }

    public static void imprimirUsuarios() {
        for (Usuario usuario : getInstance().getListaUsuario()) {
            System.out.println("Username: " + usuario.getUsername() + ", Password: " + usuario.getPassword());
        }
    }
    public static void imprimirArticulo() {
        for (Articulo articulo : getInstance().getListaArticulo()) {
            System.out.println("Cuerpo: " + articulo.getId() +", titulo: " + articulo.getTitulo() + ", Cuerpo: " + articulo.getCuerpo() + ", Autor: " + articulo.getAutor().getUsername()  + ", Fecha: " + articulo.getFecha());
        }
    }

    public Articulo agregarArticulo(Articulo articulo){
        if(getArticuloPorId(articulo.getId())!=null ){
            System.out.println("Articulo registrado");
            return null;
        }
        listaArticulo.add(articulo);
        imprimirArticulo();
        return articulo;
    }

    public Comentario agregarComentario(Comentario comentario){
        if(getComentarioPorId(comentario.getId())!=null ){
            System.out.println("Articulo registrado");
            return null;
        }
        listaComentario.add(comentario);
        return comentario;
    }

    public Etiqueta agregarEtiqueta(Etiqueta etiqueta){
        if(getEtiquetaPorId(etiqueta.getId())!=null ){
            System.out.println("Articulo registrado");
            return null;
        }
        listaEtiqueta.add(etiqueta);
        return etiqueta;
    }

    public Usuario verificarUsuario(String username,String password){
        Usuario usertemp = getUsuarioPorUsername(username);
        if(usertemp == null) {
            System.out.println("Usuario Incorrecto");
            return null;
        }
        if (!usertemp.getPassword().equals(password)) {
            System.out.println("Contraseña Incorrecta");
            //return null;
        }
        return usertemp;
    }
}
