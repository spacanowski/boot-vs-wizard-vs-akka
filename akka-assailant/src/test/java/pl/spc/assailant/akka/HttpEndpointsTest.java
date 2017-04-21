package pl.spc.assailant.akka;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import pl.spc.assailant.akka.http.HttpEndpoints;
import pl.spc.assailant.akka.service.EmployeeService;

@RunWith(MockitoJUnitRunner.class)
public class HttpEndpointsTest extends JUnitRouteTest {
    @Mock
    private EmployeeService service;
    @InjectMocks
    private HttpEndpoints httpEndpoints;
    private TestRoute appRoute;

    @Before
    public void setUp() {
        appRoute = testRoute(httpEndpoints.createRoute());
    }

    @Test
    public void testNok() {
        appRoute.run(HttpRequest.GET("/nonExisting/"))
            .assertStatusCode(StatusCodes.NOT_FOUND); // 404
    }

    @Test
    public void testOk() {
        appRoute.run(HttpRequest.GET("/employees/"))
            .assertStatusCode(StatusCodes.OK) // 404
            .assertEntity("[]");
    }
}
