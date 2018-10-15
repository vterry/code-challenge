package com.vagas.app.app.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "candidato")
public class Candidato {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private String profissao;

    private String localizacao;

    private Integer nivel;

    private Integer score = 0;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "candidaturas",
            joinColumns = {@JoinColumn(name = "candidato_id")},
            inverseJoinColumns = {@JoinColumn(name = "vaga_id")}
    )
    Set<Vaga> candidaturas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Set<Vaga> getCandidaturas() {
        return candidaturas;
    }

    public void setCandidaturas(Set<Vaga> candidaturas) {
        this.candidaturas = candidaturas;
    }

    public Candidato() {
        super();
    }

    public Candidato(Long id, String nome, String profissao, String localizacao, Integer nivel, Integer score, Set<Vaga> candidaturas) {
        this.id = id;
        this.nome = nome;
        this.profissao = profissao;
        this.localizacao = localizacao;
        this.nivel = nivel;
        this.score = score;
        this.candidaturas = candidaturas;
    }
}
