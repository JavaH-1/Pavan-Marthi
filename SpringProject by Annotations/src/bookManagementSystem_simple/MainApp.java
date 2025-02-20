package bookManagementSystem_simple;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        	Book book = context.getBean(Book.class);
        	System.out.println(book);
        	context.close();
    	}
	}
