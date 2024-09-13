package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class Items {
    private String itemCode;
    private String description;
    private String packSize;
    private double unitPrice;
    private int qty;

    public Items(String itemCode, String description, String packSize, double unitPrice, int qty) {
        this.itemCode = itemCode;
        this.description = description;
        this.packSize = packSize;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }
}
