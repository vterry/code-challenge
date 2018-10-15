package com.vagas.app.app.util;

import com.vagas.app.app.domain.Trajeto;
import com.vagas.app.app.domain.Grafo;
import com.vagas.app.app.domain.Cidade;

import java.util.ArrayList;
import java.util.List;

public class GraphFactory {

    public static Grafo generate() {

        final List<Cidade> vertices = new ArrayList<>();
        final List<Trajeto> trajetos = new ArrayList<>();

        vertices.add(new Cidade("A", "A")); // 0
        vertices.add(new Cidade("B", "B")); // 1
        vertices.add(new Cidade("C", "C")); // 2
        vertices.add(new Cidade("D", "D")); // 3
        vertices.add(new Cidade("E", "E")); // 4
        vertices.add(new Cidade("F", "F")); // 5


        trajetos.add(new Trajeto("1", vertices.get(0), vertices.get(1), 5));
        trajetos.add(new Trajeto("2", vertices.get(1), vertices.get(0), 5));
        trajetos.add(new Trajeto("3", vertices.get(1), vertices.get(2), 7));
        trajetos.add(new Trajeto("4", vertices.get(1), vertices.get(3), 3));
        trajetos.add(new Trajeto("5", vertices.get(2), vertices.get(1), 7));
        trajetos.add(new Trajeto("6", vertices.get(2), vertices.get(4), 4));
        trajetos.add(new Trajeto("7", vertices.get(3), vertices.get(1), 3));
        trajetos.add(new Trajeto("8", vertices.get(3), vertices.get(4), 10));
        trajetos.add(new Trajeto("9", vertices.get(3), vertices.get(5), 8));
        trajetos.add(new Trajeto("10", vertices.get(4), vertices.get(2), 4));
        trajetos.add(new Trajeto("11", vertices.get(4), vertices.get(3), 10));
        trajetos.add(new Trajeto("12", vertices.get(5), vertices.get(3), 8));

        return new Grafo(vertices, trajetos);
    }

}
