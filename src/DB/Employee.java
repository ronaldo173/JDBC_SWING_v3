package DB;

import java.math.BigDecimal;

/**
 * Created by Santer on 04.11.2015.
 */
public class Employee {
    private int id;
    private String lastName, firstName, email;
    private BigDecimal salary;

    public Employee(int id, String lastName, String firstName, String email, BigDecimal salary) {

        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return String.format(
                "Emplooyee id = %s, lastname = %s, firstname=%s, email =%s, salary = %s",
                id, lastName, firstName,email,salary
        );
    }
}