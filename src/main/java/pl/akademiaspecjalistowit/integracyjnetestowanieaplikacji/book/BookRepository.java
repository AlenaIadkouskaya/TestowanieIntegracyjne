package pl.akademiaspecjalistowit.integracyjnetestowanieaplikacji.book;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
