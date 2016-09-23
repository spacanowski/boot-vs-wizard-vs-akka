package pl.spc.fighter.wizard;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.skife.jdbi.v2.DBI;

import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
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

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");

        environment.jersey().register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(jdbi.onDemand(EmployeeDao.class)).to(EmployeeDao.class);
                bind(EmployeeService.class).to(EmployeeService.class);
            }
        });
    }
}
