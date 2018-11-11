package com.aliniribeiro.bates.api.controller.customercheckin.contracts;

public class PastHostedCustomersDTO {

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
