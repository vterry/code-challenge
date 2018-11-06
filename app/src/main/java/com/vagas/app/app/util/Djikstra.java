package com.vagas.app.app.util;

import com.vagas.app.app.domain.Trajeto;
import com.vagas.app.app.domain.Grafo;
import com.vagas.app.app.domain.Cidade;

import java.util.*;

public class Djikstra {


    private final List<Cidade> cidades;
    private final List<Trajeto> trajetos;
    private Set<Cidade> cidadesVisitadas;
    private Set<Cidade> cidadesNaoVisitadas;
    private Map<Cidade, Cidade> antecessores;
    private Map<Cidade, Integer> distancias;

    public Djikstra(Grafo grafo) {
        this.cidades = new ArrayList<Cidade>(grafo.getVertices());
        this.trajetos = new ArrayList<Trajeto>(grafo.getTrajetos());
    }

    public void run(String nomeCidadeOrigem) {

        Cidade origem = localizarCidade(nomeCidadeOrigem);

        cidadesVisitadas = new HashSet<Cidade>();
        cidadesNaoVisitadas = new HashSet<Cidade>();
        distancias = new HashMap<Cidade, Integer>();
        antecessores = new HashMap<Cidade, Cidade>();
        distancias.put(origem, 0);
        cidadesNaoVisitadas.add(origem);

        while (cidadesNaoVisitadas.size() > 0) {
            Cidade cidade = getMinimo(cidadesNaoVisitadas);
            cidadesVisitadas.add(cidade);
            cidadesNaoVisitadas.remove(cidade);
            acharMenorDistancia(cidade);
        }
    }

    private void acharMenorDistancia(Cidade cidade) {
        List<Cidade> cidadesVizinhas = getVizinhos(cidade);
        for (Cidade destino : cidadesVizinhas) {
            if (getMenorDistancia(destino) > getMenorDistancia(cidade)
                    + getDistancia(cidade, destino)) {
                distancias.put(destino, getMenorDistancia(cidade)
                        + getDistancia(cidade, destino));
                antecessores.put(destino, cidade);
                cidadesNaoVisitadas.add(destino);
            }
        }

    }

    private int getDistancia(Cidade cidade, Cidade destino) {
        for (Trajeto trajeto : trajetos) {
            if (trajeto.getOrigem().equals(cidade)
                    && trajeto.getDestino().equals(destino)) {
                return trajeto.getDistancia();
            }
        }
        throw new RuntimeException("It shouldn't happen");
    }

    private List<Cidade> getVizinhos(Cidade cidade) {
        List<Cidade> vizinhos = new ArrayList<>();
        for (Trajeto trajeto : trajetos) {
            if (trajeto.getOrigem().equals(cidade)
                    && !foiVisitado(trajeto.getDestino())) {
                vizinhos.add(trajeto.getDestino());
            }
        }
        return vizinhos;
    }

    //Retorna a cidade com o menor peso avaliado.
    private Cidade getMinimo(Set<Cidade> cidades) {
        Cidade minimo = null;
        for (Cidade cidade : cidades) {
            if (minimo == null) {
                minimo = cidade;
            } else {
                if (getMenorDistancia(cidade) < getMenorDistancia(minimo)) {
                    minimo = cidade;
                }
            }
        }
        return minimo;
    }

    //Verifica se o no ja foi visistado.
    private boolean foiVisitado(Cidade cidade) {
        return cidadesVisitadas.contains(cidade);
    }

    //Retorna o menor valor avaliado ate o destino selecionado.
    private int getMenorDistancia(Cidade destino) {
        Integer d = distancias.get(destino);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    //Retorna o caminho entre dois vertices, caso nao exista será retornado null.
    public LinkedList<Cidade> getCaminho(Cidade destino) {
        LinkedList<Cidade> caminho = new LinkedList<Cidade>();
        Cidade step = destino;
        if (antecessores.get(step) == null) {
            return null;
        }
        caminho.add(step);
        while (antecessores.get(step) != null) {
            step = antecessores.get(step);
            caminho.add(step);
        }
        Collections.reverse(caminho);
        return caminho;
    }


    //Retorna o SCORE entre as cidades baseado na tabela abaixo.
    public int getTamanhoMenorPeso(String target) {
        int distancia = distancias.get(localizarCidade(target));

        if (distancia >= 0 && distancia <= 5) {
            distancia = 100;
        } else if (distancia > 5 && distancia <= 10) {
            distancia = 75;
        } else if (distancia > 10 && distancia <= 15) {
            distancia = 50;
        } else if (distancia > 15 && distancia <= 20) {
            distancia = 25;
        } else {
            distancia = 0;
        }

        return distancia;
    }

    //Dado uma String, será retornado a cidade equivalente.
    private Cidade localizarCidade(String nomeCidade) {
        Cidade cidade = this.cidades.stream()
                .filter(c -> c.getName().equals(nomeCidade))
                .findAny()
                .orElse(null);

        return cidade;
    }
}
