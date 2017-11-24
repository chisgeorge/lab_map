package repository.FileRepository;

import Domain.Student;
import Validator.Validator;
import Validator.ValidationException;
import repository.InMemory.InMemoryStudentRepository;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class StudentFileRepository extends InMemoryStudentRepository {

    private String fileName;

    public StudentFileRepository(Validator<Student> validator, String fileName) {
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
                int idStudent = Integer.parseInt(fields[0]);
                String numeStudent = fields[1];
                int grupaStudent = Integer.parseInt(fields[2]);
                String emailStudent = fields[3];
                String cadruStudent = fields[4];

                Student student = new Student(idStudent, numeStudent, grupaStudent, emailStudent, cadruStudent);
                try {
                    super.save(student);
                } catch (ValidationException e) {
                    System.out.println("Date eronate citite.");
                }
            });
            } catch (IOException e) {
                System.out.println("I/O Error.");
        }
    }


    private void saveToFile() {/*
     *  Save data to file
     */
        try (BufferedWriter out = new BufferedWriter(new FileWriter(fileName, false))) {
            super.getAll().forEach(student -> {
                try {
                    out.write(student.getId() + ";" +
                            student.getNume() + ";" +
                            String.valueOf(student.getGrupa()) + ";" +
                            student.getEmail() + ";" +
                            student.getCadruDidactic() + "\n");
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
     * @param entity represends the Student
     * @throws ValidationException after validation of entry
     */
    @Override
    public void save(Student entity) throws ValidationException {
        super.save(entity);
        saveToFile();
    }

    /**
     *  Delete an entry with a given id
     * @param id represents id of entry
     */
    @Override
    public void delete(Integer id) throws ValidationException {
        super.delete(id);
        saveToFile();
    }

    /**
     *  Update a given entry
     * @param entity represents new entry
     * @throws ValidationException if the entry doesn`t exist
     */
    @Override
    public void update(Student entity) throws ValidationException {
        super.update(entity);
        saveToFile();
    }
}
