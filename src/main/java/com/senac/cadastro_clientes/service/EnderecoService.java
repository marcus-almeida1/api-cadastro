package com.senac.cadastro_clientes.service;

import com.senac.cadastro_clientes.dto.endereco.EnderecoMapper;
import com.senac.cadastro_clientes.dto.endereco.EnderecoRequestDom;
import com.senac.cadastro_clientes.dto.endereco.EnderecoResponseDom;
import com.senac.cadastro_clientes.model.Cliente;
import com.senac.cadastro_clientes.model.Endereco;
import com.senac.cadastro_clientes.repository.ClienteRepository;
import com.senac.cadastro_clientes.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public EnderecoResponseDom salvar(EnderecoRequestDom endereco) throws Exception {

        Endereco enderecoPersist = EnderecoMapper.enderecoRequestDomToEndereco(endereco);

        Cliente cliente = clienteRepository.findById(enderecoPersist.getCliente().getId())
                .orElseThrow(() -> new Exception("Cliente não encontrado"));

        //Validação de campos obrigatórios
        if (    enderecoPersist.getRua() == null || enderecoPersist.getRua().isEmpty() ||
                enderecoPersist.getBairro() == null || enderecoPersist.getBairro().isEmpty() ||
                enderecoPersist.getCidade() == null || enderecoPersist.getCidade().isEmpty() ||
                enderecoPersist.getUf() == null || enderecoPersist.getUf().isEmpty()) {
            throw new Exception("Todos os campos de endereço (Rua, Bairro, Cidade, UF) devem ser informados.");
        }

        // Validação do limite de endereços para o cliente (máximo de 5 endereços)
        Integer limiteEndereco = enderecoRepository.countClienteById(cliente.getId());
        if (limiteEndereco >= 5) {
            throw new Exception("O cliente já possui o limite máximo de 5 endereços.");
        }

        //Setar data de cadastro
        enderecoPersist.setDataCadastro(LocalDateTime.now());

        Endereco clienteResult = enderecoRepository.save(enderecoPersist);

        return EnderecoMapper.enderecoToEnderecoResponseDom(clienteResult);
    }

    public EnderecoResponseDom atualizarEndereco(Long id, EnderecoRequestDom alterado) throws Exception {

        Optional<Endereco> encontrado = enderecoRepository.findById(id);

        if (encontrado.isPresent()) {

            Endereco enderecoPersist = encontrado.get();

            enderecoPersist.setRua(alterado.getRua());
            enderecoPersist.setBairro(alterado.getBairro());
            enderecoPersist.setCidade(alterado.getCidade());
            enderecoPersist.setUf(alterado.getUf());

            Endereco enderecoResult = enderecoRepository.save(enderecoPersist);

            return EnderecoMapper.enderecoToEnderecoResponseDom(enderecoResult);
        }
        throw new Exception("Cliente não encontrado com id: " + id);
    }

    public void remover (Long id){
        enderecoRepository.deleteById(id);
    }
}
