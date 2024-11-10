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

        Cliente clienteResult = clienteRepository.save(clientePersist);

        if (clientePersist.getNome() == null) {
            throw new Exception("O nome deve ser informado.");
        }

        if (clientePersist.getSobrenome() == null) {
            throw new Exception("O sobrenome deve ser informado.");
        }

        if (clientePersist.getEmail() == null) {
            throw new Exception("O email é obrigatório e deve ser único.");
        }

        if (clientePersist.getSexo() != "masculino" && clientePersist.getSexo() != "feminino") {
            clienteResult.setSexo("Não Informado");
        }

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
