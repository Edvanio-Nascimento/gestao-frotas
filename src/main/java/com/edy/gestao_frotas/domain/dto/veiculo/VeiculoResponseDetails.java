package com.edy.gestao_frotas.domain.dto.veiculo;

import com.edy.gestao_frotas.domain.entity.Veiculo;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.UUID;

public record VeiculoResponseDetails(

        UUID id,

        String marca,

        String modelo,

        String placa,

        Year anoFabricacao,

        Year anoModelo,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {

    public static VeiculoResponseDetails toResponseDetails(Veiculo veiculo) {

        return new VeiculoResponseDetails(
                veiculo.getId(),
                veiculo.getMarca(),
                veiculo.getModelo(),
                veiculo.getPlaca(),
                veiculo.getAnoFabricacao(),
                veiculo.getAnoModelo(),
                veiculo.getCreatedAt(),
                veiculo.getUpdatedAt()
        );

    }

}
