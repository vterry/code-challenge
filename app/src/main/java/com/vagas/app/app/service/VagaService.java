package com.vagas.app.app.service;

import com.vagas.app.app.domain.Candidato;
import com.vagas.app.app.domain.Candidatura;
import com.vagas.app.app.domain.Vaga;
import com.vagas.app.app.repository.CandidatoRepository;
import com.vagas.app.app.repository.VagaRepository;
import com.vagas.app.app.service.dto.CandidatoDTO;
import com.vagas.app.app.service.dto.VagaDTO;
import com.vagas.app.app.service.mapper.CandidatoMapper;
import com.vagas.app.app.service.mapper.VagaMapper;
import com.vagas.app.app.util.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VagaService {

    @Autowired
    private VagaRepository repository;

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Autowired
    private VagaMapper mapper;

    @Autowired
    private CandidatoMapper cMapper;
    private List<Candidatura> ArrayList;


    public VagaDTO save(VagaDTO vagaDto) {
        Vaga vaga = mapper.toEntity(vagaDto);
        vaga = repository.save(vaga);
        return mapper.toDto(vaga);
    }

    public List<VagaDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public boolean isPresent(Long id) {
        return repository.existsById(id);
    }

    public void saveNewApplication(Long id_vaga, Long id_candidato) {

        Vaga vaga = repository.findVagaById(id_vaga);
        Candidato candidato = candidatoRepository.findCandidatoById(id_candidato);
        Candidatura candidatura = new Candidatura(vaga, candidato, Score.getScore(vaga, candidato));
        vaga.getCandidaturas().add(candidatura);
        repository.save(vaga);
    }


    public List<CandidatoDTO> listarCandidaturas(Long id) {
        Vaga vaga = repository.findVagaById(id);
        return vaga.getCandidaturas()
                .stream()
                .map(c -> new CandidatoDTO(c.getCandidato().getId(), c.getCandidato().getNome(), c.getCandidato().getProfissao(), c.getCandidato().getLocalizacao(), c.getCandidato().getNivel(), c.getScore()))
                .sorted(CandidatoDTO::compareTo)
                .collect(Collectors.toList());
    }
}
