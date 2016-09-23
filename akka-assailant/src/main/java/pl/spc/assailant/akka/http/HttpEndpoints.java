package pl.spc.assailant.akka.http;

import static akka.http.javadsl.marshallers.jackson.Jackson.marshaller;
import static akka.http.javadsl.marshallers.jackson.Jackson.unmarshaller;

import java.sql.SQLException;
import java.util.function.BiFunction;

import javax.inject.Inject;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.StatusCode;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.ExceptionHandler;
import akka.http.javadsl.server.Route;
import pl.spc.assailant.akka.model.Employee;
import pl.spc.assailant.akka.service.EmployeeService;

public class HttpEndpoints extends AllDirectives {
    private static final String EMPLOYEES = "employees";
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    @Inject
    private EmployeeService service;

    public Route createRoute() {
        return handleExceptions(exceptionHandler(), () ->
            route(
                pathPrefix(EMPLOYEES, () ->
                    route(
                        get(() ->
                            path(this::getEmployee)),
                        get(this::getEmployees),
                        post(() ->
                            entity(unmarshaller(Employee.class), (employee) -> validate(employee, addEmployee(employee))))))));
    }

    private ExceptionHandler exceptionHandler() {
        final BiFunction<StatusCode, String, Route> mapper = (status, message) ->
            complete(HttpResponse.create().withStatus(status).withEntity(message));

        return ExceptionHandler.newBuilder()
            .match(IllegalArgumentException.class, ex -> mapper.apply(StatusCodes.BAD_REQUEST, ex.getMessage()))
            .match(SQLException.class, ex -> mapper.apply(StatusCodes.UNPROCESSABLE_ENTITY, ex.getMessage()))
            .match(ValidationException.class, ex -> mapper.apply(StatusCodes.UNPROCESSABLE_ENTITY, ex.getMessage()))
            .build();
    }

    private Route validate(Employee employee, Route route) {
        VALIDATOR.validate(employee);
        return route;
    }

    private Route getEmployee(String id) {
        return completeOK(service.getEmployee(id), marshaller());
    }

    private Route getEmployees() {
        return completeOK(service.getEmployees(), marshaller());
    }

    private Route addEmployee(Employee employee) {
        return complete(StatusCodes.CREATED, service.addEmployee(employee), marshaller());
    }
}