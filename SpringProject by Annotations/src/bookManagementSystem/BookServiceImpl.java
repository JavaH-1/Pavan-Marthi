package bookManagementSystem;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {

    private final Map<String, Book> bookRepository = new HashMap<>();

    @Override
    public void addBook(Book book) {
        bookRepository.put(book.getTitle(), book);
    }

    @Override
    public Book getBook(String title) {
        return bookRepository.get(title);
    }
}
