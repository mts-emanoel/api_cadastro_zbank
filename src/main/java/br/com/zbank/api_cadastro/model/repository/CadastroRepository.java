package br.com.zbank.api_cadastro.model.repository;

import br.com.zbank.api_cadastro.model.entity.Cadastro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface CadastroRepository extends JpaRepository<Cadastro, Integer>{
    Optional<Cadastro> findCadastroByEmail(@Param("email") String email);
    Optional<Cadastro> findCadastroByCpf(@Param("cpf") String cpf);
}
