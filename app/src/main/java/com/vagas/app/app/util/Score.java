package com.vagas.app.app.util;

import com.vagas.app.app.domain.Candidato;
import com.vagas.app.app.domain.Vaga;

public class Score {

    public static int getScore(Vaga vaga, Candidato candidato) {
        Djikstra d = new Djikstra(GraphFactory.generate());
        d.run(candidato.getLocalizacao());
        int diferencaNivel = Math.abs(vaga.getNivel() - candidato.getNivel());
        int n = 100 - 25 * diferencaNivel;
        return (n + d.getTamanhoMenorCaminho(vaga.getLocalizacao())) / 2;
    }

}
