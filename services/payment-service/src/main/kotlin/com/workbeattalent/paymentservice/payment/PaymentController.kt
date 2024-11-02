package com.workbeattalent.paymentservice.payment

import com.workbeattalent.paymentservice.payment.dto.PaymentRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/v1/payments"])
class PaymentController(private val service: PaymentService) {

    @PostMapping
    fun makePayment(@Valid @RequestBody paymentRequest: PaymentRequest): ResponseEntity<Long> {
        return ResponseEntity.ok(service.createPayment(paymentRequest))
    }

    @GetMapping
    fun check() = "The payment service is up and running"

}