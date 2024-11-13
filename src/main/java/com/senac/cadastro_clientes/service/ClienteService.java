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

        Cliente clienteExistente = clienteRepository.findByEmail(clientePersist.getEmail());

        //Validação campos obrigatórios
        if (    clientePersist.getNome().isBlank() || clientePersist.getNome().isEmpty() ||
                clientePersist.getSobrenome().isBlank() || clientePersist.getSobrenome().isEmpty() ||
                clientePersist.getEmail().isBlank() || clientePersist.getEmail().isEmpty()) {
            throw new Exception("Os campos (Nome, Sobrenome e E-mail) devem ser informados.");
        }

        //Validação de e-mail já cadastrado
        if (clienteExistente != null) {
            throw new Exception("E-mail já cadastrado.");
        }

        //Validação de sexo
        if (!"masculino".equals(clientePersist.getSexo()) && !"feminino".equals(clientePersist.getSexo())) {
            clientePersist.setSexo("Não Informado");
        }

        //Setar data de cadastro
        clientePersist.setDataCadastro(LocalDateTime.now());

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
