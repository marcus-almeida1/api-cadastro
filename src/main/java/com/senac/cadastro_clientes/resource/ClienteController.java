package com.senac.cadastro_clientes.resource;

import com.senac.cadastro_clientes.dto.cliente.ClienteRequestDom;
import com.senac.cadastro_clientes.model.Cliente;
import com.senac.cadastro_clientes.repository.ClienteRepository;
import com.senac.cadastro_clientes.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> salvar(@RequestBody ClienteRequestDom cliente) {
        try {
            return ResponseEntity.ok(clienteService.salvar(cliente));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro não mapeado: " + e.getMessage());
        }
    }
    @GetMapping("/listar")
    public ResponseEntity<List<Cliente>> buscarTodosClientes() {
            List<Cliente> clientes = clienteRepository.findAll();
            return ResponseEntity.ok(clientes);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarCliente(@PathVariable("id") Long id, @RequestBody ClienteRequestDom cliente) {
        try {
            return ResponseEntity.ok(clienteService.atualizarCliente(id, cliente));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro não mapeado: " + e.getMessage());
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> remover(@PathVariable("id") Long id) {
        try {
            clienteService.remover(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro não mapeado: " + e.getMessage());
        }
        return null;
    }
}
