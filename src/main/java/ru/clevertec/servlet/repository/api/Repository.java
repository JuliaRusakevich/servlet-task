package ru.clevertec.servlet.repository.api;

import java.util.List;
import java.util.Optional;

public interface Repository<I, E> {

    List<E> findAll();

    Optional<E> findById(I id);

    boolean delete(I id);

    void update(E entity);

    E save(E entity);
}
