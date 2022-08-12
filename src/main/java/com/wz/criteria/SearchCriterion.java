package com.wz.criteria;

import com.wz.exception.InvalidInputException;

import java.util.Collection;

public interface SearchCriterion<T, V> {
    Collection<T> search(V dataSource) throws InvalidInputException;
}
