package model;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Cashier {
    private String id;
    private String  title;
    private String  name;
    private String address;
    private String username;
    private String password;
    private String email;
    private String contact;
    private LocalDate date;

    public String getID() {
        return id;
    }


}
