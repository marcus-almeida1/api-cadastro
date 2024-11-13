package com.senac.cadastro_clientes.dto.usuario;

import com.senac.cadastro_clientes.model.Usuario;

public class UsuarioMapper {

    public static Usuario
    usuarioRequestDomToUsuario(UsuarioRequestDom input){
        Usuario output = new Usuario();
        output.setLogin(input.getLogin());
        output.setSenha(input.getSenha());

        return output;
    }

    public static UsuarioResponseDom
    usuarioToUsuarioResponseDom(Usuario input) {
        UsuarioResponseDom output = new UsuarioResponseDom();
        output.setId(input.getId());
        output.setLogin(input.getLogin());

        return output;
    }
}
