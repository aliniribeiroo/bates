package com.aliniribeiro.bates.model.customercheckin;

import java.util.List;
import java.util.UUID;

public interface CustomerCheckinRepositoryCustom {

    List<CustomerCheckinEntity> getCustomersCheckin(boolean isPastHosted, UUID customerId);

}
