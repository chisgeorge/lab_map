package repository;

import Domain.Nota;
import Domain.PairID;
import Validator.Validator;
import javafx.util.Pair;

public class InMemoryNotaRepository extends AbstractRepository<Nota, PairID<Integer,Integer>> {
    public InMemoryNotaRepository(Validator<Nota> validator) {
        super(validator);
    }
}
