package lk.ijse.dto;

import javafx.scene.control.Button;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class ItemDTO {
    private String itemID;
    private String itemName;
    private int itemQuantity;
    private double unitSellingPrice;
    private double unitCost;
}
