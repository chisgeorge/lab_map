package Service;

import Domain.Student;
import utils.ListEvent;
import utils.ListEventType;
import utils.Observable;
import repository.Repository;
import Validator.ValidationException;
import utils.Observer;

import java.util.*;


public class StudentService implements Observable<Student>{
    private Repository<Student, Integer> repository;
    private ArrayList<Observer<Student>> observers = new ArrayList<>();
    public StudentService(Repository<Student, Integer> repository) {
        this.repository = repository;
    }

    // Add a given entry
    public void add(Student student) throws ValidationException{
            repository.save(student);
            ListEvent<Student> event = createEvent(ListEventType.ADD, student, repository.getAll());
            notifyObservers(event);
    }

    // Return a set with all entries
    public Set<Student> getAll(){
        Set<Student> all = new HashSet<>();
        repository.getAll().forEach(all::add);
        return all;
    }

    // Delete an entry by a given id
    public void delete(Integer integer) throws ValidationException{
        repository.delete(integer);
        ListEvent<Student> event = createEvent(ListEventType.REMOVE, repository.findOne(integer), repository.getAll());
        notifyObservers(event);
    }

    // Update a given entry
    public void update(Student student) throws ValidationException{
        repository.update(student);
        ListEvent<Student> event = createEvent(ListEventType.UPDATE, student, repository.getAll());
        notifyObservers(event);
    }

    public List<Student> filtruStudentiIntreGrupele(int grupaInceput, int grupaSfarsit)
    {
        List<Student> all = new ArrayList<>();
        all.addAll((Collection<? extends Student>) repository.getAll());
        return Filter.filterAndSorter(all,Filter.StudentiIntreGrupele(grupaInceput,grupaSfarsit),Comparator.comparing(Student::getId));
    }
    public List<Student> filtruStudentiContineEmail(String text)
    {
        List<Student> all = new ArrayList<>();
        all.addAll((Collection<? extends Student>) repository.getAll());
        return Filter.filterAndSorter(all,Filter.StudentiContineEmail(text),Comparator.comparing(Student::getId));
    }
    public List<Student> filtruStudentiCareAuCadru(String cadru)
    {
        List<Student> all = new ArrayList<>();
        all.addAll((Collection<? extends Student>) repository.getAll());
        return Filter.filterAndSorter(all,Filter.StudentiCareAuCadru(cadru),Comparator.comparing(Student::getId));
    }

    @Override
    public void addObserver(Observer<Student> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<Student> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(ListEvent<Student> event) {
        observers.forEach(observer->observer.notifyEvent(event));
    }

    private <E> ListEvent<E> createEvent(ListEventType type, final E elem, final Iterable<E> l){
        return new ListEvent<>(type) {
            @Override
            public Iterable<E> getList() {
                return l;
            }

            @Override
            public E getElement() {
                return elem;
            }
        };
    }
}
