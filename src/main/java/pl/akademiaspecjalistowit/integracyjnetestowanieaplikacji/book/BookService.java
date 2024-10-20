package pl.akademiaspecjalistowit.integracyjnetestowanieaplikacji.book;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.akademiaspecjalistowit.integracyjnetestowanieaplikacji.EventCreatingBook;
import pl.akademiaspecjalistowit.integracyjnetestowanieaplikacji.notification.NotificationService;

@AllArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final NotificationService notificationService;

    @Transactional
    public BookEntity createBook(BookDto bookDto, String client) {
        BookEntity bookToSave = bookRepository.save(
                new BookEntity(bookDto.title(), bookDto.author()));
        EventCreatingBook eventCreatingBook = new EventCreatingBook(bookToSave.getId(), client);
        notificationService.accept(eventCreatingBook);
        return bookToSave;
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
