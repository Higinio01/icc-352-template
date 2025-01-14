package org.example.Controladores;

import io.javalin.Javalin;
import org.example.colecciones.Articulo;
import org.example.colecciones.Comentario;
import org.example.colecciones.Usuario;
import org.example.Servicios.ServicioArticulo;
import org.example.Servicios.ServicioComentario;
import org.example.Servicios.ServicioUsuario;
import org.example.util.BaseControlador;

public class ControladorComentario extends BaseControlador {
    private static ServicioUsuario servicio_usuario = ServicioUsuario.getInstancia();
    private static ServicioArticulo servicio_art = ServicioArticulo.getInstancia();
    private static ServicioComentario servicioComentario = ServicioComentario.getInstancia();
    private static long ultimoId = 0;
    public ControladorComentario(Javalin app) {
        super(app);
    }

    public void aplicarRutas(){
        app.before("/comentario", ctx -> {
            String username = ctx.sessionAttribute("username");
            Usuario usuario = servicio_usuario.getUsuarioLogeado();

            if (usuario == null) {
                Articulo articulo = ctx.sessionAttribute("artActual");
                System.out.println("Necesita iniciar sesion");
                ctx.redirect("/verArticulo/" + articulo.getId());
            }
        });

        app.post("/comentario", ctx -> {
            long nuevoId = ultimoId + 10;
            ultimoId = nuevoId;
            String username = servicio_usuario.getUsuarioLogeado().getUsername();
            String comentario = ctx.formParam("comentario");
            Usuario autor = servicio_usuario.getUsuarioPorUsuario(username);
            Articulo articulo = ctx.sessionAttribute("artActual");


            Comentario nuevoComentario = new Comentario(nuevoId,comentario, autor, articulo);
            servicioComentario.crearComentario(nuevoComentario);

            articulo.getListaComentarios().add(nuevoComentario);

            servicio_art.actualizarArticulo(articulo);

            ctx.redirect("/verArticulo/" + articulo.getId());

        });


        app.before("/eliminar/{id}", ctx -> {
            Comentario comentario = servicioComentario.getComentariosPorId(ctx.pathParamAsClass("id", long.class).get());
            String autor = comentario.getAutor().getUsername();
            String username = servicio_usuario.getUsuarioLogeado().getUsername();
            if(!servicio_usuario.getUsuarioPorUsuario(username).isAdmin()) {
                if (!autor.equals(username)) {
                    System.out.println("Necesita ser ADMIN o autor para eliminar un comentario");
                    Articulo articulo = ctx.sessionAttribute("artActual");
                    ctx.redirect("/verArticulo/" + articulo.getId());
                }
            }
        });

        app.get("/eliminar/{id}", ctx -> {
            servicioComentario.borrarComentario(ctx.pathParamAsClass("id", long.class).get());
            Articulo articulo = ctx.sessionAttribute("artActual");
            ctx.redirect("/verArticulo/" + articulo.getId());
        });

    }

}
