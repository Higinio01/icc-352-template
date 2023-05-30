package org.example.Controladores;

import io.javalin.http.Context;
import org.example.BasedeDatos.BD;
import org.example.colecciones.Articulo;
import org.example.colecciones.Comentario;
import org.example.colecciones.Etiqueta;
import org.example.colecciones.Usuario;

public class EtiquetaControlador {
    public static void crearEtiqueta (Context ctx){
        long id = ctx.pathParamAsClass("idEtiqueta", Long.class).get();
        String nameEtiqueta = ctx.pathParam("etiqueta");

        Etiqueta etiqueta = BD.getInstance().getEtiquetaPorId(id);
        if(etiqueta!=null) {
            Etiqueta tmp = new Etiqueta(id,nameEtiqueta);
            BD.getInstance().agregarEtiqueta(tmp);
        }
    }
}
