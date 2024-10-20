package com.workbeattalent.orderservice.order;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.workbeattalent.orderservice.lines.OrderLine;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer_orders")
@EntityListeners({AuditingEntityListener.class})
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    private String reference;
    private BigDecimal totalAmount;

    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;

    private String customerId;

    @OneToMany(mappedBy = "order")
    @JsonManagedReference
    private Set<OrderLine> lines = new HashSet<>();

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime lastModifiedAt;
}
