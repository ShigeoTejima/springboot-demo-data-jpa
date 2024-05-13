package com.example.demo.repository;

import com.example.demo.entity.Foo;
import com.example.demo.entity.FooExt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FooRepository extends JpaRepository<Foo, Long> {

    List<Foo> findByFooExt(FooExt fooExt);

    List<Foo> findByNameIn(List<String> names);

    List<Foo> findByFooExt_ExtValue(String extValue);

    List<Foo> findByFooExt_extValueIn(List<String> extValues);

    @Query("SELECT f FROM Foo f LEFT JOIN FooExt fe ON f.id = fe.fooId WHERE fe.extValue IN :extValues")
    List<Foo> findByExtValueIn(@Param("extValues") List<String> extValues);
}
