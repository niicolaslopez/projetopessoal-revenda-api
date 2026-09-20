package com.nicolas.revenda.controller;

import com.nicolas.revenda.dto.*;
import com.nicolas.revenda.model.Usuario;
import com.nicolas.revenda.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> criar(@RequestBody CriarUsuarioRequest request) {
        Usuario novoUsuario = usuarioService.criar(
                request.nome(),
                request.email(),
                request.senha(),
                request.role()
        );
        return ResponseEntity.ok(UsuarioResponse.from(novoUsuario));
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioResponse>> listar(Pageable pageable) {
        Page<Usuario> pagina = usuarioService.listar(pageable);
        Page<UsuarioResponse> paginaConvertida = pagina.map(UsuarioResponse::from);
        return ResponseEntity.ok(paginaConvertida);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioResponse.from(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable Long id, @RequestBody AtualizarUsuarioRequest request) {
        Usuario novoUsuario = usuarioService.atualizar(
                id,
                request.nome(),
                request.email()
        );
        return ResponseEntity.ok(UsuarioResponse.from(novoUsuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioResponse> arquivar(@PathVariable Long id) {
        Usuario usuario = usuarioService.arquivar(id);
        return ResponseEntity.ok(UsuarioResponse.from(usuario));
    }
}