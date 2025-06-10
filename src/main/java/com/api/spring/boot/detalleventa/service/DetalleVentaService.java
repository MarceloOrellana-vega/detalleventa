package com.api.spring.boot.detalleventa.service;

import com.api.spring.boot.detalleventa.model.DetalleVenta;
import com.api.spring.boot.detalleventa.repository.DetalleVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleVentaService {

    @Autowired
    private DetalleVentaRepository repo;

    public List<DetalleVenta> listar() {
        return repo.findAll();
    }

    public DetalleVenta guardar(DetalleVenta detalle) {
        return repo.save(detalle);
    }

    public DetalleVenta obtenerPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    public List<DetalleVenta> listarPorVenta(Integer idVenta) {
        return repo.findByIdVenta(idVenta);
    }
}
