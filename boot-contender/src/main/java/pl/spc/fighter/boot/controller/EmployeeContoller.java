package pl.spc.fighter.boot.controller;

import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.spc.fighter.boot.model.Employee;
import pl.spc.fighter.boot.service.EmployeeService;

@RestController
@RequestMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeContoller {
    @Autowired
    private EmployeeService service;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Employee addEmployee(@RequestBody @Valid Employee employee) {
        return service.addEmployee(employee);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Employee getEmployee(@PathParam("id") String id) {
        return service.getEmployee(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Employee> getEmployees() {
        return service.getEmployees();
    }
}
