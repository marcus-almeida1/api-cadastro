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
import java.util.List;
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

        List<Endereco> enderecos = cliente.getEnderecos();
        Integer limiteEndereco = enderecos.size();

        // Valida o limite de 5 endereços
        if (limiteEndereco >= 5) {
            throw new Exception("O cliente já possui o limite máximo de 5 endereços.");
        }

        //Validação de campos obrigatórios
        if (    enderecoPersist.getRua().isBlank() || enderecoPersist.getRua().isEmpty() ||
                enderecoPersist.getBairro().isBlank()  || enderecoPersist.getBairro().isEmpty() ||
                enderecoPersist.getCidade().isBlank()  || enderecoPersist.getCidade().isEmpty() ||
                enderecoPersist.getUf().isBlank()  || enderecoPersist.getUf().isEmpty()) {
            throw new Exception("Os campos (Rua, Bairro, Cidade, UF) devem ser informados.");
        }

        // Setar data de cadastro
        enderecoPersist.setDataCadastro(LocalDateTime.now());

        Endereco enderecoResult = enderecoRepository.save(enderecoPersist);

        return EnderecoMapper.enderecoToEnderecoResponseDom(enderecoResult);
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
