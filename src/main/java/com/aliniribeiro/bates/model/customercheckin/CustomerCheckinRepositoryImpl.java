package com.aliniribeiro.bates.model.customercheckin;

import com.aliniribeiro.bates.model.common.RepositoryBaseImpl;
import com.querydsl.jpa.impl.JPAQuery;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class CustomerCheckinRepositoryImpl extends RepositoryBaseImpl implements CustomerCheckinRepositoryCustom {

    @Override
    public List<CustomerCheckinEntity> getCustomersCheckin(boolean isPastHosted, UUID customerId) {
        LocalDateTime now = LocalDateTime.now();
        QCustomerCheckinEntity checkin = QCustomerCheckinEntity.customerCheckinEntity;
        JPAQuery<CustomerCheckinEntity> query = select(checkin).from(checkin);
        if (isPastHosted){
            query.where(checkin.checkOutDate.before(now));
        } else {
            query.where(checkin.checkOutDate.after(now).and(checkin.checkInDate.before(now)));
        }
        query.where(checkin.customerId.eq(customerId));
        query.orderBy(checkin.checkOutDate.desc());

        return query.fetch();
    }

}
