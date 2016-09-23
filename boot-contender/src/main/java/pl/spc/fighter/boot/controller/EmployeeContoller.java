package pl.spc.fighter.boot.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import pl.spc.fighter.boot.model.Employee;
import pl.spc.fighter.boot.service.EmployeeService;

@RestController
@RequestMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeContoller {
    @Autowired
    private EmployeeService service;

    @Timed
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody @Valid Employee employee) {
        return service.addEmployee(employee);
    }

    @Timed
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Employee getEmployee(@PathVariable String id) {
        return service.getEmployee(id);
    }

    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public List<Employee> getEmployees() {
        return service.getEmployees();
    }
}
