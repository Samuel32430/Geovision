package com.example.geovision.generic;

import java.util.Optional;

public interface CrudService<T, ID> {
    T save(T entity);
    T findById(ID id);
    void delete(ID id);
    Optional<T> read(ID id);
    Iterable<T> findAll();
}
