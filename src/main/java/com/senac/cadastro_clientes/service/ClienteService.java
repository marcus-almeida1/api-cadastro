package com.senac.cadastro_clientes.service;

import com.senac.cadastro_clientes.dto.cliente.ClienteMapper;
import com.senac.cadastro_clientes.dto.cliente.ClienteRequestDom;
import com.senac.cadastro_clientes.dto.cliente.ClienteResponseDom;
import com.senac.cadastro_clientes.model.Cliente;
import com.senac.cadastro_clientes.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteResponseDom salvar(ClienteRequestDom cliente) throws Exception {

        Cliente clientePersist = ClienteMapper.clienteRequestDomToCliente(cliente);

        clientePersist.setDataCadastro(LocalDateTime.now());

        //Validação de nome
        if (clientePersist.getNome() == null || clientePersist.getNome().isEmpty()) {
            throw new Exception("O nome deve ser informado.");
        }

        //Validação de sobrenome
        if (clientePersist.getSobrenome() == null ||clientePersist.getSobrenome().isEmpty()) {
            throw new Exception("O sobrenome deve ser informado.");
        }

        //Validação de e-mail único
        if (clientePersist.getEmail() == null || clientePersist.getEmail().isEmpty()) {
            throw new Exception("O email é obrigatório e deve ser único.");
        }

        //Validação de sexo
        if (!"masculino".equals(clientePersist.getSexo()) && !"feminino".equals(clientePersist.getSexo())) {
            clientePersist.setSexo("Não Informado");
        }

        Cliente clienteResult = clienteRepository.save(clientePersist);

        return ClienteMapper.clienteToClienteResponseDom(clienteResult);
    }

    public ClienteResponseDom atualizarCliente(Long id, ClienteRequestDom alterado) throws Exception {

        Optional<Cliente> encontrado = clienteRepository.findById(id);

        if (encontrado.isPresent()) {

            Cliente clientePersist = encontrado.get();

            clientePersist.setEmail(alterado.getEmail());

            Cliente clientResult = clienteRepository.save(clientePersist);

            return ClienteMapper.clienteToClienteResponseDom(clientResult);
        }
        throw new Exception("Cliente não encontrado com id: " + id);
    }

    public void remover (Long id){

        clienteRepository.deleteById(id);
    }
}
