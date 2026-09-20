package com.nicolas.revenda.service;

import com.nicolas.revenda.exception.RecursoNaoEncontradoException;
import com.nicolas.revenda.model.*;
import com.nicolas.revenda.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario criar(String nome, String email, String senhaPura, Role role){
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(senhaPura));
        usuario.setRole(role);
        usuario.setStatus(StatusUsuario.ATIVO);

        return usuarioRepository.save(usuario);
    }

    public Page<Usuario> listar(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    public Usuario buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuario não encontrado"));
        return usuario;
    }

    public Usuario arquivar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setStatus(StatusUsuario.ARQUIVADO);
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, String nome, String email) {
        Usuario usuario = buscarPorId(id);
        usuario.setNome(nome);
        usuario.setEmail(email);

        return usuarioRepository.save(usuario);
    }
}
