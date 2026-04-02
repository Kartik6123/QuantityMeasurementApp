package com.app.quantity.repository;

import com.app.quantity.model.QuantityRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuantityRepository extends JpaRepository<QuantityRecord, Long> {
}
