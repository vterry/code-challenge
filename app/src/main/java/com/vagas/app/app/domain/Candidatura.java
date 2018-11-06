package com.vagas.app.app.domain;

import javax.persistence.*;

@Entity
public class Candidatura {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "candidatura_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaga_id")
    private Vaga vaga;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidato_id")
    private Candidato candidato;

    @Column(name = "candidatura")
    private Integer score;

    @Transient
    public Vaga getVaga() {
        return vaga;
    }

    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }

    @Transient
    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Candidatura() {

    }

    public Candidatura(Vaga vaga, Candidato candidato, Integer score) {
        this.vaga = vaga;
        this.candidato = candidato;
        this.score = score;
    }

}
