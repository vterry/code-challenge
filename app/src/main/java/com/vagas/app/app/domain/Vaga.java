package com.vagas.app.app.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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

    @OneToMany(mappedBy = "vaga", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Candidatura> candidaturas;

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

    public List<Candidatura> getCandidaturas() {
        return candidaturas;
    }

    public void setCandidaturas(List<Candidatura> candidaturas) {
        this.candidaturas = candidaturas;
    }


    public Vaga() {
        super();
    }

    public Vaga(Long id, String empresa, String titulo, String descricao, String localizacao, Integer nivel, List<Candidatura> candidaturas) {
        this.id = id;
        this.empresa = empresa;
        this.titulo = titulo;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.nivel = nivel;
        this.candidaturas = candidaturas;
    }
}
