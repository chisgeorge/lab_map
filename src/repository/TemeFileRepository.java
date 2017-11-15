package repository;

import Domain.Tema;
import Validator.Validator;
import Validator.ValidationException;

import java.io.*;

public class TemeFileRepository extends InMemoryTemeRepository {

    private String fileName;

    public TemeFileRepository(Validator<Tema> validator, String fileName) {
        super(validator);
        this.fileName = fileName;

        readFromFile();
    }


    private void readFromFile()  {
        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = in.readLine()) != null){
                String[] fields = line.split(";");

                int numarTema = Integer.parseInt(fields[0]);
                String cerintaTema = fields[1];
                int deadline = Integer.parseInt(fields[2]);
                Tema tema = new Tema(numarTema, cerintaTema, deadline);
                try{
                    super.save(tema);
                }catch (ValidationException e) {
                    System.out.println("Date eronate citite.");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul nu a fost gasit.");
        } catch (IOException e) {
            System.out.println("I/O Error.");
        }
    }

    /**
     *  Save data to file
     */
    private void saveToFile() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(fileName, false))) {
            super.getAll().forEach(temaLaborator -> {
                try {
                    out.write(String.valueOf(temaLaborator.getId()) + ";" +
                            temaLaborator.getCerinta() + ";" +
                            String.valueOf(temaLaborator.getDeadline()) + ";"  + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            System.out.println("I/O Error");
        }
    }

    /**
     *  Save an entry
     * @param entity
     * @return entity that was saved
     * @throws ValidationException after validation of entry
     */
    @Override
    public void save(Tema entity) throws ValidationException {
        super.save(entity);
        saveToFile();
    }

    /**
     *  Delete an entry with a given id
     * @param id represents id of entry
     * @return that entry that was removed
     */
    @Override
    public void delete(Integer id) {
        super.delete(id);
        saveToFile();
    }
    /**
     *  Update a given entry
     * @param entity represents new entry
     * @return the updated entry
     * @throws ValidationException if the entry doesn`t exist
     */
    @Override
    public void update(Tema entity) throws ValidationException {
        super.update(entity);
        saveToFile();
    }
}
