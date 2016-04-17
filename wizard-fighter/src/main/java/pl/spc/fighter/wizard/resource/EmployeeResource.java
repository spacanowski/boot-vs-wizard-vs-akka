package pl.spc.fighter.wizard.resource;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pl.spc.fighter.wizard.model.Employee;
import pl.spc.fighter.wizard.service.EmployeeService;

@Path("/employees")
public class EmployeeResource {
    @Inject
    private EmployeeService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEmployee(@Valid Employee employee) {
        return Response.created(null).entity(service.addEmployee(employee)).build();
    }

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getEmployee(@PathParam("id") String id) {
        return Response.ok().entity(service.getEmployee(id)).build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getEmployees() {
        return Response.ok().entity(service.getEmployees()).build();
    }
}
