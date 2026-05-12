package com.portafolio.ordersystem.repository;

import com.portafolio.ordersystem.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);

    // Consulta de agregación para obtener el gasto total del cliente
    @Query("SELECT SUM(p.total) FROM Pedido p WHERE p.cliente.id = :clienteId")
    BigDecimal calcularTotalGastadoPorCliente(@Param("clienteId") Long clienteId);
}