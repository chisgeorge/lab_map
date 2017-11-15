package Service;

import Domain.Tema;
import repository.Repository;
import Validator.ValidationException;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class TemeService {

    private int saptamanaCurenta;
    private Repository<Tema, Integer> repository;

    public TemeService(Repository<Tema, Integer> repository) {
        saptamanaCurenta = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)-39;
        this.repository = repository;
    }

    // Add a given entry
    public void add(Tema tema) throws ValidationException{
        repository.save(tema);
    }

    // Delete an entry by a given id
    public void delete(Integer integer){
        repository.delete(integer);
    }

    // Return a set with all entries
    public Set<Tema> getAll(){
        Set<Tema> all = new HashSet<>();
        repository.getAll().forEach(temaLaborator -> all.add(temaLaborator));
        return all;
    }

    // Update a given entry
    public void update(Tema tema) throws  ValidationException{
        repository.update(tema);
    }

    public void prelungire(Integer id, int saptamanaNoua) throws ValidationException{
        Tema tema = repository.findOne(id);
        if ((saptamanaNoua > tema.getDeadline()) && (tema.getDeadline() >= saptamanaCurenta-1))
            throw new ValidationException("Saptamana data nu poate fi actualizata deoarce depaseste termenul");
        tema.setDeadline(saptamanaNoua);
        repository.update(tema);
    }
}
