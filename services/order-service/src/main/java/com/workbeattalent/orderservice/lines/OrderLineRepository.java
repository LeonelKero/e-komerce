package com.workbeattalent.orderservice.lines;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {

    List<OrderLine> findAllByOrderId(final Long id);

}
