package com.api_soporte_ticket.repository;

import com.api_soporte_ticket.models.SoporteTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SoporteTicketRepository extends JpaRepository<SoporteTicket, Integer> {
    List<SoporteTicket> findByIdUsuario(Integer idUsuario);
}
