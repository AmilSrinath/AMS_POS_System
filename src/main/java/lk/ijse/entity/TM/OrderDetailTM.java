package lk.ijse.entity.TM;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter

@Entity
public class OrderDetailTM {
    @Id
    private String orderDetailID;
    private String item;
    private String order;
    private int quantity;
    private double unitPrice;
    private double subTotal;
}
