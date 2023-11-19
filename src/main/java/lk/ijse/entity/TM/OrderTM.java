package lk.ijse.entity.TM;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter

@Entity
public class OrderTM {
    @Id
    private String itemID;
    private String itemName;
    private double unitSellingPrice;
    private double unitCost;
    private int quantity;
    private double total;
}
