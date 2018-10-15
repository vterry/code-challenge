package com.vagas.app.app.service.mapper;

import com.vagas.app.app.domain.Vaga;
import com.vagas.app.app.service.dto.VagaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VagaMapper extends EntityMapper<VagaDTO, Vaga> {

    @Override
    VagaDTO toDto(Vaga vaga);

    @Override
    Vaga toEntity(VagaDTO vagaDTO);

    @Override
    List<VagaDTO> toDto(List<Vaga> vagas);

    @Override
    List<Vaga> toEntity(List<VagaDTO> vagasDTO);
}
