package repository;

import Validator.ValidationException;

public interface Repository<E, ID> {
    long size();
    void save(E entity) throws ValidationException;
    void delete(ID id) throws ValidationException;
    E findOne(ID id);
    void update(E entity) throws ValidationException;
    Iterable<E> getAll();
}
