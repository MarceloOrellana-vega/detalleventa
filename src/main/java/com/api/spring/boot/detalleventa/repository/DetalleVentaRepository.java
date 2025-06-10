package com.api.spring.boot.detalleventa.repository;

import com.api.spring.boot.detalleventa.model.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Integer> {
    List<DetalleVenta> findByIdVenta(Integer idVenta); // Buscar detalles por id de venta
}
