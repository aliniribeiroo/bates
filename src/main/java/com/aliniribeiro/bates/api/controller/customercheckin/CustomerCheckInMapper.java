package com.aliniribeiro.bates.api.controller.customercheckin;

import com.aliniribeiro.bates.api.common.util.Spring;
import com.aliniribeiro.bates.api.controller.customer.CustomerMapper;
import com.aliniribeiro.bates.api.controller.customercheckin.contracts.CheckInDTO;
import com.aliniribeiro.bates.api.controller.customercheckin.contracts.CheckInOutput;
import com.aliniribeiro.bates.api.controller.customercheckin.contracts.PastHostedCustomersDTO;
import com.aliniribeiro.bates.model.customer.CustomerEntity;
import com.aliniribeiro.bates.model.customercheckin.CustomerCheckinEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerCheckInMapper {

    public CheckInOutput toCheckinDTO(CustomerCheckinEntity entity) {
        if (entity == null) {
            return null;
        }

        CheckInOutput dto = new CheckInOutput();
        dto.addictional = entity.getAdditional();
        dto.inDate = entity.getCheckInDate();
        dto.outDate = entity.getCheckOutDate();
        dto.customer = Spring.bean(CustomerMapper.class).customerDTO(entity.getCustomer());
        return dto;
    }

    public PastHostedCustomersDTO toPastHostedCustomersDTO(CustomerCheckinEntity entity) {
        if (entity == null) {
            return null;
        }

        PastHostedCustomersDTO dto = new PastHostedCustomersDTO();
        dto.custmerName = entity.getCustomer().getName();
      //  dto.inDate = entity.getCheckInDate();
       // dto.outDate = entity.getCheckOutDate();
     //   dto.customer = Spring.bean(CustomerMapper.class).customerDTO(entity.getCustomer());
        return dto;
    }
}
