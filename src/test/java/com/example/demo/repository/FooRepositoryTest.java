package com.example.demo.repository;

import com.example.demo.entity.Foo;
import com.example.demo.entity.FooExt;
import com.example.demo.entity.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@SpringBootTest
public class FooRepositoryTest {
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
    FooRepository fooRepository;

    @Test
    @Sql("classpath:demo-data-foo.sql")
    void demo() {
        List<Foo> actual = fooRepository.findAll();

        assertIterableEquals(
                List.of(foo(1L, "Foo"),
                        foo(2L, "Bar")),
                actual);

        actual.stream().forEach(System.out::println);
    }

    @Test
    @Sql("classpath:demo-data-foo.sql")
    void demo2() {
        FooExt fooExt = new FooExt();
        fooExt.setExtValue("ExtFoo");

        List<Foo> actual = fooRepository.findByFooExt(fooExt);

        actual.stream().forEach(System.out::println);
    }

    @Test
    @Sql("classpath:demo-data-foo.sql")
    void demo3() {
        List<Foo> actual = fooRepository.findByNameIn(List.of("Foo", "Bar"));

        actual.stream().forEach(System.out::println);
    }

    /*
     * org.hibernate.SQL           : select f1_0.id,f1_0.name from foo f1_0 left join foo_ext fe1_0 on f1_0.id=fe1_0.id where fe1_0.ext_value=?
     * org.hibernate.orm.jdbc.bind : binding parameter (1:VARCHAR) <- [ExtFoo]
     * org.hibernate.SQL           : select fe1_0.id,fe1_0.ext_value,f1_0.id,f1_0.name,fe1_0.foo_id from foo_ext fe1_0 left join foo f1_0 on f1_0.id=fe1_0.id where fe1_0.id=?
     * org.hibernate.orm.jdbc.bind : binding parameter (1:BIGINT) <- [1]
     */
    @Test
    @Sql("classpath:demo-data-foo.sql")
    void demo4() {
        List<Foo> actual = fooRepository.findByFooExt_ExtValue("ExtFoo");

        actual.stream().forEach(System.out::println);
    }

    /*
     * org.hibernate.SQL           : select f1_0.id,f1_0.name from foo f1_0 left join foo_ext fe1_0 on f1_0.id=fe1_0.id where fe1_0.ext_value in (?,?)
     * org.hibernate.orm.jdbc.bind : binding parameter (1:VARCHAR) <- [ExtFoo]
     * org.hibernate.orm.jdbc.bind : binding parameter (2:VARCHAR) <- [ExtBar]
     * org.hibernate.SQL           : select fe1_0.id,fe1_0.ext_value,f1_0.id,f1_0.name,fe1_0.foo_id from foo_ext fe1_0 left join foo f1_0 on f1_0.id=fe1_0.id where fe1_0.id=?
     * org.hibernate.orm.jdbc.bind : binding parameter (1:BIGINT) <- [1]
     * org.hibernate.SQL           : select fe1_0.id,fe1_0.ext_value,f1_0.id,f1_0.name,fe1_0.foo_id from foo_ext fe1_0 left join foo f1_0 on f1_0.id=fe1_0.id where fe1_0.id=?
     * org.hibernate.orm.jdbc.bind : binding parameter (1:BIGINT) <- [2]
     */
    @Test
    @Sql("classpath:demo-data-foo.sql")
    void demo5() {
        List<Foo> actual = fooRepository.findByFooExt_extValueIn(List.of("ExtFoo", "ExtBar"));

        actual.stream().forEach(System.out::println);
    }

    /*
     * org.hibernate.SQL           : select f1_0.id,f1_0.name from foo f1_0 left join foo_ext fe1_0 on f1_0.id=fe1_0.foo_id where fe1_0.ext_value in (?,?)
     * org.hibernate.orm.jdbc.bind : binding parameter (1:VARCHAR) <- [ExtFoo]
     * org.hibernate.orm.jdbc.bind : binding parameter (2:VARCHAR) <- [ExtBar]
     * org.hibernate.SQL           : select fe1_0.id,fe1_0.ext_value,f1_0.id,f1_0.name,fe1_0.foo_id from foo_ext fe1_0 left join foo f1_0 on f1_0.id=fe1_0.id where fe1_0.id=?
     * org.hibernate.orm.jdbc.bind : binding parameter (1:BIGINT) <- [1]
     * org.hibernate.SQL           : select fe1_0.id,fe1_0.ext_value,f1_0.id,f1_0.name,fe1_0.foo_id from foo_ext fe1_0 left join foo f1_0 on f1_0.id=fe1_0.id where fe1_0.id=?
     * org.hibernate.orm.jdbc.bind : binding parameter (1:BIGINT) <- [2]
     */
    @Test
    @Sql("classpath:demo-data-foo.sql")
    void demo6() {
        List<Foo> actual = fooRepository.findByExtValueIn(List.of("ExtFoo", "ExtBar"));

        actual.stream().forEach(System.out::println);
    }

    private Foo foo(Long id, String name) {
        Foo foo = new Foo();
        foo.setId(id);
        foo.setName(name);
        return foo;
    }
}
