package com.aliniribeiro.bates.api.controller.customer.contracts;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerDTO {

    /**
     * Identificador único do cliente
     */
    public  UUID id;

    /**
     * Nome do cliente
     */
    public String name;

    /**
     * Telefone do cliente
     */
    public String phone;

    /**
     * data de criaçãp do cadastro do cliente
     */
    public LocalDateTime creationDate;

    /**
     * Documento do cliente
     */
    public String registerId;
}
