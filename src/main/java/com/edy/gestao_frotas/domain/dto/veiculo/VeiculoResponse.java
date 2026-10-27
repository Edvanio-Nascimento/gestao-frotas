package com.edy.gestao_frotas.domain.dto.veiculo;

import com.edy.gestao_frotas.domain.entity.Veiculo;

import java.time.Year;
import java.util.UUID;

public record VeiculoResponse(

        UUID id,

        String marca,

        String modelo,

        String placa,

        Year anoFabricacao

) {

    public static VeiculoResponse toResponse(Veiculo veiculo) {

        return new VeiculoResponse(
                veiculo.getId(),
                veiculo.getMarca(),
                veiculo.getModelo(),
                veiculo.getPlaca(),
                veiculo.getAnoFabricacao()
        );

    }

}
