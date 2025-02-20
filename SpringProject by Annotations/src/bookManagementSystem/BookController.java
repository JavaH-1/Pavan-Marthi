package bookManagementSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    public void addNewBook(Book book) {
        bookService.addBook(book);
        System.out.println("Book added: " + book);
    }

    public void displayBook(String title) {
        Book book = bookService.getBook(title);
        if (book != null) {
            System.out.println("Book details: " + book);
        } else {
            System.out.println("Book not found!");
        }
    }
}
