package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter

@Entity
@Table(name = "orders")
public class Order {
    @Id
    private String orderID;
    private String date;
    private String time;
    private double netTotal;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderDetail> orderDetails = new HashSet<>();

    public void addOrderDetail(OrderDetail orderDetails) {
        this.orderDetails.add(orderDetails);
    }
}
