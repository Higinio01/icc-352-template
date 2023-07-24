package org.example.Servicios;

import org.example.colecciones.Articulo;
import org.example.colecciones.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServicioArticulo {

    private static  ServicioArticulo instancia;
    private List<Articulo> ListaArticulos = new ArrayList<>();

    private ServicioArticulo(){
        LocalDate date = LocalDate.now();
        Usuario autor = new Usuario();
        List<String> ListaEtiquetas = new ArrayList<>();

        autor = ServicioUsuario.buscarUsuarioPorUsername("admin");
        Articulo art1 = new Articulo(1,"Ejemplo", "Texto de Ejemplo",autor,date);
        ListaEtiquetas.add("Etiqueta");
        ListaEtiquetas.add("Etiqueta2");
        ListaArticulos.add(art1);
        art1.setListaEtiqueta(ListaEtiquetas);

    }

    public static ServicioArticulo getInstancia(){
        if(instancia==null){
            instancia = new ServicioArticulo();
        }
        return instancia;
    }

    public List<Articulo> getListaArticulos() {
        return ListaArticulos;
    }

    public void setListaProductos(List<Articulo> ListaArticulos) {
        this.ListaArticulos = ListaArticulos;
    }

    //CREAR
    public Articulo crearArticulo(Articulo articulo) {

        Articulo tmp = getArticuloPorID(articulo.getId());
        if(tmp!=null) {
            System.out.println("Alerta: Articulo ya registrado");
            return null;
        }
        System.out.println("Alerta: Producto registrado correctamente");
        ListaArticulos.add(articulo);
        return articulo;
    }

    public Articulo actualizarArticulo(Articulo articulo) {
        Articulo tmp = getArticuloPorID(articulo.getId());
        if(tmp == null) {
            System.out.println("ERROR");
            return null;
        }
        System.out.println("Articulo actualizado correctamente");
        tmp.actualizar(articulo);
        return articulo;
    }

    public Articulo getArticuloPorID(long id) {
        return ListaArticulos.stream().filter(p -> p.getId() == id).findFirst().orElse(null);

    }

    public boolean autenticarArticulo(long nuevoId, String titulo, String cuerpo) {
        if (titulo != null && !titulo.isEmpty() && cuerpo != null && !cuerpo.isEmpty()) {
            return true;
        }
        return false;
    }
}
