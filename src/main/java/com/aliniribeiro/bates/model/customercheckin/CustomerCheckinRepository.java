package com.aliniribeiro.bates.model.customercheckin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerCheckinRepository extends JpaRepository<CustomerCheckinEntity, String>, CustomerCheckinRepositoryCustom {

    Page<CustomerCheckinEntity> findAll(Pageable pageable);

    Optional<List<CustomerCheckinEntity>> findByCustomerId(UUID customer);


}
