package com.senac.cadastro_clientes.service;

import com.senac.cadastro_clientes.dto.usuario.UsuarioMapper;
import com.senac.cadastro_clientes.dto.usuario.UsuarioRequestDom;
import com.senac.cadastro_clientes.dto.usuario.UsuarioResponseDom;
import com.senac.cadastro_clientes.jwt.TokenService;
import com.senac.cadastro_clientes.model.Usuario;
import com.senac.cadastro_clientes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    public UsuarioResponseDom
    criarUsuario(UsuarioRequestDom usuario){

        Usuario usuarioPersist = UsuarioMapper
                .usuarioRequestDomToUsuario(usuario);

        usuarioPersist
                .setSenha(passwordEncoder.encode(usuario.getSenha()));

        Usuario usuarioResult =
                usuarioRepository.save(usuarioPersist);

        return UsuarioMapper
                .usuarioToUsuarioResponseDom(usuarioResult);
    }

    public UsuarioResponseDom
    loginUsuario(UsuarioRequestDom usuario) throws Exception {
        Optional<Usuario> usuarioResult =
                usuarioRepository.findByLogin(usuario.getLogin());
        if(usuarioResult.isPresent()){
            if(passwordEncoder.matches(
                    usuario.getSenha(),
                    usuarioResult.get().getSenha())){

                // gera token jwt
                String token = tokenService
                        .gerarToken(usuarioResult.get());

                UsuarioResponseDom usuarioRetorno = UsuarioMapper
                        .usuarioToUsuarioResponseDom(usuarioResult.get());

                usuarioRetorno.setToken(token);

                return usuarioRetorno;

            }

            throw new Exception("Senha invalida");
        }

        throw new Exception("Usuário não encontrado!");
    }
    public Usuario buscarPorLogin(String login) {
        return usuarioRepository.findByLogin(login).orElse(null);
    }
}
