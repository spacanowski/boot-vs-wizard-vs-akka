package pl.spc.fighter.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
public class BootContender extends WebMvcConfigurerAdapter {
    public static void main(String[] args) {
        SpringApplication.run(BootContender.class, args);
    }
}
