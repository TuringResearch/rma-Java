package br.pro.turing.rma.core.repository;

import java.util.List;
import java.util.Optional;

public interface MongoRepository<Entity, ID> {
    <S extends Entity> S save(S var1);

    <S extends Entity> Iterable<S> saveAll(Iterable<S> var1);

    Optional<Entity> findById(ID var1);

    boolean existsById(ID var1);

    List<Entity> findAll();

    long count();

    void deleteById(ID var1);

    void delete(Entity var1);

    void deleteAll(Iterable<? extends Entity> var1);

    void deleteAll();
}