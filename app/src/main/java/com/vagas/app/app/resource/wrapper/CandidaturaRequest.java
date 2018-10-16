package com.vagas.app.app.resource.wrapper;

import io.swagger.annotations.ApiModelProperty;

public class CandidaturaRequest {

    @ApiModelProperty(notes = "ID da vaga pretendida.")
    private Long id_vaga;

    @ApiModelProperty(notes = "ID do candidato.")
    private Long id_candidato;

    public Long getId_vaga() {
        return id_vaga;
    }

    public void setId_vaga(Long id_vaga) {
        this.id_vaga = id_vaga;
    }

    public Long getId_candidato() {
        return id_candidato;
    }

    public void setId_candidato(Long id_candidato) {
        this.id_candidato = id_candidato;
    }

    public CandidaturaRequest() {
        super();
    }

    public CandidaturaRequest(Long id_vaga, Long id_candidato) {
        this.id_vaga = id_vaga;
        this.id_candidato = id_candidato;
    }
}
