package com.vagas.app.app.resource;

import com.vagas.app.app.service.CandidatoService;
import com.vagas.app.app.service.dto.CandidatoDTO;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CandidatoResource {

    private CandidatoService service;

    private JSONObject response = new JSONObject();

    public CandidatoResource(CandidatoService service) {
        this.service = service;
    }

    @PostMapping("/v1/candidato")
    public ResponseEntity<JSONObject> cadastrarCandidato(@RequestBody @Valid CandidatoDTO candidatoDTO) {
        CandidatoDTO candidato = service.save(candidatoDTO);
        response.clear();
        response.put("mensagem", "Candidato registrado com sucesso!");
        response.put("identificador", candidato.getId());
        response.put("nome", candidato.getNome());
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/v1/candidatos")
    public ResponseEntity<List<CandidatoDTO>> listarCandidatos() {
        return ResponseEntity.ok(service.findAll());
    }

}
