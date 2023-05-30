package org.example.Controladores;

import io.javalin.http.Context;
import org.example.BasedeDatos.BD;
import org.example.colecciones.Articulo;
import org.example.colecciones.Comentario;
import org.example.colecciones.Etiqueta;
import org.example.colecciones.Usuario;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Stream;


public class ArticuloControlador {

    public static Date getCurrentDate() {
        LocalDate localDate = LocalDate.now();
        return java.sql.Date.valueOf(localDate);
    }
    private static final AtomicLong idCounter = new AtomicLong(0);

    public static long generateUniqueId() {
        return idCounter.incrementAndGet();
    }
    public static void crearArticulo (Context ctx, String userautor){
        ArrayList<Etiqueta> etiquetas = new ArrayList<>();
        ArrayList<Comentario> comentaios = new ArrayList<>();

        long idArticulo = generateUniqueId();
        String titulo = ctx.formParam("titulo");
        String cuerpo = ctx.formParam("cuerpo");
        Date fecha = getCurrentDate();

        Usuario autor = BD.getInstance().getUsuarioPorUsername(userautor);
        //Articulo articulo = BD.getInstance().getArticuloPorId(idArticulo);

        if(autor!=null /*articulo!=null*/) {
            Articulo tmp = new Articulo(idArticulo, titulo, cuerpo,autor, fecha);
            BD.getInstance().agregarArticulo(tmp);
            System.out.println("Se guardo miop");
        }
    }
}
