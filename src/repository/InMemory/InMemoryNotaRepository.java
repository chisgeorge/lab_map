package repository.InMemory;

import Domain.Nota;
import Domain.PairID;
import Validator.Validator;
import javafx.util.Pair;
import repository.AbstractRepository;

public class InMemoryNotaRepository extends AbstractRepository<Nota, PairID<Integer,Integer>> {
    public InMemoryNotaRepository(Validator<Nota> validator) {
        super(validator);
    }
}
