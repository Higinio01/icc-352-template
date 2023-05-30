package org.example.util;

import io.javalin.Javalin;

public abstract class baseControlador {

    protected Javalin app;

    public baseControlador(Javalin app){
        this.app = app;
    }

    abstract public void aplicarRutas();
}
