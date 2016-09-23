package pl.spc.fighter.wizard.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import pl.spc.fighter.wizard.model.Employee;
import pl.spc.fighter.wizard.model.Employee.Salary;

public interface EmployeeDao {
    @SqlUpdate("insert into employees values (:id, :name, :surname, :email)")
    void addEmployee(@BindBean Employee e);

    @SqlUpdate("insert into salary values (:id, :amount)")
    void addSalary(@BindBean Salary s);

    @SqlQuery("select e.id, e.name, e.surname, e.email, s.amount from employees e join salary s on e.id = s.id where e.id = :id")
    @Mapper(EmployeeMapper.class)
    Employee getEmployee(@Bind("id") String id);

    @SqlQuery("select e.id, e.name, e.surname, e.email, s.amount from employees e join salary s on e.id = s.id")
    @Mapper(EmployeeMapper.class)
    List<Employee> getEmployees();
}
