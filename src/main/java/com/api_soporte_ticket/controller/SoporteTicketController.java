package com.api_soporte_ticket.controller;

import com.api_soporte_ticket.models.SoporteTicket;
import com.api_soporte_ticket.service.SoporteTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/soporte/tickets")
public class SoporteTicketController {

    @Autowired
    private SoporteTicketService service;

    // Crear un ticket
    @PostMapping
    public ResponseEntity<SoporteTicket> crearTicket(@RequestBody SoporteTicket ticket) {
        SoporteTicket creado = service.crearTicket(ticket);
        return ResponseEntity.status(201).body(creado);
    }

    // Listar tickets de un cliente por idUsuario
    @GetMapping("/cliente/{idUsuario}")
    public ResponseEntity<List<SoporteTicket>> listarTicketsPorCliente(@PathVariable Integer idUsuario) {
        List<SoporteTicket> tickets = service.listarTicketsPorCliente(idUsuario);
        return ResponseEntity.ok(tickets);
    }

    // Actualizar estado de un ticket
    @PutMapping("/{idTicket}/estado")
    public ResponseEntity<SoporteTicket> actualizarEstado(@PathVariable Integer idTicket, @RequestBody String nuevoEstado) {
        Optional<SoporteTicket> ticketActualizado = service.actualizarEstadoTicket(idTicket, nuevoEstado);
        return ticketActualizado
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
