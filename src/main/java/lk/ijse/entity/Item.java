package lk.ijse.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import javafx.scene.control.Button;
import lombok.*;

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
}
