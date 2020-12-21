package net.dni.job.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCsvItem {

    private String firstName;
    private String lastName;
    private String position;

}
