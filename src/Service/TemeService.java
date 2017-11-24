package Service;

import Domain.Student;
import Domain.Tema;
import repository.Repository;
import Validator.ValidationException;

import java.util.*;

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
    public void delete(Integer integer) throws ValidationException{
        repository.delete(integer);
    }

    // Return a set with all entries
    public Set<Tema> getAll(){
        Set<Tema> all = new HashSet<>();
        repository.getAll().forEach(all::add);
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

    public List<Tema> filtruTemaPanaLaDeadline (int deadline)
    {
        List<Tema> all = new ArrayList<>();
        all.addAll((Collection<? extends Tema>) repository.getAll());
        return Filter.filterAndSorter(all,Filter.TemaPanaLaDeadline(deadline),Comparator.comparing(Tema::getId));
    }
    public List<Tema> filtruTemaContineinText (String text)
    {
        List<Tema> all = new ArrayList<>();
        all.addAll((Collection<? extends Tema>) repository.getAll());
        return Filter.filterAndSorter(all,Filter.TemaContineinText(text),Comparator.comparing(Tema::getId));
    }
    public List<Tema> filtruTemaCuIDintre (int IDInceput, int IDSfarsit)
    {
        List<Tema> all = new ArrayList<>();
        all.addAll((Collection<? extends Tema>) repository.getAll());
        return Filter.filterAndSorter(all,Filter.TemaCuIDintre(IDInceput,IDSfarsit),Comparator.comparing(Tema::getId));
    }
}
