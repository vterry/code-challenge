package com.vagas.app.app.rest;

import com.vagas.app.app.AppApplication;
import com.vagas.app.app.domain.Vaga;
import com.vagas.app.app.repository.VagaRepository;
import com.vagas.app.app.resource.VagaResource;
import com.vagas.app.app.util.Util;
import com.vagas.app.app.service.VagaService;
import com.vagas.app.app.service.dto.VagaDTO;
import com.vagas.app.app.service.mapper.VagaMapper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApplication.class)
public class VagasResourceIntTest {

    private static final String VAGA_EMPRESA = "Teste";
    private static final String VAGA_TITULO = "Vaga Teste";
    private static final String VAGA_DESCRICAO = "Criar os mais diferentes tipos de testes";
    private static final String VAGA_LOCALIZACAO = "A";
    private static final int VAGA_NIVEL = 3;

    @Autowired
    private VagaRepository repository;

    @Autowired
    private VagaService service;

    @Autowired
    private VagaMapper mapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private EntityManager em;

    private MockMvc restVagaServiceMockMvc;

    private Vaga vaga;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VagaResource vagaResource = new VagaResource(service);
        this.restVagaServiceMockMvc = MockMvcBuilders.standaloneSetup(vagaResource).setMessageConverters(jacksonMessageConverter).build();
    }

    public static Vaga createEntity(EntityManager em) {

        Vaga vaga = new Vaga();
        vaga.setEmpresa(VAGA_EMPRESA);
        vaga.setTitulo(VAGA_TITULO);
        vaga.setDescricao(VAGA_DESCRICAO);
        vaga.setLocalizacao(VAGA_LOCALIZACAO);
        vaga.setNivel(VAGA_NIVEL);

        return vaga;
    }

    @Before
    public void initTest() {
        vaga = createEntity(em);
    }

    @Test
    @Transactional
    public void cadastrarVaga() throws Exception {
        int databaseSizeBeforeCreate = repository.findAll().size();

        VagaDTO dto = mapper.toDto(vaga);

        restVagaServiceMockMvc.perform(post("/v1/vaga")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.convertObjectToJsonBytes(dto)))
                .andExpect(status().isCreated());

        List<Vaga> vagas = repository.findAll();
        assertThat(vagas).hasSize(databaseSizeBeforeCreate + 1);

        Vaga vagaCadastrada = vagas.get(vagas.size() - 1);
        assertThat(vagaCadastrada.getEmpresa()).isEqualTo(VAGA_EMPRESA);
        assertThat(vagaCadastrada.getTitulo()).isEqualTo(VAGA_TITULO);
        assertThat(vagaCadastrada.getDescricao()).isEqualTo(VAGA_DESCRICAO);
        assertThat(vagaCadastrada.getLocalizacao()).isEqualTo(VAGA_LOCALIZACAO);
        assertThat(vagaCadastrada.getNivel()).isEqualTo(VAGA_NIVEL);
    }

    @Test
    @Transactional
    public void cadastrarVagaSemEmpresa() throws Exception {
        vaga.setEmpresa(null);
        VagaDTO dto = mapper.toDto(vaga);
        restVagaServiceMockMvc.perform(post("/v1/vaga")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.convertObjectToJsonBytes(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void cadastrarVagaSemTitulo() throws Exception {
        vaga.setTitulo(null);
        VagaDTO dto = mapper.toDto(vaga);
        restVagaServiceMockMvc.perform(post("/v1/vaga")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.convertObjectToJsonBytes(dto)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Transactional
    public void cadastrarVagaSemDescricao() throws Exception {
        vaga.setDescricao(null);
        VagaDTO dto = mapper.toDto(vaga);
        restVagaServiceMockMvc.perform(post("/v1/vaga")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.convertObjectToJsonBytes(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void cadastrarVagaSemLocalizacao() throws Exception {
        vaga.setLocalizacao(null);
        VagaDTO dto = mapper.toDto(vaga);
        restVagaServiceMockMvc.perform(post("/v1/vaga")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.convertObjectToJsonBytes(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void cadastrarVagaSemNivel() throws Exception {
        vaga.setNivel(null);
        VagaDTO dto = mapper.toDto(vaga);
        restVagaServiceMockMvc.perform(post("/v1/vaga")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.convertObjectToJsonBytes(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void cadastrarComNivelInvalido() throws Exception {
        vaga.setNivel(0);
        VagaDTO dto = mapper.toDto(vaga);
        restVagaServiceMockMvc.perform(post("/v1/vaga")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.convertObjectToJsonBytes(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void listarVagasCadastradas() throws Exception {
        repository.saveAndFlush(vaga);
        restVagaServiceMockMvc.perform(get("/v1/vagas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].empresa").value(hasItem(VAGA_EMPRESA.toString())))
                .andExpect(jsonPath("$.[*].titulo").value(hasItem(VAGA_TITULO.toString())))
                .andExpect(jsonPath("$.[*].descricao").value(hasItem(VAGA_DESCRICAO.toString())))
                .andExpect(jsonPath("$.[*].localizacao").value(hasItem(VAGA_LOCALIZACAO.toString())))
                .andExpect(jsonPath("$.[*].nivel").value(hasItem(VAGA_NIVEL)));
    }
}

