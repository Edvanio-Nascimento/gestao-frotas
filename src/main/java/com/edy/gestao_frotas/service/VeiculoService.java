package com.edy.gestao_frotas.service;

import com.edy.gestao_frotas.domain.dto.veiculo.VeiculoRequest;
import com.edy.gestao_frotas.domain.dto.veiculo.VeiculoResponse;
import com.edy.gestao_frotas.domain.dto.veiculo.VeiculoResponseDetails;
import com.edy.gestao_frotas.domain.dto.veiculo.VeiculoUpdate;
import com.edy.gestao_frotas.domain.entity.Veiculo;
import com.edy.gestao_frotas.repository.VeiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    public VeiculoService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    @Transactional
    public VeiculoResponse salvarVeiculo(VeiculoRequest request) {

        if (veiculoRepository.existsByPlaca(request.placa())) {
            throw new RuntimeException("Veículo já cadastrado com essa placa: " + request.placa());
        }

        Veiculo veiculo = request.toEntiy();

        veiculoRepository.save(veiculo);

        return VeiculoResponse.toResponse(veiculo);

    }

    @Transactional(readOnly = true)
    public List<VeiculoResponse> listarVeiculos() {

        return veiculoRepository.findAll().stream().map(VeiculoResponse::toResponse).toList();

    }

    @Transactional(readOnly = true)
    public VeiculoResponseDetails buscarVeiculoPelaPlaca(String placa) {

        Veiculo veiculo = veiculoRepository.findByPlacaContainingIgnoreCase(placa)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado com essa placa: " + placa));

        return VeiculoResponseDetails.toResponseDetails(veiculo);

    }

    @Transactional
    public void excluirVeiculo(UUID id) {

        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado com esse ID: " + id));

        veiculoRepository.delete(veiculo);

    }

    @Transactional
    public void atualizarVeiculo(UUID id, VeiculoUpdate update) {

        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado com esse ID: " + id));

        veiculo.setMarca(update.marca());

        veiculo.setModelo(update.modelo());

        veiculo.setPlaca(update.placa());

        veiculo.setAnoFabricacao(update.anoFabricacao());

        veiculo.setAnoModelo(update.anoModelo());


        veiculo = veiculoRepository.saveAndFlush(veiculo);

    }
}
