package com.edy.gestao_frotas.repository;

import com.edy.gestao_frotas.domain.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RevisaoRepository extends JpaRepository<Veiculo, UUID> {

}
