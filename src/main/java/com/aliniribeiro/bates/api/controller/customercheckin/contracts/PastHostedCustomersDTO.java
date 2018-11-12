package com.aliniribeiro.bates.api.controller.customercheckin.contracts;

import java.util.UUID;

public class PastHostedCustomersDTO {

    /**
     * Id o registro do cliente na base de dados
     */
    public UUID customerId;

    /**
     * Nome do cliente
     */
    public String custmerName;

    /**
     * Documento do cliente
     */
    public String registerId;

    /**
     * Valor total da última hospedagem no hotel.
     */
    public String lastHostedValue;

    /**
     * Soma de todas as hospedagens do cliente no hotel.
     */
    public String allhostedValue;
}
