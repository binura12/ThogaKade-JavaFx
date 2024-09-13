package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@ToString
public class Customer {
    private String id;
    private String custTitle;
    private String custName;
    private LocalDate dob;
    private double salary;
    private String custAddress;
    private String city;
    private String province;
    private String postalCode;

    public Customer(String id, String custTitle, String custName, LocalDate dob, double salary, String custAddress, String city, String province, String postalCode) {
        this.id = id;
        this.custTitle = custTitle;
        this.custName = custName;
        this.dob = dob;
        this.salary = salary;
        this.custAddress = custAddress;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
    }
}
