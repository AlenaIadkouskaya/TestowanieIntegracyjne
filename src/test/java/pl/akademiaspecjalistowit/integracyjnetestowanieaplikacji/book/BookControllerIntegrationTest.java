package pl.akademiaspecjalistowit.integracyjnetestowanieaplikacji.book;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void setup() {
        bookRepository.deleteAll();
    }

    @Test
    public void shouldReturnNotFoundWhenBookDoesNotExist() throws Exception {
        mockMvc.perform(get("/books/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateBook() throws Exception {
        //given
        BookDto book = new BookDto("Spring Boot", "John Doe");

        //when then
        mockMvc.perform(post("/books")
                        .header("Client", "Name of client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateBookAndFindItById() throws Exception {
        //given
        BookDto book = new BookDto("Spring Boot", "John Doe");

        //when then
        mockMvc.perform(post("/books")
                        .header("Client", "Name of client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk());

        //when then
        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Spring Boot"))
                .andExpect(content().json(objectMapper.writeValueAsString(book)));
    }

    @Test
    public void shouldCreateBookAndNotification() throws Exception {
        //given
        BookDto bookDto = new BookDto("Spring Boot", "John Doe");
        BookEntity savedBook = new BookEntity(bookDto.title(), bookDto.author());
        //savedBook.setId(1L);

        //BDDMockito.given(bookRepository.save(any(BookEntity.class))).willReturn(savedBook);
        bookRepository.save(savedBook);

        //when then
        mockMvc.perform(post("/books")
                        .header("Client", "Name of client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                        .andExpect(status().isOk());

    }

}