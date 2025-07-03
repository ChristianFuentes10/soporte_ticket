package com.api_soporte_ticket.controller;

import com.api_soporte_ticket.models.SoporteTicket;
import com.api_soporte_ticket.service.SoporteTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/soporte/tickets")
public class SoporteTicketController {

    @Autowired
    private SoporteTicketService service;

    // Crear un ticket
    @PostMapping
    public ResponseEntity<EntityModel<SoporteTicket>> crearTicket(@RequestBody SoporteTicket ticket) {
        SoporteTicket creado = service.crearTicket(ticket);
        EntityModel<SoporteTicket> resource = EntityModel.of(creado,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SoporteTicketController.class).listarTicketsPorCliente(creado.getIdUsuario())).withRel("tickets-del-usuario"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SoporteTicketController.class).actualizarEstado(creado.getIdTicket(), null)).withRel("actualizar-estado")
        );
        return ResponseEntity.status(201).body(resource);
    }

    // Listar tickets de un cliente por idUsuario
    @GetMapping("/cliente/{idUsuario}")
    public ResponseEntity<CollectionModel<EntityModel<SoporteTicket>>> listarTicketsPorCliente(@PathVariable Integer idUsuario) {
        List<SoporteTicket> tickets = service.listarTicketsPorCliente(idUsuario);
        List<EntityModel<SoporteTicket>> ticketResources = tickets.stream()
                .map(ticket -> EntityModel.of(ticket,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SoporteTicketController.class).actualizarEstado(ticket.getIdTicket(), null)).withRel("actualizar-estado")
                ))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<SoporteTicket>> collectionModel = CollectionModel.of(ticketResources,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SoporteTicketController.class).listarTicketsPorCliente(idUsuario)).withSelfRel()
        );
        return ResponseEntity.ok(collectionModel);
    }

    // Actualizar estado de un ticket
    @PutMapping("/{idTicket}/estado")
    public ResponseEntity<EntityModel<SoporteTicket>> actualizarEstado(@PathVariable Integer idTicket, @RequestBody String nuevoEstado) {
        Optional<SoporteTicket> ticketActualizado = service.actualizarEstadoTicket(idTicket, nuevoEstado);
        return ticketActualizado
                .map(ticket -> {
                    EntityModel<SoporteTicket> resource = EntityModel.of(ticket,
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SoporteTicketController.class).listarTicketsPorCliente(ticket.getIdUsuario())).withRel("tickets-del-usuario")
                    );
                    return ResponseEntity.ok(resource);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
