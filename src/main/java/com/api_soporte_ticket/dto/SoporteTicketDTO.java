package com.api_soporte_ticket.dto;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// Extiende RepresentationModel para soporte HATEOAS
public class SoporteTicketDTO extends RepresentationModel<SoporteTicketDTO> {
    private Integer idTicket;
    private Integer idUsuario;
    private String tipoTicket;
    private String descripcion;
    private String estado;
    private LocalDate fechaCreacion;
    private LocalDate fechaResolucion;
}
