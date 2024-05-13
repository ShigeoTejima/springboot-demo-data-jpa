package com.example.demo.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "foo")
public class Foo {
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(mappedBy = "foo")
    private FooExt fooExt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FooExt getFooExt() {
        return fooExt;
    }

    public void setFooExt(FooExt fooExt) {
        this.fooExt = fooExt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Foo foo = (Foo) o;
        return Objects.equals(id, foo.id) && Objects.equals(name, foo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Foo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fooExt=" + fooExt +
                '}';
    }
}
