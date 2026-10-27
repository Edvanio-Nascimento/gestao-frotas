package com.edy.gestao_frotas.controller;

import com.edy.gestao_frotas.domain.dto.veiculo.VeiculoRequest;
import com.edy.gestao_frotas.domain.dto.veiculo.VeiculoResponse;
import com.edy.gestao_frotas.domain.dto.veiculo.VeiculoResponseDetails;
import com.edy.gestao_frotas.domain.dto.veiculo.VeiculoUpdate;
import com.edy.gestao_frotas.service.VeiculoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @PostMapping
    public ResponseEntity<VeiculoResponse> criarVeiculo(@RequestBody @Valid VeiculoRequest request) {

        VeiculoResponse veiculo = veiculoService.salvarVeiculo(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(veiculo.id())
                .toUri();

        return ResponseEntity.created(location).body(veiculo);

    }

    @GetMapping
    public ResponseEntity<List<VeiculoResponse>> buscarVeiculos() {

        return ResponseEntity.ok(veiculoService.listarVeiculos());

    }

    @GetMapping("/{placa}")
    public ResponseEntity<VeiculoResponseDetails> buscarVeiculoPelaPlaca(@PathVariable String placa) {

        return ResponseEntity.ok(veiculoService.buscarVeiculoPelaPlaca(placa));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirVeiculo(@PathVariable UUID id) {

        veiculoService.excluirVeiculo(id);
        return ResponseEntity.noContent().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarVeiculo(@PathVariable UUID id, @RequestBody @Valid VeiculoUpdate update) {

        veiculoService.atualizarVeiculo(id, update);

        return ResponseEntity.noContent().build();

    }

}
