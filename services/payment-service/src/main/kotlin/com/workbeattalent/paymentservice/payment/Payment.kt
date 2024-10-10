package com.workbeattalent.paymentservice.payment

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class Payment(
    @Id
    @GeneratedValue
    val id: Long?,

    val amount: BigDecimal,

    @Enumerated(EnumType.STRING)
    val paymentMethod: PaymentMethod,

    val orderId: Long,

    @CreatedDate
    @Column(updatable = false, nullable = false)
    val createdAt: LocalDateTime,

    @LastModifiedDate
    @Column(insertable = false)
    val modifiedAt: LocalDateTime
) {
}