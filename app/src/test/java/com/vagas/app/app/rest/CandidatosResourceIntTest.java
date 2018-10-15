package com.vagas.app.app.rest;

import com.vagas.app.app.AppApplication;
import com.vagas.app.app.domain.Candidato;
import com.vagas.app.app.repository.CandidatoRepository;
import com.vagas.app.app.resource.CandidatoResource;
import com.vagas.app.app.service.dto.CandidatoDTO;
import com.vagas.app.app.service.mapper.CandidatoMapper;
import com.vagas.app.app.util.Util;
import com.vagas.app.app.service.CandidatoService;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApplication.class)
public class CandidatosResourceIntTest {

    private static final String DEFAULT_NOME = "Sample Name";
    private static final String DEFAULT_PROFISSAO = "Sample Position";
    private static final String DEFAULT_LOCALIZACAO = "F";
    private static final Integer DEFAULT_NIVEL = 3;

    @Autowired
    private CandidatoRepository repository;

    @Autowired
    private CandidatoService service;

    @Autowired
    private CandidatoMapper mapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private EntityManager em;

    private MockMvc restCandidatoServiceMockMvc;

    private Candidato candidato;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CandidatoResource resource = new CandidatoResource(service);
        this.restCandidatoServiceMockMvc = MockMvcBuilders.standaloneSetup(resource).setMessageConverters(jacksonMessageConverter).build();
    }

    public static Candidato creatyEntity(EntityManager em) {
        Candidato candidato = new Candidato();
        candidato.setNome(DEFAULT_NOME);
        candidato.setProfissao(DEFAULT_PROFISSAO);
        candidato.setLocalizacao(DEFAULT_LOCALIZACAO);
        candidato.setNivel(DEFAULT_NIVEL);

        return candidato;
    }

    @Before
    public void initTest() {
        candidato = creatyEntity(em);
    }

    @Test
    @Transactional
    public void cadastrarCandidato() throws Exception {
        int databaseSizeBeforeCreate = repository.findAll().size();

        CandidatoDTO dto = mapper.toDto(candidato);

        restCandidatoServiceMockMvc.perform(post("/v1/candidato")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.convertObjectToJsonBytes(dto)))
                .andExpect(status().isCreated());

        List<Candidato> candidatos = repository.findAll();
        assertThat(candidatos).hasSize(databaseSizeBeforeCreate + 1);

        Candidato candidatoCadastrado = candidatos.get(candidatos.size() - 1);
        assertThat(candidatoCadastrado.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(candidatoCadastrado.getProfissao()).isEqualTo(DEFAULT_PROFISSAO);
        assertThat(candidatoCadastrado.getLocalizacao()).isEqualTo(DEFAULT_LOCALIZACAO);
        assertThat(candidatoCadastrado.getNivel()).isEqualTo(DEFAULT_NIVEL);
    }

    @Test
    @Transactional
    public void cadastrarCandidatoSemProfissao() throws Exception {
        int databaseSizeBeforeCreate = repository.findAll().size();

        candidato.setProfissao(null);
        CandidatoDTO dto = mapper.toDto(candidato);

        restCandidatoServiceMockMvc.perform(post("/v1/candidato")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.convertObjectToJsonBytes(dto)))
                .andExpect(status().isCreated());

        List<Candidato> candidatos = repository.findAll();
        assertThat(candidatos).hasSize(databaseSizeBeforeCreate + 1);

        Candidato candidatoCadastrado = candidatos.get(candidatos.size() - 1);
        assertThat(candidatoCadastrado.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(candidatoCadastrado.getProfissao()).isNullOrEmpty();
        assertThat(candidatoCadastrado.getLocalizacao()).isEqualTo(DEFAULT_LOCALIZACAO);
        assertThat(candidatoCadastrado.getNivel()).isEqualTo(DEFAULT_NIVEL);
    }

    @Test
    @Transactional
    public void cadastrarCandidatoSemNome() throws Exception {
        candidato.setNome(null);
        CandidatoDTO dto = mapper.toDto(candidato);
        restCandidatoServiceMockMvc.perform(post("/v1/candidato")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.convertObjectToJsonBytes(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void cadastrarCandidatoSemLocalizacao() throws Exception {
        candidato.setLocalizacao(null);
        CandidatoDTO dto = mapper.toDto(candidato);
        restCandidatoServiceMockMvc.perform(post("/v1/candidato")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.convertObjectToJsonBytes(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void cadastrarCandidatoSemNivel() throws Exception {
        candidato.setNivel(null);
        CandidatoDTO dto = mapper.toDto(candidato);
        restCandidatoServiceMockMvc.perform(post("/v1/candidato")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.convertObjectToJsonBytes(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void listarCandidatosCadastrados() throws Exception {
        repository.saveAndFlush(candidato);
        restCandidatoServiceMockMvc.perform(get("/v1/candidatos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
                .andExpect(jsonPath("$.[*].profissao").value(hasItem(DEFAULT_PROFISSAO.toString())))
                .andExpect(jsonPath("$.[*].localizacao").value(hasItem(DEFAULT_LOCALIZACAO.toString())))
                .andExpect(jsonPath("$.[*].nivel").value(hasItem(DEFAULT_NIVEL)));
    }
}
