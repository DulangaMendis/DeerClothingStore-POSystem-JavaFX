package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString


public class Item {
    private String id;
    private String  type;
    private String description;
    private String packSize;
    private Double unitPrice;
    private Integer qty;
    private LocalDate date;
}
