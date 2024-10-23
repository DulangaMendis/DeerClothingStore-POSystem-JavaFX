package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Supplier {
    private String id;
    private String  title;
    private String  name;
    private String company;
    private String contact;
    private String itemCode;
    private String status;
    private Integer qty;
    private LocalDate date;

    public String getID() {
        return id;
    }

    public int getQTY() {
        return qty;
    }

}
