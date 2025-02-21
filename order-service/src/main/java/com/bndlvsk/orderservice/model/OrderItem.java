package com.bndlvsk.orderservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public void setOrder(Order order) {
        if (this.order != null) {
            this.order.getOrderItems().remove(this); //del связь с предыдущим Order
        }
        this.order = order;
        if (order != null) {
            order.getOrderItems().add(this); //add связь с новым Order
        }
    }
}
