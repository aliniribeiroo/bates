package com.aliniribeiro.bates.model.customer;

import com.aliniribeiro.bates.model.common.PageResult;

public interface CustomerRepositoryCustom {

    PageResult searchCustomers(String q, Long page, Long size);

    PageResult findCustomersWithCheckin(boolean isPastHosted,Long page, Long size);

}
