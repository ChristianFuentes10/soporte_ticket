package com.api_soporte_ticket.controller;

import com.api_soporte_ticket.dto.SoporteTicketDTO;
import com.api_soporte_ticket.models.SoporteTicket;
import com.api_soporte_ticket.service.SoporteTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.Link;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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

    // METODOS HATEOAS

    // METODO HATEOAS para buscar ticket por ID
    @GetMapping("/hateoas/{idTicket}")
    public SoporteTicketDTO obtenerHATEOAS(@PathVariable Integer idTicket) {
        SoporteTicketDTO dto = service.obtenerPorIdDTO(idTicket);

        // links urls de la misma API
        dto.add(linkTo(methodOn(SoporteTicketController.class).obtenerHATEOAS(idTicket)).withSelfRel());
        dto.add(linkTo(methodOn(SoporteTicketController.class).obtenerTodosHATEOAS()).withRel("todos"));
        dto.add(linkTo(methodOn(SoporteTicketController.class).eliminar(idTicket)).withRel("eliminar"));

        // links HATEOAS para API Gateway "A mano"
        dto.add(Link.of("http://localhost:8888/api/proxy/tickets/" + dto.getIdTicket()).withSelfRel());
        dto.add(Link.of("http://localhost:8888/api/proxy/tickets/" + dto.getIdTicket()).withRel("Modificar HATEOAS").withType("PUT"));
        dto.add(Link.of("http://localhost:8888/api/proxy/tickets/" + dto.getIdTicket()).withRel("Eliminar HATEOAS").withType("DELETE"));

        return dto;
    }

    // METODO HATEOAS para listar todos los tickets utilizando HATEOAS
    @GetMapping("/hateoas")
    public List<SoporteTicketDTO> obtenerTodosHATEOAS() {
        List<SoporteTicketDTO> lista = service.listarDTO();

        for (SoporteTicketDTO dto : lista) {
            // link url de la misma API
            dto.add(linkTo(methodOn(SoporteTicketController.class).obtenerHATEOAS(dto.getIdTicket())).withSelfRel());

            // link HATEOAS para API Gateway "A mano"
            dto.add(Link.of("http://localhost:8888/api/proxy/tickets").withRel("Get todos HATEOAS"));
            dto.add(Link.of("http://localhost:8888/api/proxy/tickets/" + dto.getIdTicket()).withRel("Crear HATEOAS").withType("POST"));
        }

        return lista;
    }
}
