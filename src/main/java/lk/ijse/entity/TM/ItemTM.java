package lk.ijse.entity.TM;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter

public class ItemTM {
    private String itemID;
    private String itemName;
    private int itemQuantity;
    private double unitSellingPrice;
    private double unitCost;
}
