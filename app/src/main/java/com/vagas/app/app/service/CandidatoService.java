package com.vagas.app.app.service;

import com.vagas.app.app.domain.Candidato;
import com.vagas.app.app.repository.CandidatoRepository;
import com.vagas.app.app.service.dto.CandidatoDTO;
import com.vagas.app.app.service.mapper.CandidatoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidatoService {

    @Autowired
    private CandidatoRepository repository;

    @Autowired
    private CandidatoMapper mapper;

    public CandidatoDTO save(CandidatoDTO candidatoDTO) {
        Candidato candidato = mapper.toEntity(candidatoDTO);
        candidato = repository.save(candidato);
        return mapper.toDto(candidato);
    }

    public List<CandidatoDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }
}
