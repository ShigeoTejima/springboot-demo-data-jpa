package com.example.demo.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.Reference;

import java.util.Objects;

@Entity
@Table(name = "foo_ext")
public class FooExt {

    @Id
    private Long id;

    @Column(nullable = false)
    private Long fooId;

    @Column(nullable = false)
    private String extValue;

    @OneToOne
    @JoinColumn(name = "id")
    private Foo foo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFooId() {
        return fooId;
    }

    public void setFooId(Long fooId) {
        this.fooId = fooId;
    }

    public String getExtValue() {
        return extValue;
    }

    public void setExtValue(String extValue) {
        this.extValue = extValue;
    }

    public Foo getFoo() {
        return foo;
    }

    public void setFoo(Foo foo) {
        this.foo = foo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FooExt fooExt = (FooExt) o;
        return Objects.equals(id, fooExt.id) && Objects.equals(fooId, fooExt.fooId) && Objects.equals(extValue, fooExt.extValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fooId, extValue);
    }

    @Override
    public String toString() {
        return "FooExt{" +
                "id=" + id +
                ", fooId=" + fooId +
                ", extValue='" + extValue + '\'' +
                '}';
    }
}
