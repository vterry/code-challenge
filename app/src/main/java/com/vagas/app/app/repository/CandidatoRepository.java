package com.vagas.app.app.repository;

import com.vagas.app.app.domain.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Long> {

    public Candidato findCandidatoById(Long id);

}
