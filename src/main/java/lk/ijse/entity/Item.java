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
public class Item {
    @Id
    private String itemID;
    private String itemName;
    private int itemQuantity;
    private double unitSellingPrice;
    private double unitCost;

    @ManyToMany(mappedBy = "items")
    private Set<Order> orders = new HashSet<>();
}
