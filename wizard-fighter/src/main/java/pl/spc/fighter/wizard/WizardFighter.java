package pl.spc.fighter.wizard;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.skife.jdbi.v2.DBI;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import pl.spc.fighter.wizard.configuration.WizardConfiguration;
import pl.spc.fighter.wizard.dao.EmployeeDao;
import pl.spc.fighter.wizard.service.EmployeeService;

public class WizardFighter extends Application<WizardConfiguration> {
    public static void main(String[] args) throws Exception {
        new WizardFighter().run(args);
    }

    @Override
    public void run(WizardConfiguration configuration, Environment environment) throws Exception {
        environment.jersey().packages("pl.spc");

        HikariConfig config = createHikariConfig(configuration, environment);
        HikariDataSource dataSource = new HikariDataSource(config);
        DBI dbi = new DBI(dataSource);

        environment.jersey().register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(dbi.onDemand(EmployeeDao.class)).to(EmployeeDao.class);
                bind(EmployeeService.class).to(EmployeeService.class);
            }
        });
    }

    private HikariConfig createHikariConfig(WizardConfiguration configuration, Environment environment) {
        HikariConfig config = new HikariConfig("/hikari.properties");
        config.setJdbcUrl(configuration.getDataSourceFactory().getUrl());
        config.setUsername(configuration.getDataSourceFactory().getUser());
        config.setPassword(configuration.getDataSourceFactory().getPassword());
        config.setPoolName("HikariPool");
        config.setMetricRegistry(environment.metrics());
        return config;
    }
}
