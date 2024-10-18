package pl.akademiaspecjalistowit.integracyjnetestowanieaplikacji.book;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookEntity createBook(BookDto bookDto) {
        return bookRepository.save(
            new BookEntity(bookDto.title(), bookDto.author()));
    }

    public List<BookDto> getAllBooks() {
        return bookRepository.findAll()
            .stream()
            .map(e -> new BookDto(e.getTitle(), e.getAuthor()))
            .toList();
    }

    public Optional<BookDto> getBookById(Long id) {
        return bookRepository.findById(id)
            .map(e -> new BookDto(e.getTitle(), e.getAuthor()));
    }
}
