package pl.spc.assailant.akka.configuration;

import java.io.File;
import java.io.IOException;

import org.skife.jdbi.v2.DBI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.inject.AbstractModule;

import pl.spc.assailant.akka.configuration.AkkaAssilantConfiguration.DataSourceConfiguration;
import pl.spc.assailant.akka.dao.EmployeeDao;
import pl.spc.assailant.akka.http.HttpEndpoints;
import pl.spc.assailant.akka.service.EmployeeService;

public class AkkaAssilantConfigurer extends AbstractModule {
    private String config;

    public AkkaAssilantConfigurer(String config) {
        this.config = config;
    }

    @Override
    protected void configure() {
        AkkaAssilantConfiguration configuration = loadConfiguration();

        bind(HttpEndpoints.class);
        bind(EmployeeService.class);
        bind(AkkaAssilantConfiguration.class).toInstance(configuration);
        bind(EmployeeDao.class).toInstance(dbi(configuration).onDemand(EmployeeDao.class));
    }

    private DBI dbi(AkkaAssilantConfiguration configuration) {
        DataSourceConfiguration dbConfiguration = configuration.getDbConfiguration();
        return new DBI(dbConfiguration.getUrl(), dbConfiguration.getUser(), dbConfiguration.getPassword());
    }

    private AkkaAssilantConfiguration loadConfiguration() {
        try {
            return new ObjectMapper(new YAMLFactory()).readValue(new File(config), AkkaAssilantConfiguration.class);
        } catch (IOException e) {
            throw new IllegalStateException("No configuration found");
        }
    }
}
