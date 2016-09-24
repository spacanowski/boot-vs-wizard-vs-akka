package pl.spc.fighter.boot.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatabaseConfiguration {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Bean(destroyMethod = "close")
    @Primary
    public DataSource dataSource() {
        return new HikariDataSource(createHikariConfig());
    }

    private HikariConfig createHikariConfig() {
        HikariConfig config = new HikariConfig("/hikari.properties");

        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setPoolName("HikariPool");

        return config;
    }
}
