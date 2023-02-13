package by.it.spring.dao;

import by.it.spring.models.Book;
import by.it.spring.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person where id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name,birth_date) VALUES (?,?)",
                person.getName(), person.getBirth_date());
    }

    public void update(Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET name=?,birth_date=? WHERE id=?", updatedPerson.getName(),
                updatedPerson.getBirth_date(), updatedPerson.getId());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person where id=?", id);
    }

    public List<Book> getBooksByPerson(int id) {
        return jdbcTemplate.query("Select *  from Book  where person_id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Person> checkNameUnique(Person person) {
        return jdbcTemplate.query("SELECT * from Person where name=?", new Object[]{person.getName()},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
}
