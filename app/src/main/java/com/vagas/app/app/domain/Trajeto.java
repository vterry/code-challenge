package com.vagas.app.app.domain;

public class Trajeto {

    private final String id;
    private final Cidade origem;
    private final Cidade destino;
    private final int distancia;

    public Trajeto(String id, Cidade origem, Cidade target, int distancia) {
        this.id = id;
        this.origem = origem;
        this.destino = target;
        this.distancia = distancia;
    }

    public String getId() {
        return id;
    }

    public Cidade getOrigem() {
        return origem;
    }

    public Cidade getDestino() {
        return destino;
    }

    public int getDistancia() {
        return distancia;
    }

    @Override
    public String toString() {
        return origem + " " + destino;
    }

}
