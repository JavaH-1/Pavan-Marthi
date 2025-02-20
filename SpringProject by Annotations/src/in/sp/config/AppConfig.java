package in.sp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "in.sp.beans")
public class AppConfig
{
    // No bean definitions are required here
}