package by.it.spring.dao;

import by.it.spring.models.Book;
import by.it.spring.models.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book where id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name,author,year) VALUES (?,?,?)",
                book.getName(), book.getAuthor(), book.getYear());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE from Book where id=?", id);
    }

    public void update(Book book) {
        jdbcTemplate.update("UPDATE  Book SET name=?,author=?,year=? where id=?", book.getName(),
                book.getAuthor(), book.getYear(), book.getId());
    }

    public Optional<Person> getOwner(int id) {
        return jdbcTemplate.query("Select Person.*  from Book  join Person on Person.id = Book.person_id where Book.id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void release(int id) {
        jdbcTemplate.update("UPDATE Book SET person_id=NULL where id=?", id);

    }

    public void assign(int id, Person person) {
        jdbcTemplate.update("UPDATE Book SET person_id=? where id=?", person.getId(), id);
    }
}
