package pl.spc.fighter.boot.model;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class Employee {
    @Null
    private String id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @Email
    @NotEmpty
    private String email;
    @NotNull
    @Valid
    private Salary salary;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public static class Salary {
        @Null
        private String id;
        @NotNull
        @Min(0)
        private long amount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getAmount() {
            return amount;
        }

        public void setAmount(long amount) {
            this.amount = amount;
        }
    }
}
