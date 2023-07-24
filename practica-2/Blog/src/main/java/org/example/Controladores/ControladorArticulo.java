package org.example.Controladores;

import io.javalin.Javalin;
import org.example.colecciones.Articulo;
import org.example.colecciones.Comentario;
import org.example.colecciones.Usuario;
import org.example.Servicios.ServicioArticulo;
import org.example.Servicios.ServicioComentario;
import org.example.Servicios.ServicioUsuario;
import org.example.util.BaseControlador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorArticulo extends BaseControlador {

    private static ServicioArticulo servicioArticulo = ServicioArticulo.getInstancia();
    private static ServicioUsuario servicioUsuario = ServicioUsuario.getInstancia();
    private static ServicioComentario servicioComentario = ServicioComentario.getInstancia();

    public ControladorArticulo(Javalin app){super (app);}
    private static long ultimoId = 0;

    public void aplicarRutas() {

        app.before("/crearArticulo", ctx -> {
            Usuario usuario = servicioUsuario.getUsuarioLogeado();
            if(usuario.getUsuario() == null){
                System.out.println("Necesita iniciar sesion");
                ctx.redirect("/");
            }
        });

        app.get("/crearArticulo", ctx ->{
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("titulo", "¡Publica un articulo!");
            modelo.put("accion", "/publicar");

            ctx.render("publico/NuevoArticulo.html", modelo);
        });

        app.before("/editar/{id}", ctx -> {
            Articulo articulo = servicioArticulo.getArticuloPorID(ctx.pathParamAsClass("id", long.class).get());
            String autor = articulo.getAutor().getUsername();
            String username = servicioUsuario.getUsuarioLogeado().getUsername();
            if (!servicioUsuario.getUsuarioPorUsuario(username).isAdmin()) {
                if (!autor.equals(username)) {
                    System.out.println("Solo un admin o el autor del articulo lo puede editar");
                    ctx.redirect("/");
                }
            }
        });

        app.get("/editar/{id}", ctx -> {
            Articulo articulo = servicioArticulo.getArticuloPorID(ctx.pathParamAsClass("id", long.class).get());

            Map<String, Object> modelo = new HashMap<>();
            modelo.put("titulo", "Editar "+articulo.getId());
            modelo.put("articulo", articulo);
            modelo.put("accion", "/editar");
            ctx.render("publico/NuevoArticulo.html", modelo);
        });

        app.post("/editar", ctx -> {
            String titulo = ctx.formParam("titulo");
            String cuerpo = ctx.formParam("cuerpo");
            String etiquetas = ctx.formParam("etiquetas");

            Articulo articuloExistente = ctx.sessionAttribute("artActual");

            articuloExistente.setTitulo(titulo);
            articuloExistente.setCuerpo(cuerpo);

            String[] etiquetasArray = etiquetas.split(", ");
            List<String> listaEtiquetas = new ArrayList<>();
            for (String etiquetaStr : etiquetasArray) {
                String etiqueta = etiquetaStr;
                listaEtiquetas.add(etiqueta);
            }
            articuloExistente.setListaEtiqueta(listaEtiquetas);

            servicioArticulo.actualizarArticulo(articuloExistente);

            ctx.redirect("/verArticulo/" + articuloExistente.getId());

        });

        app.post("/publicar", ctx -> {
            long nuevoId = ultimoId + 10;
            ultimoId = nuevoId;
            String titulo = ctx.formParam("titulo");
            String cuerpo = ctx.formParam("cuerpo");
            String etiquetas = ctx.formParam("etiquetas");
            Usuario autor = servicioUsuario.getUsuarioLogeado();
            LocalDate fecha =  LocalDate.now();

            String[] etiquetasArray = etiquetas.split(", ");
            List<String> listaEtiquetas = new ArrayList<>();
            for (String etiquetaStr : etiquetasArray) {
                String etiqueta = etiquetaStr;
                listaEtiquetas.add(etiqueta);
            }

            if (servicioArticulo.getArticuloPorID(nuevoId) == null) {
                if(servicioArticulo.autenticarArticulo(nuevoId, titulo,cuerpo)) {
                    Articulo art = new Articulo(nuevoId, titulo, cuerpo, autor, fecha);
                    servicioArticulo.crearArticulo(art);
                    art.setListaEtiqueta(listaEtiquetas);

                }else{
                    System.out.println("Campos necesarios");
                    ctx.redirect("/publicar");
                }

                ctx.redirect("/");

            } else {
                ctx.html("Este articulo ya existe. <a href='/nuevoUsuario'>Volver a intentar</a>");
            }
        });

        app.get("/verArticulo/{id}", ctx ->{
            Articulo articulo = servicioArticulo.getArticuloPorID(ctx.pathParamAsClass("id", long.class).get());
            ctx.sessionAttribute("artActual", articulo);

            List<Comentario> listaComen = servicioComentario.getComentariosPorArticulo(articulo);

            Map<String, Object> modelo = new HashMap<>();
            modelo.put("titulo", "Vista Formulario"+articulo.getId());
            modelo.put("articulo", articulo);
            modelo.put("listaComen", listaComen);
            modelo.put("accion", "/verArticulo");

            ctx.render("publico/VistaArticulo.html", modelo);
        });


    }
}
