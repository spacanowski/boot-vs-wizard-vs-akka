package pl.spc.fighter.boot.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pl.spc.fighter.boot.model.Employee;
import pl.spc.fighter.boot.model.Employee.Salary;

@Repository
public class EmployeeRepository {
    private static final String AMOUNT_COLUMN_NAME = "amount";
    private static final String SURNAME_COLUMN_NAME = "surname";
    private static final String NAME_COLUMN_NAME = "name";
    private static final String EMAIL_COLUMN_NAME = "email";
    private static final String ID_COLUMN_NAME = "id";

    private static final String ADD_EMPLOYEE = "insert into employees values (?,?,?,?)";
    private static final String ADD_SALARY = "insert into employees values (?,?)";
    private static final String GET_EMPOYEE_BY_ID = "select e.id, e.name, e.surname, e.email, s.amount from employees e join salary s on e.id = s.id where e.id = ?";
    private static final String GET_EMPOYEES = "select e.id, e.name, e.surname, e.email, s.amount from employees e join salary s on e.id = s.id";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addEmployee(Employee empolyee) {
        jdbcTemplate.update(ADD_EMPLOYEE, empolyee.getId(), empolyee.getName(), empolyee.getSurname(), empolyee.getEmail());
    };

    public void addSalary(Salary salary) {
        jdbcTemplate.update(ADD_SALARY, salary.getId(), salary.getAmount());
    };

    public Employee getEmployee(String id) {
        return jdbcTemplate.queryForObject(GET_EMPOYEE_BY_ID, (rs, rowNum) -> extractEmployee(rs), id);
    };

    public List<Employee> getEmployees() {
        return jdbcTemplate.query(GET_EMPOYEES, (rs, rowNum) -> extractEmployee(rs));
    };

    private Employee extractEmployee(ResultSet rs) throws SQLException {
        Employee employee = new Employee();

        employee.setId(rs.getString(ID_COLUMN_NAME));
        employee.setName(rs.getString(NAME_COLUMN_NAME));
        employee.setSurname(rs.getString(SURNAME_COLUMN_NAME));
        employee.setEmail(rs.getString(EMAIL_COLUMN_NAME));

        Salary salary = new Salary();

        salary.setId(rs.getString(ID_COLUMN_NAME));
        salary.setAmount(rs.getLong(AMOUNT_COLUMN_NAME));

        employee.setSalary(salary);

        return employee;
    }
}
