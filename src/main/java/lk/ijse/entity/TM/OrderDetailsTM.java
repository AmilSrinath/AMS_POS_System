package lk.ijse.entity.TM;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class OrderDetailsTM {
    private String orderDetailID;
    private String itemID;
    private String itemName;
    private int quantity;
    private double unitSellingPrice;
    private double unitCost;
    private double subTotal;
}
