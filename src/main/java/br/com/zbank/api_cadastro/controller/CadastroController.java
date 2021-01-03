package br.com.zbank.api_cadastro.controller;

import br.com.zbank.api_cadastro.dto.CadastroDTO;
import br.com.zbank.api_cadastro.model.entity.Cadastro;
import br.com.zbank.api_cadastro.model.service.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("cadastro")
public class CadastroController {
    @Autowired
    CadastroService cadastroService;

    @PostMapping
    public ResponseEntity<Cadastro> save(@RequestBody CadastroDTO cadastroDTO) {
        Cadastro cadastro = new Cadastro();
        try{
            cadastro.setNome(cadastroDTO.getNome());
            cadastro.setEmail(cadastroDTO.getEmail());
            cadastro.setCpf(cadastroDTO.getCpf());
            cadastro.setNascimento(cadastroDTO.getNascimento());
        }catch (IllegalArgumentException e){
            //Retorno com Status de Erro na requisição junto com a mensagem informativa
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        //Verificação caso CPF já esteja cadastrado
        Optional<Cadastro> c;
        c = cadastroService.findByCpf(cadastro.getCpf());
        if(c.isPresent())
            return new ResponseEntity<Cadastro>(c.get(), HttpStatus.OK);

        //Verificação caso Email já esteja cadastrado
        c = cadastroService.findByEmail(cadastro.getEmail());
        if(c.isPresent())
            return new ResponseEntity<Cadastro>(c.get(), HttpStatus.OK);

        //Salva o cadastro e retorna a resposta da requisição
        cadastro = cadastroService.save(cadastro);
        return new ResponseEntity<Cadastro>(cadastro, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Cadastro> showAll(){
        return cadastroService.showAll();
    }

}
