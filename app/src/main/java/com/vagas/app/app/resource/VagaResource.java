package com.vagas.app.app.resource;

import com.vagas.app.app.resource.errors.RecordNotFoundException;
import com.vagas.app.app.service.VagaService;
import com.vagas.app.app.service.dto.CandidatoDTO;
import com.vagas.app.app.resource.wrapper.CandidaturaRequest;
import com.vagas.app.app.service.dto.VagaDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(value = "vagas.com", description = "Permite cadastrar novas vagas, realizar a listagemd e vagas cadastradas, " +
             "realizar candidaturas e rankear os candidatos.")
public class VagaResource {

    private VagaService service;

    private JSONObject response = new JSONObject();

    public VagaResource(VagaService service) {
        this.service = service;
    }

    @ApiOperation("Cadastrar novas vagas.")
    @PostMapping(value = "/v1/vaga")
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

    @ApiOperation("Efetua a candidatura de um candidato em uma vaga existente.")
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


    @ApiOperation("Listar vagas cadastradas.")
    @GetMapping("/v1/vagas")
    public ResponseEntity<List<VagaDTO>> listarVagas() {
        return ResponseEntity.ok(service.findAll());
    }

    @ApiOperation("Lista o ranking de candidatos para uma vaga especifica.")
    @RequestMapping(value = "/v1/vagas/{id}/candidaturas/ranking", method = RequestMethod.GET)
    public ResponseEntity<List<CandidatoDTO>> listarCandidaturas(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.listarCandidaturas(id));
    }

}
