package com.workbeattalent.orderservice.lines;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.workbeattalent.orderservice.order.Order;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderLine {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;
    private Integer productId;
    private Integer quantity;
}
