package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter

@Entity
@Table(name = "order_details")
public class OrderDetail {
    @Id
    private String orderDetailID;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "itemID")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "orderID")
    private Order order;

    private int quantity;
    private double unitCost;
    private double unitPrice;
    private double subTotal;
}
