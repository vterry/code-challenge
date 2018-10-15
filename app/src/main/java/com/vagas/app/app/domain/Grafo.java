package com.vagas.app.app.domain;

import java.util.List;

public class Grafo {

    private final List<Cidade> vertices;
    private final List<Trajeto> trajetos;

    public Grafo(List<Cidade> vertices, List<Trajeto> trajetos) {
        this.vertices = vertices;
        this.trajetos = trajetos;
    }

    public List<Cidade> getVertices() {
        return vertices;
    }

    public List<Trajeto> getTrajetos() {
        return trajetos;
    }

}
