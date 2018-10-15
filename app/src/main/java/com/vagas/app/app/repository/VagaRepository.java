package com.vagas.app.app.repository;

import com.vagas.app.app.domain.Candidato;
import com.vagas.app.app.domain.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {

    public Vaga findVagaById(Long id);

    @Query("Select v.candidatos from vaga v where v.id = :id")
    public List<Candidato> findAllApplications(@Param(value = "id") Long id);


}
