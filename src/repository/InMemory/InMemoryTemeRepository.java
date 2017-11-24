package repository.InMemory;

import Domain.Tema;
import Validator.Validator;
import repository.AbstractRepository;

public class InMemoryTemeRepository extends AbstractRepository<Tema, Integer> {
    public InMemoryTemeRepository(Validator<Tema> validator) {
        super(validator);
    }
}
