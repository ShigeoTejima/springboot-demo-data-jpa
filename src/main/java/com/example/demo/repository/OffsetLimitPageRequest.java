package com.example.demo.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Objects;

public class OffsetLimitPageRequest implements Pageable, Serializable {

    private final int limit;
    private final long offset;
    private final Sort sort;

    public OffsetLimitPageRequest(long offset, int limit, Sort sort) {
        this.offset = offset;
        this.limit = limit;
        this.sort = sort;
    }

    public OffsetLimitPageRequest(int offset, int limit) {
        this(offset, limit, Sort.unsorted());
    }

    @Override
    public int getPageNumber() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Pageable previousOrFirst() {
        throw new UnsupportedOperationException();
    }

    public OffsetLimitPageRequest previous() {
        return hasPrevious() ? new OffsetLimitPageRequest(getOffset() - getPageSize(), getPageSize(), getSort()) : this;
    }

    @Override
    public Pageable first() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Pageable withPage(int pageNumber) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasPrevious() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OffsetLimitPageRequest that = (OffsetLimitPageRequest) o;
        return limit == that.limit && offset == that.offset && Objects.equals(sort, that.sort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(limit, offset, sort);
    }

}
