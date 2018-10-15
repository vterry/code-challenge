package com.vagas.app.app.service.mapper;

import com.vagas.app.app.domain.Candidato;
import com.vagas.app.app.service.dto.CandidatoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CandidatoMapper extends EntityMapper<CandidatoDTO, Candidato> {

    @Override
    public Candidato toEntity(CandidatoDTO dto);

    @Override
    public CandidatoDTO toDto(Candidato entity);

    @Override
    public List<Candidato> toEntity(List<CandidatoDTO> dtoList);

    @Override
    public List<CandidatoDTO> toDto(List<Candidato> entityList);

}
