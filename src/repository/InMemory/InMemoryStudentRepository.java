package repository.InMemory;

import Domain.Student;
import Validator.Validator;
import repository.AbstractRepository;

public class InMemoryStudentRepository extends AbstractRepository<Student, Integer> {
    public InMemoryStudentRepository(Validator<Student> validator) { super(validator); }
}
