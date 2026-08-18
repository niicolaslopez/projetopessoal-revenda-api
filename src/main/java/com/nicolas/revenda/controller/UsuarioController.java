package com.nicolas.revenda.controller;

import com.nicolas.revenda.dto.CriarUsuarioRequest;
import com.nicolas.revenda.model.Usuario;
import com.nicolas.revenda.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody CriarUsuarioRequest request) {
        Usuario novoUsuario = usuarioService.criar(
                request.nome(),
                request.email(),
                request.senha(),
                request.role()
        );
        return ResponseEntity.ok(novoUsuario);
    }
}
