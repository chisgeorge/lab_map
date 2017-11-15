package repository;

import Domain.Tema;
import Validator.Validator;

public class InMemoryTemeRepository extends AbstractRepository<Tema, Integer> {
    public InMemoryTemeRepository(Validator<Tema> validator) {
        super(validator);
    }
}
