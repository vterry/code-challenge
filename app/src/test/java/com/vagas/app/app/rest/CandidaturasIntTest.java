package com.vagas.app.app.rest;

import com.vagas.app.app.AppApplication;
import com.vagas.app.app.domain.Candidato;
import com.vagas.app.app.domain.Candidatura;
import com.vagas.app.app.domain.Vaga;
import com.vagas.app.app.repository.CandidatoRepository;
import com.vagas.app.app.repository.VagaRepository;
import com.vagas.app.app.resource.VagaResource;
import com.vagas.app.app.resource.wrapper.CandidaturaRequest;
import com.vagas.app.app.service.VagaService;
import com.vagas.app.app.util.Score;
import com.vagas.app.app.util.Util;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApplication.class)
public class CandidaturasIntTest {

    private static final String VAGA_EMPRESA = "DEFAULT TEST LTDA";
    private static final String VAGA_TITULO = "Engenheiro de Software";
    private static final String VAGA_DESCRICAO = "DEFAULT DESCRIPTION";
    private static final String VAGA_LOCALIZACAO = "A";
    private static final Integer VAGA_NIVEL = 5;

    private static final String N1_NOME = "Candidato 1";
    private static final String N1_PROFISSAO = "Engenheiro de Software";
    private static final String N1_LOCALIZACAO = "F";
    private static final Integer N1_NIVEL = 3;
    private static final Integer N1_SCORE = 37;

    private static final String N2_NOME = "Candidato 2";
    private static final String N2_PROFISSAO = "Desenvolvedor";
    private static final String N2_LOCALIZACAO = "B";
    private static final Integer N2_NIVEL = 5;
    private static final Integer N2_SCORE = 100;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private EntityManager em;

    @Autowired
    private VagaRepository vagaRepository;

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Autowired
    private VagaService vagaService;

    private MockMvc restVagaServiceMockMvc;

    private Vaga vaga;

    private Candidato candidato1;

    private Candidato candidato2;

    private List<Candidatura> candidaturas;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VagaResource vagaResource = new VagaResource(vagaService);
        this.restVagaServiceMockMvc = MockMvcBuilders.standaloneSetup(vagaResource).setMessageConverters(jacksonMessageConverter).build();
    }

    public static Vaga createVaga(EntityManager em) {
        Vaga vaga = new Vaga();
        vaga.setEmpresa(VAGA_EMPRESA);
        vaga.setTitulo(VAGA_TITULO);
        vaga.setDescricao(VAGA_DESCRICAO);
        vaga.setLocalizacao(VAGA_LOCALIZACAO);
        vaga.setNivel(VAGA_NIVEL);
        vaga.setCandidaturas(new ArrayList<Candidatura>());
        return vaga;
    }

    public static List<Candidato> createCandidatos(EntityManager em) {
        List<Candidato> candidatos = new ArrayList<>();
        Candidato candidato1 = new Candidato();
        candidato1.setNome(N1_NOME);
        candidato1.setProfissao(N1_PROFISSAO);
        candidato1.setLocalizacao(N1_LOCALIZACAO);
        candidato1.setNivel(N1_NIVEL);

        Candidato candidato2 = new Candidato();
        candidato2.setNome(N2_NOME);
        candidato2.setProfissao(N2_PROFISSAO);
        candidato2.setLocalizacao(N2_LOCALIZACAO);
        candidato2.setNivel(N2_NIVEL);

        candidatos.add(candidato1);
        candidatos.add(candidato2);
        return candidatos;
    }

    @Before
    public void initTest() {
        vaga = createVaga(em);
        candidato1 = createCandidatos(em).get(0);
        candidato2 = createCandidatos(em).get(1);
    }

    @Test
    @Transactional
    public void efetuarCandidatura() throws Exception {

        vagaRepository.saveAndFlush(vaga);
        candidatoRepository.saveAndFlush(candidato1);

        CandidaturaRequest candidatura = new CandidaturaRequest();
        candidatura.setId_vaga(vagaRepository.findAll().get(0).getId());
        candidatura.setId_candidato(candidatoRepository.findAll().get(0).getId());

        restVagaServiceMockMvc.perform(post("/v1/candidatura")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.convertObjectToJsonBytes(candidatura)))
                .andExpect(status().isOk());

    }

    @Test
    @Transactional
    public void efetuarCandidaturaSemVaga() throws Exception {
        vagaRepository.deleteAll();
        candidatoRepository.saveAndFlush(candidato1);

        CandidaturaRequest candidatura = new CandidaturaRequest();
        candidatura.setId_vaga(100L);
        candidatura.setId_candidato(candidatoRepository.findAll().get(0).getId());

        restVagaServiceMockMvc.perform(post("/v1/candidatura")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.convertObjectToJsonBytes(candidatura)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void efetuarCandidaturaSemCandidato() throws Exception {
        vagaRepository.saveAndFlush(vaga);
        candidatoRepository.deleteAll();

        CandidaturaRequest candidatura = new CandidaturaRequest();
        candidatura.setId_vaga(vagaRepository.findAll().get(0).getId());
        candidatura.setId_candidato(100L);

        restVagaServiceMockMvc.perform(post("/v1/candidatura")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.convertObjectToJsonBytes(candidatura)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void efetuarCandidaturaSemCandidatoEVaga() throws Exception {
        vagaRepository.deleteAll();
        candidatoRepository.deleteAll();

        CandidaturaRequest candidatura = new CandidaturaRequest();
        candidatura.setId_vaga(100L);
        candidatura.setId_candidato(100L);

        restVagaServiceMockMvc.perform(post("/v1/candidatura")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.convertObjectToJsonBytes(candidatura)))
                .andExpect(status().isNotFound());
    }


    @Test
    @Transactional
    public void consultarRankingCandidatos() throws Exception {

        candidatoRepository.saveAndFlush(candidato1);
        candidatoRepository.saveAndFlush(candidato2);

        vaga.getCandidaturas().add(new Candidatura(vaga, candidato1, Score.getScore(vaga, candidato1)));
        vaga.getCandidaturas().add(new Candidatura(vaga, candidato2, Score.getScore(vaga, candidato2)));

        vagaRepository.saveAndFlush(vaga);

        restVagaServiceMockMvc.perform(get("/v1/vagas/{id}/candidaturas/ranking", String.valueOf(vagaRepository.findAll().get(0).getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is(candidatoRepository.findAll().get(1).getNome())))
                .andExpect(jsonPath("$[0].score", is(N2_SCORE)))
                .andExpect(jsonPath("$[1].nome", is(candidatoRepository.findAll().get(0).getNome())))
                .andExpect(jsonPath("$[1].score", is(N1_SCORE)));
    }

}
