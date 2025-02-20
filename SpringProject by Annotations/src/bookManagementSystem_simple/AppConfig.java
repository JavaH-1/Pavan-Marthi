package bookManagementSystem_simple;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // Registering a Book bean in the Spring context
    @Bean
    public Book book() {
        // Creating a new Book object
        return new Book("1984", "George Orwell");
    }
}
