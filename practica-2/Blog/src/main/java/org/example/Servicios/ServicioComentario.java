package org.example.Servicios;

import org.example.colecciones.Articulo;
import org.example.colecciones.Comentario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServicioComentario {

    private static ServicioComentario instancia;
    //private static ServicioArticulo servicio_articulo = ServicioArticulo.getInstancia();
    private List<Comentario> listaComentarios = new ArrayList<>();

    private ServicioComentario(){}

    public static ServicioComentario getInstancia(){
        if(instancia==null){
            instancia = new ServicioComentario();
        }
        return instancia;
    }

    public Comentario crearComentario(Comentario comen) {
        Comentario tmp = getComentarioPorID(comen.getId());
        if(tmp!=null) {
            System.out.println("ERROR");
            return null;
        }
        System.out.println("Su comentario ha sido publicado");
        listaComentarios.add(comen);

        //AGREGAR COMENTARIO
        return comen;
    }

    private Comentario getComentarioPorID(long id) {
        return listaComentarios.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    public List<Comentario> getComentariosPorArticulo(Articulo articulo) {
        return listaComentarios.stream()
                .filter(c -> c.getArticulo().equals(articulo))
                .collect(Collectors.toList());
    }

    public boolean borrarComentario(long id) {
        Comentario tmp = getComentarioPorID(id);
        if(tmp==null) {
            System.out.println("ERROR");
            return false;
        }
        System.out.println("Comentario eliminado");
        listaComentarios.remove(tmp);
        return true;
    }

    public Comentario getComentariosPorId(long Id) {
        for (Comentario comen : listaComentarios) {
            if (comen.getId() == Id) {
                return comen; // Se encontró el comentario
            }
        }
        return null; // No se encontró el comentario
    }

}
