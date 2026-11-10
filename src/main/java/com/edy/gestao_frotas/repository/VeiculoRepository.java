package com.edy.gestao_frotas.repository;

import com.edy.gestao_frotas.domain.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VeiculoRepository extends JpaRepository<Veiculo, UUID> {

    Optional<Veiculo> findByPlaca(String placa);

    Boolean existsByPlaca(String placa);

    Boolean existsByPlacaAndIdNot(String placa, UUID id);

}
