package com.aliniribeiro.bates.api.controller.customercheckin;

import com.aliniribeiro.bates.api.common.exceptions.Exceptions;
import com.aliniribeiro.bates.api.common.util.Spring;
import com.aliniribeiro.bates.api.controller.customercheckin.contracts.CheckInDTO;
import com.aliniribeiro.bates.api.controller.customercheckin.contracts.CheckInOutput;
import com.aliniribeiro.bates.api.controller.customercheckin.contracts.HostedCustomersOutut;
import com.aliniribeiro.bates.api.controller.customercheckin.contracts.PastHostedCustomersDTO;
import com.aliniribeiro.bates.model.common.PageResult;
import com.aliniribeiro.bates.model.customer.CustomerEntity;
import com.aliniribeiro.bates.model.customer.CustomerRepository;
import com.aliniribeiro.bates.model.customercheckin.CustomerCheckinEntity;
import com.aliniribeiro.bates.model.customercheckin.CustomerCheckinRepository;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CustomerCheckInService {

    public CheckInOutput createOrUpdateCheckIn(CheckInDTO checkinDTO) {
        CustomerCheckinEntity checkin = new CustomerCheckinEntity();

        if (checkinDTO.id != null) {
            Optional<CustomerCheckinEntity> c = Spring.bean(CustomerCheckinRepository.class).findById(checkinDTO.id);
            checkin = c.isPresent() ? c.get() : checkin;
        }

        Optional<CustomerEntity> customer = Spring.bean(CustomerRepository.class).findCustomerById(checkinDTO.customerId);
        if (customer.isPresent()) {
            checkin.setCustomer(customer.isPresent() ? customer.get() : null);
        } else {
            Exceptions.customerNotFound(checkinDTO.customerId);
        }

        checkin.setAdditional(checkinDTO.addictional);
        checkin.setCheckInDate(checkinDTO.inDate);
        checkin.setCheckOutDate(checkinDTO.outDate);
        Spring.bean(CustomerCheckinRepository.class).save(checkin);
        return Spring.bean(CustomerCheckInMapper.class).toCheckinDTO(checkin);
    }

    public HostedCustomersOutut getHostedCustomers(boolean isPastHosted, Long page, Long size) {
        HostedCustomersOutut outut = new HostedCustomersOutut();
        outut.pastHosted = new ArrayList<>();
        PageResult<CustomerEntity> allCustomers = Spring.bean(CustomerRepository.class).findCustomersWithCheckin(isPastHosted, page, size);
        outut.registeredFound = allCustomers.getTotalCount();
        allCustomers.getRows().forEach(customer -> {
            List<CustomerCheckinEntity> customerCheckins =  Spring.bean(CustomerCheckinRepository.class).getCustomersCheckin(isPastHosted, customer.getId());
            if (!customerCheckins.isEmpty()) {
                PastHostedCustomersDTO dto = new PastHostedCustomersDTO();
                dto.customerId = customer.getId();
                dto.custmerName = customer.getName();
                dto.lastHostedValue = format(getHostedValue(customerCheckins.stream().findFirst().get()));
                dto.allhostedValue = getAllCheckinHostedValue(customerCheckins);
                dto.registerId = customer.getRegisterId();
                outut.pastHosted.add(dto);
            }
        });
        return outut;
    }

    private String getAllCheckinHostedValue(List<CustomerCheckinEntity> customerCheckins) {
        AtomicReference<Double> hostedAmount = new AtomicReference<Double>(new Double(0));
        customerCheckins.forEach(checkIn -> hostedAmount.set(hostedAmount.get() + getHostedValue(checkIn)));
        return format(hostedAmount.get());
    }

    /**
     * Método que calcula o valor gasto pelo hóspede no hotel.
     *
     * @param checkin informações da hospedagem do cliente.
     * @return Soma co o valor da hospedagem do cliente.
     */
    private Double getHostedValue(CustomerCheckinEntity checkin) {
        LocalDateTime startDate = checkin.getCheckInDate();
        LocalDateTime endDate = checkin.getCheckOutDate();
        List<LocalDateTime> hostedDays = Stream.iterate(startDate, start -> start.plusDays(1)).limit(ChronoUnit.DAYS.between(startDate, endDate)).collect(Collectors.toList());
        Double weekenddaysValue = getWeekEndDaysValue(hostedDays, endDate, checkin.getAdditional());
        Double weekDaysValue = getWeekDaysValue(hostedDays, checkin.getAdditional());

        return weekenddaysValue + weekDaysValue;
    }

    private Double getWeekEndDaysValue(List<LocalDateTime> hostedDays, LocalDateTime endDate, boolean hasAddicional) {
        Long weekenddays = hostedDays.stream().filter(data -> data.getDayOfWeek().equals(DayOfWeek.SATURDAY) && data.getDayOfWeek().equals(DayOfWeek.SUNDAY)).count();
        boolean lasDayWasWeekEnd = endDate.toLocalTime().isAfter(LocalTime.parse("16:30:00"));
        weekenddays = lasDayWasWeekEnd ? weekenddays + 1L : weekenddays;
        Double weekenddaysValue = new Double(weekenddays * (hasAddicional ? 150 + 20 : 150));
        return weekenddaysValue;
    }

    private Double getWeekDaysValue(List<LocalDateTime> hostedDays, boolean hasAddicional) {
        Long weekdays = hostedDays.stream().filter(data -> !data.getDayOfWeek().equals(DayOfWeek.SATURDAY) && !data.getDayOfWeek().equals(DayOfWeek.SUNDAY)).count();
        Double weekDaysValue = new Double(weekdays * (hasAddicional ? 120 + 15 : 120));
        return weekDaysValue;
    }

    private String format(Double value) {
        DecimalFormat df= new DecimalFormat("0.00");
        return df.format(value);
    }
}
