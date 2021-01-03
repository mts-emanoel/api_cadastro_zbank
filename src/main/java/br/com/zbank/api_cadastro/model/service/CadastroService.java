package br.com.zbank.api_cadastro.model.service;

import br.com.zbank.api_cadastro.model.entity.Cadastro;
import br.com.zbank.api_cadastro.model.repository.CadastroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroService {

    @Autowired
    CadastroRepository cadastroRepository;

    public Cadastro save(Cadastro cadastro){
        return cadastroRepository.save(cadastro);
    }

    public List<Cadastro> showAll(){
        return cadastroRepository.findAll();
    }

    public Optional<Cadastro> findByEmail(String email){
        return cadastroRepository.findCadastroByEmail(email);
    }

    public Optional<Cadastro> findByCpf(String cpf){
        return cadastroRepository.findCadastroByCpf(cpf);
    }

}
