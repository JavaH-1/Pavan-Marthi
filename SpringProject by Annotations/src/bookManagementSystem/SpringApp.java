package bookManagementSystem;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringApp {

    public static void main(String[] args) {
        // Create the application context
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Get the BookController bean
        BookController controller = context.getBean(BookController.class);

        // Add new books
        controller.addNewBook(new Book("1984", "George Orwell"));
        controller.addNewBook(new Book("The Catcher in the Rye", "J.D. Salinger"));

        // Retrieve and display a book by title
        controller.displayBook("1984");
        controller.displayBook("Moby Dick");

        // Close the context
        context.close();
    }
}
