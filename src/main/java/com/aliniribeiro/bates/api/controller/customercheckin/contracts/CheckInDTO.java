package com.aliniribeiro.bates.api.controller.customercheckin.contracts;

import java.time.LocalDateTime;
import java.util.UUID;

public class CheckInDTO {

    /**
     * Identificador único do checkin
     */
    public String id;

    /**
     * Id do cliente
     */
    public UUID customerId;

    /**
     * Data de entrada
     */
    public LocalDateTime inDate;

    /**
     * Data de saída
     */
    public LocalDateTime outDate;

    /**
     * Documento do cliente
     */
    public boolean addictional;
}
