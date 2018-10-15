package com.vagas.app.app.service.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CandidatoDTO implements Comparable<CandidatoDTO> {

    private Long id;

    @NotNull(message = "Nome do candidato obrigatorio.")
    private String nome;

    private String profissao;

    @NotNull(message = "Localizacao do canditado obrigatorio.")
    private String localizacao;

    @NotNull(message = "Nivel do candidato obrigatorio.")
    @Min(1) @Max(5)
    private Integer nivel;

    private Integer score;

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

    public CandidatoDTO() {
        super();
    }

    public CandidatoDTO(Long id, String nome, String profissao, String localizacao, Integer nivel, Integer score) {
        this.id = id;
        this.nome = nome;
        this.profissao = profissao;
        this.localizacao = localizacao;
        this.nivel = nivel;
        this.score = score;
    }

    public int compareTo(CandidatoDTO candidatoDTO) {
        return this.score < candidatoDTO.getScore() ? 1 : this.score > candidatoDTO.score ? -1 : 0;
    }
}
