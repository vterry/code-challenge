package com.vagas.app.app.service;

import com.vagas.app.app.domain.Candidato;
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
        vaga.addCandidato(candidato);
        repository.save(vaga);
    }


    public List<CandidatoDTO> listarCandidaturas(Long id) {
        Vaga vaga = repository.findVagaById(id);
        List<CandidatoDTO> result = repository.findAllApplications(id).stream()
                .map(c -> new Candidato(c.getId(), c.getNome(), c.getProfissao(), c.getLocalizacao(), c.getNivel(), Score.getScore(vaga, c), c.getCandidaturas()))
                .map(cMapper::toDto).collect(Collectors.toList());
        Collections.sort(result);
        return result;
    }
}
