package com.vagas.app.app.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "vaga")
public class Vaga implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vaga_id")
    private Long id;

    private String empresa;

    private String titulo;

    private String descricao;

    private String localizacao;

    private Integer nivel;

    @ManyToMany(mappedBy = "candidaturas")
    Set<Candidato> candidatos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public void setNivel(Integer level) {
        this.nivel = level;
    }

    public Set<Candidato> getCandidatos() {
        return candidatos;
    }

    public void addCandidato(Candidato candidato) {
        this.candidatos.add(candidato);
        candidato.getCandidaturas().add(this);
    }

    public void removeCandidato(Candidato candidato) {
        this.candidatos.remove(candidato);
        candidato.getCandidaturas().remove(this);
    }

    public Vaga() {
        super();
    }

    public Vaga(String empresa, String titulo, String descricao, String localizacao, Integer level, Set<Candidato> candidatos) {
        this.empresa = empresa;
        this.titulo = titulo;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.nivel = level;
        this.candidatos = candidatos;
    }
}
