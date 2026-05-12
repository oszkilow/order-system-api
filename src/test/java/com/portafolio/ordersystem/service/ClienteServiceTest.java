package com.portafolio.ordersystem.service;

import com.portafolio.ordersystem.dto.ClienteCreateDTO;
import com.portafolio.ordersystem.dto.ClienteDTO;
import com.portafolio.ordersystem.entity.Cliente; // <--- IMPORTANTE: dice .entity
import com.portafolio.ordersystem.repository.ClienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    @DisplayName("Debe guardar un cliente exitosamente")
    void guardarTest() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Oscar Fuentes");
        cliente.setEmail("oscar@test.com");

        ClienteCreateDTO dto = new ClienteCreateDTO();
        dto.setNombre("Oscar Fuentes");
        dto.setEmail("oscar@test.com");

        when(clienteRepository.existsByEmail(anyString())).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // Act
        ClienteDTO resultado = clienteService.guardar(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals("Oscar Fuentes", resultado.getNombre());
        verify(clienteRepository).save(any(Cliente.class));
    }
}