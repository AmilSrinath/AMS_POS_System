package lk.ijse.dto;

import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class OrderAndOrderDetailsDTO {
    @Id
    private String orderDetailID;
    private String itemID;
    private String itemName;
    private int quantity;
    private double unitSellingPrice;
    private double subTotal;
    private String orderID;
    private double unitCost;
    private double netTotal;
    private String date;
    private String time;
}
