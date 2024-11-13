package com.senac.cadastro_clientes.resource;

import com.senac.cadastro_clientes.dto.usuario.UsuarioRequestDom;
import com.senac.cadastro_clientes.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?>
    cadastrarUsuario(@RequestBody UsuarioRequestDom usuario) {

        try {
            return ResponseEntity
                    .ok(usuarioService.criarUsuario(usuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro não mapeado: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioRequestDom usuario) {
        try {
            return ResponseEntity.ok(usuarioService.loginUsuario(usuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro não mapeado: " + e.getMessage());
        }
    }
}