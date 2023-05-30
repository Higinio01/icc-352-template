package org.example.Controladores;

import io.javalin.http.Context;
import org.example.BasedeDatos.BD;
import org.example.colecciones.Usuario;


public class UsuarioControlador {
    Usuario userLogin;

    public Usuario getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(Usuario userLogin) {
        this.userLogin = userLogin;
    }

    public static void crearUsuario (Context ctx){
        String user = ctx.formParam("username");
        String name = ctx.formParam("nombre");
        String pass = ctx.formParam("password");

        Usuario tmp = new Usuario(user, name, pass, false,true);
        if (BD.getInstance().agregarUsuario(tmp)==null){
            System.out.println("error");
        };
    }

    public static Usuario verificarUsuario(Context ctx){
        String user = ctx.formParam("username");
        String pass = ctx.formParam("password");
        Usuario usertemp = BD.getInstance().verificarUsuario(user, pass);
        if(usertemp!=null){
            return usertemp;
        }
        return null;
    }


}
