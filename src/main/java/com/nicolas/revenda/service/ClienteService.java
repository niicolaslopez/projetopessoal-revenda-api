package com.nicolas.revenda.service;


import com.nicolas.revenda.model.Cliente;
import com.nicolas.revenda.repository.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente criar(String nome, String cpf, String telefone, String email, String cidade) {
        if (clienteRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        if (clienteRepository.findByCpf(cpf).isPresent()){
            throw new IllegalArgumentException("CPF já cadastrado");
        }

        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setTelefone(telefone);
        cliente.setEmail(email);
        cliente.setCidade(cidade);

        return clienteRepository.save(cliente);
    }
}
