package repository.FileRepository;

import Domain.Nota;
import Domain.PairID;
import Validator.Validator;
import Validator.ValidationException;
import repository.InMemory.InMemoryNotaRepository;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class NotaFileRepository extends InMemoryNotaRepository {
    private String fileName;

    public NotaFileRepository(Validator<Nota> validator, String fileName) {
        super(validator);
        this.fileName = fileName;

        readFromFile();
    }

    /**
     *  Read data from file
     */
    private void readFromFile(){
        Path path = Paths.get(fileName);
        Stream<String> lines;
        try{
            lines = Files.lines(path);
            lines.forEach(line->{
                String[] fields = line.split(";");
                PairID<Integer, Integer> id= new PairID<Integer,Integer>(Integer.parseInt(fields[0]),Integer.parseInt(fields[1]));
                int SaptPreluare = Integer.parseInt(fields[2]);
                int nota = Integer.parseInt(fields[3]);

                Nota nota_object = new Nota(id,  nota, SaptPreluare);
                try {
                    super.save(nota_object);
                }catch (ValidationException e) {
                    System.out.println("Date eronate citite.");
                }
            });
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul nu a fost gasit.");
        } catch (IOException e) {
            System.out.println("I/O Error.");
        }
    }


    private void saveToFile() {/*
     *  Save data to file
     */
        try (BufferedWriter out = new BufferedWriter(new FileWriter(fileName, false))) {
            super.getAll().forEach(nota -> {
                try {
                    out.write(nota.getId().getKey() + ";" +
                            nota.getId().getValue() + ";" +
                            String.valueOf(nota.getSaptPreluare()) + ";" +
                            String.valueOf(nota.getNota()) + "\n");
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
     * @throws ValidationException after validation of entry
     */
    @Override
    public void save(Nota entity) throws ValidationException {
        super.save(entity);
        saveToFile();
    }

    /**
     *  Delete an entry with a given id
     * @param id represents id of entry
     */
    @Override
    public void delete(PairID<Integer,Integer> id) throws ValidationException{
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
    public void update(Nota entity) throws ValidationException {
        super.update(entity);
        saveToFile();
    }
}
