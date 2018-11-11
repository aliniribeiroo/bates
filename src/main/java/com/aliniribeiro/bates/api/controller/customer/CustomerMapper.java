package com.aliniribeiro.bates.api.controller.customer;

import com.aliniribeiro.bates.api.controller.customer.contracts.CustomerDTO;
import com.aliniribeiro.bates.model.customer.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerDTO customerDTO(CustomerEntity entity) {
        if (entity == null) {
            return null;
        }

        CustomerDTO dto = new CustomerDTO();
        dto.name = entity.getName();
        dto.phone = entity.getPhone();
        dto.registerId = entity.getRegisterId();
        dto.creationDate = entity.getCreationDate();
        dto.id = entity.getId();
        return dto;
    }
}
