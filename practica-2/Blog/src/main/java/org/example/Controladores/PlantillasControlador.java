package org.example.Controladores;

import io.javalin.Javalin;
import io.javalin.rendering.JavalinRenderer;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.example.util.BaseControlador;


public class PlantillasControlador extends BaseControlador {

    public PlantillasControlador(Javalin app) {
        super(app);
        registrandoPlantillas();
    }

    private void registrandoPlantillas(){
        JavalinRenderer.register(new JavalinThymeleaf(), ".html");
    }

    @Override
    public void aplicarRutas() {

    }
}

