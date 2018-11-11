package com.aliniribeiro.bates.model.customer;

import com.aliniribeiro.bates.model.common.PageRequest;
import com.aliniribeiro.bates.model.common.PageResult;
import com.aliniribeiro.bates.model.common.RepositoryBaseImpl;
import com.aliniribeiro.bates.model.customercheckin.QCustomerCheckinEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerRepositoryImpl extends RepositoryBaseImpl implements CustomerRepositoryCustom {

    @Override
    public PageResult searchCustomers(String q, Long page, Long size) {
        QCustomerEntity customer = QCustomerEntity.customerEntity;

        JPAQuery<CustomerEntity> query = select(customer).from(customer);
        if (!StringUtils.isEmpty(q)) {
            String[] terms = q.replaceAll("\\s+", " ").trim().split("\\s");
            BooleanExpression predicate = null;
            for (String t : terms) {
                BooleanExpression namePredicate = customer.name.containsIgnoreCase(t);
                BooleanExpression registerNumberPredicate = customer.registerId.containsIgnoreCase(t);
                BooleanExpression phoneNumberPredicate = customer.phone.containsIgnoreCase(t);
                predicate = predicate == null ? namePredicate.or(registerNumberPredicate).or(phoneNumberPredicate) : namePredicate.or(registerNumberPredicate).or(phoneNumberPredicate);
            }
            if (predicate != null) {
                query.where(predicate);
            }
        }

        PageRequest pageRequest = new PageRequest(page, size);
        return getPagedQuery(query, pageRequest);
    }

    @Override
    public PageResult findCustomersWithCheckin(boolean isPastHosted, Long page, Long size) {
        LocalDateTime now = LocalDateTime.now();
        QCustomerCheckinEntity checkin = QCustomerCheckinEntity.customerCheckinEntity;
        JPAQuery<UUID> checkinQuery = select(checkin.customerId).from(checkin);
        if (isPastHosted){
            checkinQuery.where(checkin.checkOutDate.before(now));
        } else {
            checkinQuery.where(checkin.checkOutDate.after(now).and(checkin.checkInDate.before(now)));
        }

        QCustomerEntity customer = QCustomerEntity.customerEntity;
        JPAQuery<CustomerEntity> query = select(customer).from(customer);
        query.where(customer.id.in(checkinQuery));

        PageRequest pageRequest = new PageRequest(page, size);
        return getPagedQuery(query, pageRequest);
    }
}
