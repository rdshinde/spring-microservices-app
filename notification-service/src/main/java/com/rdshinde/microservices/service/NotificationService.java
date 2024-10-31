package com.rdshinde.microservices.service;

import com.rdshinde.microservices.order_service.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "order-service")
    public void listen(OrderPlacedEvent orderPlacedEvent) {
        log.info("Received message from order-service: {}", orderPlacedEvent);
        //Send email to user
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("kalyani2007shinde@gmail.com");
            messageHelper.setTo(orderPlacedEvent.getEmail().toString());
            messageHelper.setSubject("Order Placed Successfully : " + orderPlacedEvent.getOrderNumber());
            messageHelper.setText("Your order has been placed successfully. Order number is: " + orderPlacedEvent.getOrderNumber());
        };
        try {
            javaMailSender.send(messagePreparator);
            log.info("Order notification sent successfully to: {}", orderPlacedEvent.getEmail());
        } catch (Exception e) {
            log.error("Error while sending email: {}", e.getMessage());
            throw new RuntimeException("Error while sending email");
        }
    }
}
