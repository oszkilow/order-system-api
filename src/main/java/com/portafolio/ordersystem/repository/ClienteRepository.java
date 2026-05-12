package com.portafolio.ordersystem.repository;

import com.portafolio.ordersystem.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // 1. Para la validación de duplicados en el Service
    boolean existsByEmail(String email);

    // 2. Para la paginación eficiente en el listado global
    Page<Cliente> findAllByOrderByIdAsc(Pageable pageable);

    // 3. LA JOYA DE LA CORONA: Carga eficiente de Cliente + Pedidos
    // Usamos "JOIN FETCH" para traer todo en una sola consulta SQL y evitar el problema N+1
    @Query("SELECT c FROM Cliente c LEFT JOIN FETCH c.pedidos WHERE c.id = :id")
    Optional<Cliente> findByIdWithPedidos(@Param("id") Long id);

    // 4. Búsqueda por nombre (opcional, muy útil para filtros)
    Page<Cliente> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}