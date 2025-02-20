package basicExample;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {

    public static void main(String[] args) {
    
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Person person = context.getBean(Person.class);

        System.out.println(person);

        context.close();
    }
}
