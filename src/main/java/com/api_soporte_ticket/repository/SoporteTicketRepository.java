package com.api_soporte_ticket.repository;

import com.api_soporte_ticket.models.SoporteTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoporteTicketRepository extends JpaRepository<SoporteTicket, Integer> {
}
