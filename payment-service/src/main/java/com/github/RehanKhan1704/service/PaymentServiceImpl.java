package com.github.RehanKhan1704.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.RehanKhan1704.dto.PaymentResponse;
import com.github.RehanKhan1704.entity.Payment;
import com.github.RehanKhan1704.entity.PaymentStatus;
import com.github.RehanKhan1704.events.OrderCreatedEvent;
import com.github.RehanKhan1704.events.PaymentFailedEvent;
import com.github.RehanKhan1704.events.PaymentSuccessEvent;
import com.github.RehanKhan1704.exception.PaymentNotFoundException;
import com.github.RehanKhan1704.kafka.PaymentProducer;
import com.github.RehanKhan1704.mapper.PaymentMapper;
import com.github.RehanKhan1704.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl
        implements PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final PaymentProducer producer;

    public void processPayment(
            OrderCreatedEvent event) {

        Payment payment = Payment.builder()
                .orderId(event.orderId())
                .amount(event.amount())
                .createdAt(LocalDateTime.now())
                .transactionId(
                        UUID.randomUUID().toString())
                .build();

        if (event.amount() > 100000) {

            payment.setStatus(
                    PaymentStatus.FAILED);

            repository.save(payment);

            producer.publishFailure(
                    new PaymentFailedEvent(
                            event.orderId(),
                            "Amount exceeds limit"
                    )
            );

            return;
        }

        payment.setStatus(
                PaymentStatus.SUCCESS);

        repository.save(payment);

        producer.publishSuccess(
                new PaymentSuccessEvent(
                        event.orderId(),
                        event.productId(),
                        event.quantity(),
                        payment.getTransactionId()
                )
        );
    }

    @Override
    public PaymentResponse getPayment(
            Long id) {

        return mapper.toResponse(
                repository.findById(id)
                        .orElseThrow(() ->
                                new PaymentNotFoundException(id)
                        )
        );
    }

    @Override
    public Page<PaymentResponse> getAllPayments(
            Pageable pageable) {

        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }
}