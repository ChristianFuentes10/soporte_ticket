package com.api_soporte_ticket.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoporteTicketDTO {
    private Integer idTicket;
    private Integer idUsuario;
    private String tipoTicket;
    private String descripcion;
    private String estado;
    private LocalDate fechaCreacion;
    private LocalDate fechaResolucion;
}
