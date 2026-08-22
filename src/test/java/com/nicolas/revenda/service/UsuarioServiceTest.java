package com.nicolas.revenda.service;

import com.nicolas.revenda.model.Role;
import com.nicolas.revenda.model.Usuario;
import com.nicolas.revenda.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void deveRejeitarEmailDuplicado() {
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setEmail("existente@teste.com");

        when(usuarioRepository.findByEmail("existente@teste.com"))
                .thenReturn(Optional.of(usuarioExistente));

        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.criar("Nome Teste", "existente@teste.com", "123456", Role.VENDEDOR);
        });
    }
}