package com.PriceMonitoring.AlertService.service;

import com.PriceMonitoring.AlertService.Entity.Alert;
import com.PriceMonitoring.AlertService.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {
    @Autowired
    private AlertRepository repository;

    public Alert save(Alert alert) {
        return repository.save(alert);
    }

    public List<Alert> getAll() {
        return repository.findAll();
    }

    public void delete(long alertId) {
        repository.deleteById(alertId);
    }

    public List<Alert> getByUserId(long userId){
        return repository.findAllByUserId(userId);
    }
}
