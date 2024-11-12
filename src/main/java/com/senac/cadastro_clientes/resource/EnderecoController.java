package com.senac.cadastro_clientes.resource;

import com.senac.cadastro_clientes.dto.endereco.EnderecoRequestDom;
import com.senac.cadastro_clientes.model.Endereco;
import com.senac.cadastro_clientes.repository.EnderecoRepository;
import com.senac.cadastro_clientes.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> salvar(@RequestBody EnderecoRequestDom endereco) {
        try {
            return ResponseEntity.ok(enderecoService.salvar(endereco));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro não mapeado: " + e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Endereco>> buscarTodosEnderecos() {
        List<Endereco> enderecos = enderecoRepository.findAll();
        return ResponseEntity.ok(enderecos);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarEndereco(@PathVariable("id") Long id, @RequestBody EnderecoRequestDom endereco) {
        try {
            return ResponseEntity.ok(enderecoService.atualizarEndereco(id, endereco));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro não mapeado: " + e.getMessage());
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> remover(@PathVariable("id") Long id) {
        try {
            enderecoService.remover(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro não mapeado: " + e.getMessage());
        }
        return null;
    }
}
