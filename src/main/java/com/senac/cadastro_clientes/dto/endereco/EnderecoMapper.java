package com.senac.cadastro_clientes.dto.endereco;

import com.senac.cadastro_clientes.model.Endereco;

public class EnderecoMapper {

        public static Endereco
        enderecoRequestDomToEndereco(EnderecoRequestDom input) {
            Endereco output = new Endereco();

            output.setRua(input.getRua());
            output.setBairro(input.getBairro());
            output.setCidade(input.getCidade());
            output.setUf(input.getUf());
            output.setCliente(input.getCliente());

            return output;
        }

        public static EnderecoResponseDom
        enderecoToEnderecoResponseDom(Endereco input) {
            EnderecoResponseDom output = new EnderecoResponseDom();

            output.setId(input.getId());
            output.setRua(input.getRua());
            output.setBairro(input.getBairro());
            output.setCidade(input.getCidade());
            output.setUf(input.getUf());
            output.setCliente(input.getCliente());
            output.setDataCadastro(input.getDataCadastro());

            return output;
        }
}
