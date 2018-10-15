package com.vagas.app.app.resource;

import com.vagas.app.app.domain.Vaga;
import com.vagas.app.app.resource.errors.RecordNotFoundException;
import com.vagas.app.app.service.VagaService;
import com.vagas.app.app.service.dto.CandidatoDTO;
import com.vagas.app.app.resource.wrapper.CandidaturaRequest;
import com.vagas.app.app.service.dto.VagaDTO;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class VagaResource {

    private VagaService service;

    private JSONObject response = new JSONObject();

    public VagaResource(VagaService service) {
        this.service = service;
    }

    @PostMapping(value = "/v1/vaga", produces = "application/json")
    @ResponseBody
    public ResponseEntity<JSONObject> cadastrarVagas(@RequestBody @Valid VagaDTO vagaDTO) {
        VagaDTO vaga = service.save(vagaDTO);
        response.clear();
        response.put("mensagem", "Vaga cadastrada com sucesso!");
        response.put("identificador", vaga.getId());
        response.put("titulo", vaga.getTitulo());
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping("/v1/candidatura")
    public ResponseEntity<JSONObject> efetuarCandidatura(@RequestBody CandidaturaRequest candidaturaDTO) {
        response.clear();
        try {
            response.put("mensagem", "Candidatura efetuada com sucesso!");
            service.saveNewApplication(candidaturaDTO.getId_vaga(), candidaturaDTO.getId_candidato());
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } catch (Exception e) {
            throw new RecordNotFoundException("Vaga ou candidato não encontrado!");
        }
    }


    @GetMapping("/v1/vagas")
    public ResponseEntity<List<VagaDTO>> listarVagas() {
        return ResponseEntity.ok(service.findAll());
    }

    @RequestMapping(value = "/v1/vagas/{id}/candidaturas/ranking", method = RequestMethod.GET)
    public ResponseEntity<List<CandidatoDTO>> listarCandidaturas(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.listarCandidaturas(id));
    }

}
