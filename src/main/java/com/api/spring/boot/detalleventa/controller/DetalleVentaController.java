package com.api.spring.boot.detalleventa.controller;

import com.api.spring.boot.detalleventa.model.DetalleVenta;
import com.api.spring.boot.detalleventa.service.DetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detalles")
public class DetalleVentaController {

    @Autowired
    private DetalleVentaService service;

    @GetMapping
    public List<DetalleVenta> listar() {
        return service.listar();
    }

    @PostMapping
    public DetalleVenta crear(@RequestBody DetalleVenta detalle) {
        return service.guardar(detalle);
    }

    @GetMapping("/{id}")
    public DetalleVenta obtener(@PathVariable Integer id) {
        return service.obtenerPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }

    @GetMapping("/venta/{idVenta}")
    public List<DetalleVenta> listarPorVenta(@PathVariable Integer idVenta) {
        return service.listarPorVenta(idVenta);
    }
}
