package com.edy.gestao_frotas.domain.dto.veiculo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.Year;

public record VeiculoUpdate(

        @NotBlank(message = "O campo é obrigatório.")
        @Size(max = 50, message = "O campo marca deve ter no máximo 50 caracteres.")
        String marca,

        @NotBlank(message = "O campo é obrigatório.")
        @Size(max = 50, message = "O campo modelo deve ter no máximo 50 caracteres.")
        String modelo,

        @NotBlank(message = "O campo é obrigatório.")
        @Size(max = 50, message = "O campo placa deve ter no máximo 10 caracteres.")
        String placa,

        @NotNull(message = "O campo ano de fabricação é obrigatŕoio.")
        @PastOrPresent(message = "Ano deve ser um no passado ou atual.")
        Year anoFabricacao,

        @NotNull(message = "O campo ano do modelo é obrigatŕoio.")
        @PastOrPresent(message = "Ano deve ser um no passado ou atual.")
        Year anoModelo

) {

}
