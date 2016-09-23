package pl.spc.assailant.akka.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import pl.spc.assailant.akka.model.Employee;
import pl.spc.assailant.akka.model.Employee.Salary;

public class EmployeeMapper implements ResultSetMapper<Employee> {
    private static final String AMOUNT_COLUMN_NAME = "amount";
    private static final String SURNAME_COLUMN_NAME = "surname";
    private static final String NAME_COLUMN_NAME = "name";
    private static final String EMAIL_COLUMN_NAME = "email";
    private static final String ID_COLUMN_NAME = "id";

    @Override
    public Employee map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        Employee employee = new Employee();

        employee.setId(r.getString(ID_COLUMN_NAME));
        employee.setName(r.getString(NAME_COLUMN_NAME));
        employee.setSurname(r.getString(SURNAME_COLUMN_NAME));
        employee.setEmail(r.getString(EMAIL_COLUMN_NAME));

        Salary salary = new Salary();

        salary.setId(r.getString(ID_COLUMN_NAME));
        salary.setAmount(r.getLong(AMOUNT_COLUMN_NAME));

        employee.setSalary(salary);

        return employee;
    }
}

