package com.senac.cadastro_clientes.dto.cliente;

import com.senac.cadastro_clientes.model.Cliente;
import com.senac.cadastro_clientes.model.Endereco;

public class ClienteMapper {

    public static Cliente
    clienteRequestDomToCliente(ClienteRequestDom input) {
        Cliente output = new Cliente();

        output.setNome(input.getNome());
        output.setSobrenome(input.getSobrenome());
        output.setEmail(input.getEmail());
        output.setSexo(input.getSexo());
        output.setDataNascimento(input.getDataNascimento());

        return output;
    }

    public static ClienteResponseDom
    clienteToClienteResponseDom(Cliente input) {

        ClienteResponseDom output = new ClienteResponseDom();

        output.setId(input.getId());
        output.setNome(input.getNome());
        output.setSobrenome(input.getSobrenome());
        output.setEmail(input.getEmail());
        output.setSexo(input.getSexo());
        output.setDataNascimento(input.getDataNascimento());
        output.setDataCadastro(input.getDataCadastro());

        return output;
    }
}
