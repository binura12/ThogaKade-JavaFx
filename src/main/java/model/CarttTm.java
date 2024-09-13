package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarttTm {
    private String itemCode;
    private String desc;
    private Integer qty;
    private Double unitPrice;
    private Double total;
}
