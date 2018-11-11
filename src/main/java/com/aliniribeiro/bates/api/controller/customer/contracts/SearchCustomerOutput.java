package com.aliniribeiro.bates.api.controller.customer.contracts;

import java.util.List;

public class SearchCustomerOutput {

    /**
     * Quantidade de itens encontrados
     */
    public Long registeredFound;

    /**
     * Lista de clientes encontrados
     */
    public List<CustomerDTO> customerDTOList;
}
