package com.aliniribeiro.bates.api.controller.customercheckin.contracts;

import com.aliniribeiro.bates.api.controller.customer.contracts.CustomerDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class CheckInOutput {

    /**
     * Cliente
     */
    public CustomerDTO customer;

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
