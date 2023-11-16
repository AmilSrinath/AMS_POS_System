package lk.ijse.dto;

import jakarta.persistence.*;
import lk.ijse.entity.Order;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class ItemDTO {
    @Id
    private String itemID;
    private String itemName;
    private int itemQuantity;
    private double unitSellingPrice;
    private double unitCost;
    private Set<Order> orders = new HashSet<>();
}
