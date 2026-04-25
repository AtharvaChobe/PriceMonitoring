package com.PriceMonitoring.AlertService.service;

import com.PriceMonitoring.AlertService.Entity.Alert;
import com.PriceMonitoring.AlertService.Entity.AlertStatus;
import com.PriceMonitoring.AlertService.dtos.UserDataDto;
import com.PriceMonitoring.AlertService.repository.AlertRepository;
import com.resend.core.exception.ResendException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AlertSendService {
    @Autowired
    private MarketDataClient marketDataClient;

    @Autowired
    private UserDataClient userDataClient;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 */2 * * * *")
    public void sendAlerts() throws ResendException {
        List<Alert> alerts = alertRepository.findAll();
        HashMap<String, Double> priceCache = new HashMap<>();
        for (Alert a : alerts) {
            String key = a.getCity() + ":" + a.getMetal();
            UserDataDto user = userDataClient.getUserData(a.getUserId());
            if (!priceCache.containsKey(key)) {
                double currentPrice = marketDataClient.getMarketData(a.getMetal(), a.getCity());
                priceCache.put(key, currentPrice);
            }
            double currentPrice = priceCache.get(key);
            if (currentPrice >= a.getTargetPrice() && a.getStatus() == AlertStatus.ACTIVE) {
                emailService.sendMail(user.getEmail(), user.getUsername(), a, currentPrice);
                a.setStatus(AlertStatus.TRIGGERED);
                alertRepository.save(a);
            } else if (currentPrice < a.getTargetPrice() && a.getStatus() == AlertStatus.TRIGGERED) {
                a.setStatus(AlertStatus.ACTIVE);
                alertRepository.save(a);
            }
        }
    }
}