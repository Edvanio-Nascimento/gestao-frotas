package com.edy.gestao_frotas.service;

import com.edy.gestao_frotas.domain.dto.veiculo.VeiculoRequest;
import com.edy.gestao_frotas.domain.dto.veiculo.VeiculoResponse;
import com.edy.gestao_frotas.domain.dto.veiculo.VeiculoResponseDetails;
import com.edy.gestao_frotas.domain.dto.veiculo.VeiculoUpdate;
import com.edy.gestao_frotas.domain.entity.Veiculo;
import com.edy.gestao_frotas.exception.AnoModeloInvalidoException;
import com.edy.gestao_frotas.exception.PlacaDuplicadaException;
import com.edy.gestao_frotas.exception.VeiculoNaoEncontradoException;
import com.edy.gestao_frotas.repository.VeiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
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

        if (veiculoRepository.existsByPlaca(request.placa().trim().toUpperCase())) {
            throw new PlacaDuplicadaException("Veículo já cadastrado com essa placa: " + request.placa());
        }

        Year anoFab = request.anoFabricacao();
        Year anoMod = request.anoModelo();

        if (!anoMod.equals(anoFab) && !anoMod.equals(anoFab.plusYears(1))) {
            throw new AnoModeloInvalidoException("Ano do modelo deve ser igual ao ano de fabricação ou 1 ano subsequente.");
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

        Veiculo veiculo = veiculoRepository.findByPlaca(placa.toUpperCase().trim())
                .orElseThrow(() -> new VeiculoNaoEncontradoException("Veículo não encontrado com essa placa: " + placa));

        return VeiculoResponseDetails.toResponseDetails(veiculo);

    }

    @Transactional(readOnly = true)
    public VeiculoResponse buscarVeiculoPorId(UUID id) {

        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new VeiculoNaoEncontradoException("Veículo não encontrado com esse id: " + id));

        return VeiculoResponse.toResponse(veiculo);

    }

    @Transactional
    public void excluirVeiculo(UUID id) {

        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new VeiculoNaoEncontradoException("Veículo não encontrado com esse ID: " + id));

        veiculoRepository.delete(veiculo);

    }

    @Transactional
    public void atualizarVeiculo(UUID id, VeiculoUpdate update) {

        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new VeiculoNaoEncontradoException("Veículo não encontrado com esse ID: " + id));

        if (veiculoRepository.existsByPlacaAndIdNot(update.placa().trim().toUpperCase(), id)) {
            throw new PlacaDuplicadaException("A placa fornecida já está cadastrada em outro o ID.");
        }

//        validarData();

        Year anoFab = update.anoFabricacao();
        Year anoMod = update.anoModelo();

        if (!anoMod.equals(anoFab) && !anoMod.equals(anoFab.plusYears(1))) {
            throw new AnoModeloInvalidoException("Ano do modelo deve ser igual ao ano de fabricação ou 1 ano subsequente.");
        }

        veiculo.setMarca(update.marca().toUpperCase().trim());

        veiculo.setModelo(update.modelo().toUpperCase().trim());

        veiculo.setPlaca(update.placa().toUpperCase().trim());

        veiculo.setAnoFabricacao(update.anoFabricacao());

        veiculo.setAnoModelo(update.anoModelo());

    }

    private boolean validarData() {

        Veiculo veiculo = new Veiculo();

        Year anoFab = veiculo.getAnoFabricacao();
        Year anoMod = veiculo.getAnoModelo();

        if (!anoMod.equals(anoFab) && !anoMod.equals(anoFab.plusYears(1))) {
            throw new AnoModeloInvalidoException("Ano do modelo deve ser igual ao ano de fabricação ou 1 ano subsequente.");
        }

        return true;
    }

}
