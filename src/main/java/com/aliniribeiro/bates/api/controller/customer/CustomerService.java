package com.aliniribeiro.bates.api.controller.customer;

import com.aliniribeiro.bates.api.common.exceptions.Exceptions;
import com.aliniribeiro.bates.api.common.util.Spring;
import com.aliniribeiro.bates.api.controller.customer.contracts.CustomerDTO;
import com.aliniribeiro.bates.api.controller.customer.contracts.SearchCustomerOutput;
import com.aliniribeiro.bates.model.common.PageResult;
import com.aliniribeiro.bates.model.customer.CustomerEntity;
import com.aliniribeiro.bates.model.customer.CustomerRepository;
import com.aliniribeiro.bates.model.customercheckin.CustomerCheckinEntity;
import com.aliniribeiro.bates.model.customercheckin.CustomerCheckinRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CustomerService {

    /**
     * Método que cria um novo cliente.
     *
     * @param customerDTO dto com as informações do cliente que será criado.
     * @return DTO com as informações d cliente já atualizado.
     */
    public CustomerDTO createClient(CustomerDTO customerDTO) {
        CustomerEntity customer = new CustomerEntity();
        customer.setName(customerDTO.name);
        customer.setRegisterId(customerDTO.registerId);
        customer.setCreationDate(LocalDateTime.now());
        customer.setPhone(customerDTO.phone);
        Spring.bean(CustomerRepository.class).save(customer);
        return Spring.bean(CustomerMapper.class).customerDTO(customer);
    }

    /**
     * Método que cria um novo cliente.
     *
     * @param customerDTO dto com as informações do cliente que será criado.
     * @return DTO com as informações d cliente já atualizado.
     */
    public CustomerDTO updateClient(CustomerDTO customerDTO) {
        Optional<CustomerEntity> c = Spring.bean(CustomerRepository.class).findCustomerById(customerDTO.id);
        if (!c.isPresent()) {
            Exceptions.customerNotFound(customerDTO.id);
        }
        CustomerEntity customer = c.get();
        customer.setName(customerDTO.name);
        customer.setRegisterId(customerDTO.registerId);
        customer.setCreationDate(LocalDateTime.now());
        customer.setPhone(customerDTO.phone);
        Spring.bean(CustomerRepository.class).save(customer);
        return Spring.bean(CustomerMapper.class).customerDTO(customer);
    }

    /**
     * Método que retorna clientes.
     *
     * @return Optional copm o total de clientes cadastrados no sistema.
     */
    public Optional<SearchCustomerOutput> searchCustomer(String q, Long page, Long size) {
        PageResult<CustomerEntity> customers = Spring.bean(CustomerRepository.class).searchCustomers(q, page, size);
        SearchCustomerOutput output = new SearchCustomerOutput();
        output.customerDTOList = new ArrayList<>();
        output.registeredFound = customers.getTotalCount();
        customers.getRows().stream().forEach(c -> output.customerDTOList.add(Spring.bean(CustomerMapper.class).customerDTO(c)));
        return Optional.ofNullable(output);
    }

    /**
     * Deleta um cliente, verifica se ele não possui nenhum checkIn, para então deletar.
     *
     * @param customer Cliente a ser deletado.
     */
    public void delete(CustomerEntity customer) {
        verifyIfCustomerHasCheckin(customer);
        Spring.bean(CustomerRepository.class).delete(customer);
    }

    public void verifyIfCustomerHasCheckin(CustomerEntity customer) {
        Optional<List<CustomerCheckinEntity>> checkin = Spring.bean(CustomerCheckinRepository.class).findByCustomerId(customer.getId());
        if (checkin.isPresent()) {
            Exceptions.customerHasCheckin(customer.getName());
        }
    }

    public Optional<CustomerEntity> findById(String id) {
        return Spring.bean(CustomerRepository.class).findById(id);
    }
}
