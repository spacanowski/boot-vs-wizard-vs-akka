package pl.spc.fighter.wizard.mappers;

import java.sql.SQLException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class SQLExceptionMapper implements ExceptionMapper<SQLException> {
    @Override
    public Response toResponse(SQLException exception) {
        return Response.status(422).entity(exception.getMessage()).build();
    }
}
