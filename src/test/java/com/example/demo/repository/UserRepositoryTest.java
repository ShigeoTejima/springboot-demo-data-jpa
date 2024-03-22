package com.example.demo.repository;

import com.example.demo.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
public class UserRepositoryTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
    "postgres:15-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setup() {
    }

    @Test
    @Sql("classpath:demo-data.sql")
    void demo() {
        List<User> actual = userRepository.findAll();

        assertIterableEquals(
                List.of(new User(1L, "Foo"), new User(2L, "Bar")),
                actual);

    }

    @Test
    @Sql("classpath:demo-data10.sql")
    void demoPagination() {
        Page<User> actual = userRepository.findAll(PageRequest.of(1, 5));

        assertIterableEquals(List.of(
                new User(6L, "Foo 6"),
                new User(7L, "Foo 7"),
                new User(8L, "Foo 8"),
                new User(9L, "Foo 9"),
                new User(10L, "Foo 10")
            ),
            actual.toList());
    }

    @Test
    @Sql("classpath:demo-data10.sql")
    void demoOffsetLimit() {
        OffsetLimitPageRequest offsetBasedPageRequest = new OffsetLimitPageRequest(5, 3);

        Page<User> users = userRepository.findAll(offsetBasedPageRequest);

        assertIterableEquals(List.of(
                        new User(6L, "Foo 6"),
                        new User(7L, "Foo 7"),
                        new User(8L, "Foo 8")
                ),
                users.toList());
    }

    @Autowired
    EntityManager em;

    @Test
    @Sql("classpath:demo-data10.sql")
    void demoUsingQuery() {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class)
                .setFirstResult(5)
                .setMaxResults(3);

        List<User> users = query.getResultList();

        assertIterableEquals(List.of(
                new User(6L, "Foo 6"),
                new User(7L, "Foo 7"),
                new User(8L, "Foo 8")
            ),
            users
        );
    }
}
