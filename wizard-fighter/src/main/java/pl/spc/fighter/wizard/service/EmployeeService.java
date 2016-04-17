package pl.spc.fighter.wizard.service;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import pl.spc.fighter.wizard.dao.EmployeeDao;
import pl.spc.fighter.wizard.model.Employee;
import pl.spc.fighter.wizard.model.Employee.Salary;

public class EmployeeService {
    @Inject
    private EmployeeDao dao;

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
