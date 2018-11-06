package com.vagas.app.app.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "candidato")
public class Candidato {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "candidato_id")
    private Long id;

    private String nome;

    private String profissao;

    private String localizacao;

    private Integer nivel;

    @OneToMany(mappedBy = "candidato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Candidatura> candidaturas;

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


    public List<Candidatura> getCandidaturas() {
        return candidaturas;
    }

    public void setCandidaturas(List<Candidatura> candidaturas) {
        this.candidaturas = candidaturas;
    }

    public Candidato() {
        super();
    }

    public Candidato(Long id, String nome, String profissao, String localizacao, Integer nivel, List<Candidatura> candidaturas, List<Candidatura> candidaturas1) {
        this.id = id;
        this.nome = nome;
        this.profissao = profissao;
        this.localizacao = localizacao;
        this.nivel = nivel;
        this.candidaturas = candidaturas;
        this.candidaturas = candidaturas1;
    }
}
