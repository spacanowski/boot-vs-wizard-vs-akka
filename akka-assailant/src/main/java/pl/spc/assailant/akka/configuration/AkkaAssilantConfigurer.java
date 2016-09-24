package pl.spc.assailant.akka.configuration;

import java.io.File;
import java.io.IOException;

import org.skife.jdbi.v2.DBI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.inject.AbstractModule;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

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

        DBI dbi = dbi(dataSource(createHikariConfig(configuration.getDbConfiguration(), "AuditPool")));

        bind(EmployeeDao.class).toInstance(dbi.onDemand(EmployeeDao.class));
    }

    private AkkaAssilantConfiguration loadConfiguration() {
        try {
            return new ObjectMapper(new YAMLFactory()).readValue(new File(config), AkkaAssilantConfiguration.class);
        } catch (IOException e) {
            throw new IllegalStateException("No configuration found");
        }
    }

    private HikariDataSource dataSource(HikariConfig config) {
        return new HikariDataSource(config);
    }

    private DBI dbi(HikariDataSource dataSource) {
        return new DBI(dataSource);
    }

    private HikariConfig createHikariConfig(DataSourceConfiguration dbConfiguation, String poolName) {
        HikariConfig config = new HikariConfig("/hikari.properties");

        config.setJdbcUrl(dbConfiguation.getUrl());
        config.setUsername(dbConfiguation.getUser());
        config.setPassword(dbConfiguation.getPassword());
        config.setPoolName(poolName);

        return config;
    }
}
