package org.example.Controladores;

import io.javalin.Javalin;
import org.example.colecciones.Articulo;
import org.example.colecciones.Usuario;
import org.example.Servicios.ServicioArticulo;
import org.example.Servicios.ServicioUsuario;
import org.example.util.BaseControlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorInicio extends BaseControlador {
    private static ServicioUsuario servicio_usuario = ServicioUsuario.getInstancia();
    private static ServicioArticulo servicio_art = ServicioArticulo.getInstancia();

    public ControladorInicio(Javalin app){super (app);}

    public void aplicarRutas() {

        app.get("/", ctx ->{
            ctx.redirect("/listar");
        });

        app.get("/listar", ctx ->{
            Usuario usuario = servicio_usuario.getUsuarioLogeado();
            List<Articulo> lista = servicio_art.getListaArticulos();
            Map<String, Object> modelo = new HashMap<>();

            modelo.put("titulo", "Inicio");
            modelo.put("lista", lista);
            if(usuario == null){
                modelo.put("accion", "LOG IN");
            }else{
                modelo.put("accion", "LOG OUT");
            }
            ctx.render("publico/principal.html", modelo);

        });

        app.get("/login", ctx -> {
            Usuario usuario = servicio_usuario.getUsuarioLogeado();
            if(usuario == null) {
                Map<String, Object> modelo = new HashMap<>();
                modelo.put("titulo", "Inicio de Sesion");
                ctx.render("publico/login.html", modelo);
            }else{
                servicio_usuario.setUsuarioLogeado(null);
                ctx.redirect("/");
            }
        });

        app.post("/login", ctx -> {
            String username = ctx.formParam("username");
            String password = ctx.formParam("password");

            if (servicio_usuario.autenticarUsuario(username,password) != null) {
                Usuario usuario = servicio_usuario.getUsuarioPorUsuario(username);
                servicio_usuario.setUsuarioLogeado(usuario);
                ctx.redirect("/");
            } else {
                ctx.html("Credenciales incorrectas. <a href='/login'>Volver a intentar</a>");
            }
        });

    }

}
