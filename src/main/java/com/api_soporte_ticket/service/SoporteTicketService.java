package com.api_soporte_ticket.service;

import com.api_soporte_ticket.models.SoporteTicket;
import com.api_soporte_ticket.repository.SoporteTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SoporteTicketService {

    @Autowired
    private SoporteTicketRepository repository;

    // Crear un ticket nuevo
    public SoporteTicket crearTicket(SoporteTicket ticket) {
        return repository.save(ticket);
    }

    // Listar tickets por idUsuario (cliente)
    public List<SoporteTicket> listarTicketsPorCliente(Integer idUsuario) {
        return repository.findByIdUsuario(idUsuario);
    }

    // Actualizar estado de un ticket por id
    public Optional<SoporteTicket> actualizarEstadoTicket(Integer idTicket, String nuevoEstado) {
        Optional<SoporteTicket> ticketOpt = repository.findById(idTicket);
        if (ticketOpt.isPresent()) {
            SoporteTicket ticket = ticketOpt.get();
            ticket.setEstado(nuevoEstado);
            return Optional.of(repository.save(ticket));
        }
        return Optional.empty();
    }
}
