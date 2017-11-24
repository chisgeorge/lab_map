package repository;

import Domain.HasID;
import Validator.Validator;
import Validator.ValidationException;

import java.util.TreeMap;

public abstract class AbstractRepository <E extends HasID<ID>, ID> implements Repository<E, ID> {
    private TreeMap<ID, E> entities = new TreeMap<ID, E>();
    private Validator<E> validator;

    public AbstractRepository(Validator<E> validator){
        this.validator = validator;
    }

    /*
    *   Return the size of entities
    */
    @Override
    public long size() {
        return entities.size();
    }

    /**
     *  Save entity as a new entry in repository
     * @param entity
     * @return null if entity was addes
     * @return entity if entity exists in repository
     * @throws ValidationException if entity is not valid
     */
    @Override
    public void save(E entity) throws ValidationException {
        validator.validate(entity);
        //if(entities.containsKey(entity.getId()))
            //entities.get(entity.getId());
        entities.put(entity.getId(),entity);
    }

    /**
     *  Remove an element with a given ID
     * @param id
     * @return removed element
     */
    @Override
    public void delete(ID id) throws ValidationException {

        if (entities.containsKey(id)) {
            entities.remove(id);
        }
        else throw new ValidationException("ID-ul nu exista");
    }

    @Override
    public void update(E entity) throws ValidationException{
        validator.validate(entity);
        if (entities.containsKey(entity.getId())) {
            entities.put(entity.getId(), entity);
        }
        else throw new ValidationException("ID-ul nu exista");

    }
    /**
     *  Find an entry by a given ID
     * @param id
     * @return the entry that has given ID
     */
    @Override
    public E findOne(ID id) {
        return entities.get(id);
    }

    // Return all values of repository
    @Override
    public Iterable<E> getAll() {
        return entities.values();
    }
}
