package bookManagementSystem;

public interface BookService {
    void addBook(Book book);
    Book getBook(String title);
}

