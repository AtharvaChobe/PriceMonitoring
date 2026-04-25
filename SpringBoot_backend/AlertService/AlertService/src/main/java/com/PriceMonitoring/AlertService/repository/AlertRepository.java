package com.PriceMonitoring.AlertService.repository;

import com.PriceMonitoring.AlertService.Entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findAllByUserId(long userId);
}
