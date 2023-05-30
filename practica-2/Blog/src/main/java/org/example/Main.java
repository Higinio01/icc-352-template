package org.example;

import io.javalin.Javalin;
import io.javalin.json.JavalinGson;
import org.example.BasedeDatos.BD;
import org.example.Controladores.ArticuloControlador;
import org.example.Controladores.UsuarioControlador;
import org.example.colecciones.Articulo;
import org.example.colecciones.Usuario;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class Main {

    public static void main(String[] args) {
        var app = Javalin.create(config -> {
            config.staticFiles.add(staticFilesConfig -> {
                staticFilesConfig.hostedPath = "/";
                staticFilesConfig.directory = "/publico";
            });

        }).start(8080);

        app.routes(()->{
            get("/",ctx -> {
                //userLogin = new Usuario("temp","temp","0000",false,false);
                ctx.redirect("/Principal/principal.html");
            });
            app.post("/login", ctx ->{
                Usuario user = UsuarioControlador.verificarUsuario(ctx);
                if(user!=null) {
                    ctx.sessionAttribute("idUsuario", user.getUsername());
                    ctx.redirect("/Principal/principalLogin.html");
                }else{
                    ctx.result("Hola mundo cualquier mierda");
                }
            });

            app.post("/registrarse",ctx -> {
                ctx.result("Hola mundo cualquier mierda");
                UsuarioControlador.crearUsuario(ctx);
                ctx.redirect("/Login/Login.html");
                //BD.getInstance().
            });

            app.post("/agregarArticulo", ctx -> {
                String usernameLogin = ctx.sessionAttribute("idUsuario");
                ArticuloControlador.crearArticulo(ctx, usernameLogin);
                BD.imprimirArticulo();
                ctx.redirect("/Principal/principalLogin.html");

            });

            app.get("/articulos", ctx -> {
            ArrayList<Articulo> aux = BD.getInstance().getListaArticulo();

            ctx.json(aux);
            });

            app.get("/verArticulos", ctx ->{

                String idArticuloString = ctx.pathParam("id");
                Long idArticulo = Long.parseLong(idArticuloString);
                Articulo elArticulo = BD.getInstance().getArticuloPorId(idArticulo);
                ctx.json(elArticulo);

            });

        });
    }
}