package com.aliniribeiro.bates.api.controller.customercheckin.contracts;

import java.util.List;

public class HostedCustomersOutut {

    /**
     * Quantidade de itens encontrados
     */
    public Long registeredFound;

    /**
     * Lista de clientes que estiveram hospedados no hotel
     */
    public List<PastHostedCustomersDTO> pastHosted;

}
