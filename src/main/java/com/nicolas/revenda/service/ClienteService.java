package com.nicolas.revenda.service;


import com.nicolas.revenda.exception.RecursoNaoEncontradoException;
import com.nicolas.revenda.model.Cliente;
import com.nicolas.revenda.model.StatusCliente;
import com.nicolas.revenda.repository.ClienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Page<Cliente> listar(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }

    public Page<Cliente> listarPorStatus(StatusCliente status, Pageable pageable) {
        return clienteRepository.findByStatus(status, pageable);
    }

    public Cliente buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado"));
        return cliente;
    }

    public Cliente arquivar(Long id) {
        Cliente cliente = buscarPorId(id);
        cliente.setStatus(StatusCliente.ARQUIVADO);
        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Long id, String nome, String cpf, String telefone, String email, String cidade) {

        Cliente cliente = buscarPorId(id);
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setTelefone(telefone);
        cliente.setEmail(email);
        cliente.setCidade(cidade);

        return clienteRepository.save(cliente);
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
        cliente.setStatus(StatusCliente.ATIVO);

        return clienteRepository.save(cliente);
    }
}
