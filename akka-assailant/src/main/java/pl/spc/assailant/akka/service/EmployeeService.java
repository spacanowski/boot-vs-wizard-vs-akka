package pl.spc.assailant.akka.service;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.skife.jdbi.v2.sqlobject.Transaction;

import pl.spc.assailant.akka.dao.EmployeeDao;
import pl.spc.assailant.akka.model.Employee;
import pl.spc.assailant.akka.model.Employee.Salary;

public class EmployeeService {
    @Inject
    private EmployeeDao dao;

    @Transaction
    public Employee addEmployee(Employee employee) {
        String id = UUID.randomUUID().toString();
        employee.setId(id);
        Salary salary = employee.getSalary();
        salary.setId(id);

        dao.addEmployee(employee);
        dao.addSalary(salary);

        return employee;
    }

    public Employee getEmployee(String id) {
        return dao.getEmployee(id);
    }

    public List<Employee> getEmployees() {
        return dao.getEmployees();
    }
}
