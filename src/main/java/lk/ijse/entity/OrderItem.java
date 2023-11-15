package lk.ijse.entity;

import jakarta.persistence.Entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter

@Entity
public class OrderItem {
    private String itemID;
    private String itemName;
    private double unitSellingPrice;
    private int quantity;
    private double total;
}
