package com.vagas.app.app.service.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class VagaDTO {

    @ApiModelProperty(notes = "ID da vaga - Essa informação é gerada automaticamente.")
    private Long id;

    @ApiModelProperty(notes = "Nome da empresa.")
    @NotNull(message = "Nome da empresa obrigatorio.")
    private String empresa;

    @ApiModelProperty(notes = "Titulo da vaga.")
    @NotNull(message = "Titulo da vaga obrigatorio.")
    private String titulo;

    @ApiModelProperty(notes = "Descrição da vaga.")
    @NotNull(message = "Descricao da vaga obrigatoria.")
    private String descricao;

    @ApiModelProperty(notes = "Localização da vaga.")
    @NotNull(message = "Localizacao obrigatoria.")
    private String localizacao;

    @ApiModelProperty(notes = "Nivel de experiencia requerido pela vaga.")
    @NotNull(message = "Nivel da vaga obrigatorio.")
    @Min(1) @Max(5)
    private Integer nivel;

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

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public VagaDTO() {
        super();
    }

    public VagaDTO(Long id, String empresa, String titulo, String descricao, String localizacao, Integer nivel) {
        this.id = id;
        this.empresa = empresa;
        this.titulo = titulo;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.nivel = nivel;
    }
}
