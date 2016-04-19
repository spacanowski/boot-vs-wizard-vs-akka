package pl.spc.fighter.boot.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.spc.fighter.boot.dao.EmployeeDao;
import pl.spc.fighter.boot.model.Employee;
import pl.spc.fighter.boot.model.Employee.Salary;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeDao dao;

    @Transactional
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
