package UI.Console;

import Domain.Nota;
import Domain.Student;
import Domain.Tema;
import Domain.PairID;
import repository.FileRepository.NotaFileRepository;
import repository.FileRepository.StudentFileRepository;
import repository.FileRepository.TemeFileRepository;
import Service.NotaService;
import Service.StudentService;
import Service.TemeService;
import UI.Console.Menu.*;
import Validator.ValidatorTeme;
import Validator.ValidatorStudent;
import Validator.ValidationException;
import Validator.ValidatorNota;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Console {

    private StudentFileRepository repoStud = new StudentFileRepository(new ValidatorStudent(),"Student.txt");
    private TemeFileRepository repoTeme = new TemeFileRepository(new ValidatorTeme(),"Teme.txt");

    private StudentService controllerStudent = new StudentService(repoStud);
    private TemeService controllerTeme = new TemeService(repoTeme);
    private NotaService controllerNote = new NotaService(new NotaFileRepository(new ValidatorNota(),"Catalog.txt"),repoStud,repoTeme);

    private MenuCommand mainMenu;

    private Scanner scanner = new Scanner(System.in);

    public class ReadStudentCommand implements Command{
        @Override
        public void execute() {
            int idStudent, grupaStudent;
            String nume, email, cadruDidactic;

            System.out.print("Introduceti  id student = ");
            idStudent = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Introduceti nume student = ");
            nume=scanner.nextLine();
            System.out.println("Intoduceti grupa studentului = ");
            grupaStudent = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Introduceti email-ul studentului = ");
            email = scanner.nextLine();
            System.out.println("Introduceti cadrul didactic al studentului = ");
            cadruDidactic = scanner.nextLine();
            try {
                controllerStudent.add(new Student(idStudent, nume, grupaStudent, email, cadruDidactic));
                System.out.println("S-a citit un student ");
            }catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public class ReadTemaCommand implements Command{
        @Override
        public void execute() {
            int numarTema, deadline;
            String cerinta;

            System.out.print("Introduceti  numar tema =");
            numarTema = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Introduceti cerinta tema =");
            cerinta = scanner.nextLine();
            System.out.println("Intoduceti deadline tema =");
            deadline = scanner.nextInt();

            try {
                controllerTeme.add(new Tema(numarTema, cerinta, deadline));
                System.out.println("S-a citit o tema ");
            }catch (ValidationException e){
                System.out.println(e.getMessage());
            }

        }
    }

    public class ReadNotaCommand implements Command{
        @Override
        public void execute() {
            int idStudent, numarTema, nota;
            String observatii;

            System.out.print("Introduceti  id-ul studentului =");
            idStudent = scanner.nextInt();
            System.out.print("Introduceti numar tema =");
            numarTema = scanner.nextInt();
            System.out.println("Intoduceti nota =");
            nota = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Introduceti observatii(optional) =");
            observatii = scanner.nextLine();
            if(observatii.equals(""))
                observatii = "null";

            try {
                controllerNote.add(new Nota(new PairID<Integer, Integer>(idStudent, numarTema), nota),observatii);
                System.out.println("S-a citit o nota ");
            }catch (ValidationException e){
                System.out.println(e.getMessage());
            }

        }
    }

    private class UpdateStudentCommand implements Command {
        @Override
        public void execute() {
            int idStudent, grupaStudent;
            String nume, email, cadruDidactic;

            System.out.print("Introduceti  id student = ");
            idStudent = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Introduceti nume student = ");
            nume=scanner.nextLine();
            System.out.println("Intoduceti grupa studentului = ");
            grupaStudent = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Introduceti email-ul studentului = ");
            email = scanner.nextLine();
            System.out.println("Introduceti cadrul didactic al studentului = ");
            cadruDidactic = scanner.nextLine();

            try {
                controllerStudent.update(new Student(idStudent, nume, grupaStudent, email, cadruDidactic));
                System.out.println("S-a updatat un student ");
            }catch (ValidationException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public class UpdateTemaCommand implements Command{
        @Override
        public void execute() {
            int numarTema, deadline;
            String cerinta;

            System.out.print("Introduceti  numar tema =");
            numarTema = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Introduceti cerinta tema =");
            cerinta = scanner.nextLine();
            System.out.println("Intoduceti deadline tema =");
            deadline = scanner.nextInt();

            try {
                controllerTeme.update(new Tema(numarTema, cerinta, deadline));
                System.out.println("S-a updatat o tema ");
            }catch (ValidationException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public class UpdateNotaCommand implements Command{
        @Override
        public void execute() {
            int idStudent, numarTema, nota;
            String observatii;

            System.out.print("Introduceti  id-ul studentului =");
            idStudent = scanner.nextInt();
            System.out.print("Introduceti numar tema =");
            numarTema = scanner.nextInt();
            System.out.println("Intoduceti nota =");
            nota = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Introduceti observatii(optional) =");
            observatii = scanner.nextLine();
            if(observatii.equals(""))
                observatii = "null";

            try {
                controllerNote.update(new Nota(new PairID<Integer, Integer>(idStudent, numarTema), nota),observatii);
                System.out.println("S-a updatat o nota ");
            }catch (ValidationException e){
                System.out.println(e.getMessage());
            }

        }
    }

    private class DeleteTemaCommand implements Command {
        @Override
        public void execute() {
            int numarTema;

            System.out.print("Introduceti  numar tema =");
            numarTema = scanner.nextInt();

            try {
                controllerTeme.delete(numarTema);
            } catch (ValidationException e) {
                e.printStackTrace();
            }
            System.out.println("S-a sters  tema ");

        }
    }

    private class DeleteNotaCommand implements Command {
        @Override
        public void execute() {
            int idStudent, numarTema;

            System.out.print("Introduceti  id student = ");
            idStudent = scanner.nextInt();
            System.out.println("Introduceti numar tema = ");
            numarTema = scanner.nextInt();

            try {
                controllerNote.delete(new PairID<Integer,Integer>(idStudent,numarTema));
            } catch (ValidationException e) {
                e.printStackTrace();
            }
            System.out.println("S-a sters  nota ");

        }
    }

    private class DeleteStudentCommand implements Command {
        @Override
        public void execute() {
            int idStudent;

            System.out.print("Introduceti  id student = ");
            idStudent = scanner.nextInt();

            try {
                controllerStudent.delete(idStudent);
            } catch (ValidationException e) {
                e.printStackTrace();
            }
            System.out.println("Studentul a fost sters. \n");

        }
    }
    private class FiltruStudentIntre implements Command{
        @Override
        public void execute() {
            int grupaStudentInceput,grupaStudentSfarsit;
            System.out.println("Intoduceti grupa de inceput = ");
            grupaStudentInceput = scanner.nextInt();
            System.out.println("Intoduceti grupa de sfarsit = ");
            grupaStudentSfarsit = scanner.nextInt();
            List<Student> filtered = controllerStudent.filtruStudentiIntreGrupele(grupaStudentInceput,grupaStudentSfarsit);
            filtered.forEach(student -> System.out.println(student.toString()));
        }
    }
    private class FiltruStudentEmail implements Command{
        @Override
        public void execute() {
            String contains;
            scanner.nextLine();
            System.out.println("String email = ");
            contains = scanner.nextLine();
            List<Student> filtered = controllerStudent.filtruStudentiContineEmail(contains);
            filtered.forEach(student -> System.out.println(student.toString()));
        }
    }
    private class FiltruStudentCadru implements Command{
        @Override
        public void execute() {
            String contains;
            scanner.nextLine();
            System.out.println("String cadru = ");
            contains = scanner.nextLine();
            List<Student> filtered = controllerStudent.filtruStudentiCareAuCadru(contains);
            filtered.forEach(student -> System.out.println(student.toString()));
        }
    }
    private class FiltruTemaDeadline implements Command{
        @Override
        public void execute() {
            int deadline;
            System.out.println("Dati saptamana deadline = ");
            deadline = scanner.nextInt();
            List<Tema> filtered = controllerTeme.filtruTemaPanaLaDeadline(deadline);
            filtered.forEach(tema -> System.out.println(tema.toString()));
        }
    }
    private class FiltruTemaText implements Command{
        @Override
        public void execute() {
            String contains;
            scanner.nextLine();
            System.out.println("String text = ");
            contains = scanner.nextLine();
            List<Tema> filtered = controllerTeme.filtruTemaContineinText(contains);
            filtered.forEach(tema -> System.out.println(tema.toString()));
        }
    }
    private class FiltruTemaID implements Command{
        @Override
        public void execute() {
            int idInceput,idSfarsit;
            System.out.println("Intoduceti id de inceput = ");
            idInceput = scanner.nextInt();
            System.out.println("Intoduceti id de sfarsit = ");
            idSfarsit = scanner.nextInt();
            List<Tema> filtered = controllerTeme.filtruTemaCuIDintre(idInceput,idSfarsit);
            filtered.forEach(tema -> System.out.println(tema.toString()));
        }
    }
    private class FiltruNotaSaptamana implements Command{
        @Override
        public void execute() {
            int saptamana;
            System.out.println("Intoduceti saptamana = ");
            saptamana = scanner.nextInt();
            List<Nota> filtered = controllerNote.filtruNotaPredataInainteDe(saptamana);
            filtered.forEach(nota -> System.out.println(nota.toString()));
        }
    }
    private class FiltruNotaIntre implements Command{
        @Override
        public void execute() {
            int notaInceput,notaSfarsit;
            System.out.println("Intoduceti nota de inceput = ");
            notaInceput = scanner.nextInt();
            System.out.println("Intoduceti nota de sfarsit = ");
            notaSfarsit = scanner.nextInt();
            List<Nota> filtered = controllerNote.filtruNotaIntre(notaInceput,notaSfarsit);
            filtered.forEach(nota -> System.out.println(nota.toString()));
        }
    }
    private class FiltruNotaPredata implements Command{
        @Override
        public void execute() {
            List<Nota> filtered = controllerNote.filtruNotaPredataLaTimp();
            filtered.forEach(nota -> System.out.println(nota.toString()));
        }
    }


    private void createMenu()
    {
        mainMenu = new MenuCommand(" Meniu Principal");
        MenuCommand crudStudent =new MenuCommand("Operatii CRUD Student");
        MenuCommand crudTema = new MenuCommand("Operatii CRUD Tema de laborator");
        MenuCommand crudNota = new MenuCommand("Operatii CRUD Note");
        MenuCommand filtre = new MenuCommand("Filtre");
        crudStudent.addCommand("0. Back to main menu " , mainMenu);
        crudStudent.addCommand("1. Add Student" , new ReadStudentCommand());
        crudStudent.addCommand("2. Update Student" , new UpdateStudentCommand());
        crudStudent.addCommand("3. Delete Student", new DeleteStudentCommand());

        crudTema.addCommand("0. Back to main menu", mainMenu);
        crudTema.addCommand("1. Add Tema de laborator", new ReadTemaCommand());
        crudTema.addCommand("2. Update Tema de laborator", new UpdateTemaCommand());
        crudTema.addCommand("3. Delete Tema de laborator", new DeleteTemaCommand());

        crudNota.addCommand("0. Back to main menu", mainMenu);
        crudNota.addCommand("1. Add Nota", new ReadNotaCommand());
        crudNota.addCommand("2. Update nota", new UpdateNotaCommand());
        crudNota.addCommand("3. Delete nota", new DeleteNotaCommand());

        filtre.addCommand("0. Back to main menu", mainMenu);
        filtre.addCommand("1. Filtrare Studenti intre grupele", new FiltruStudentIntre());
        filtre.addCommand("2. Filtrare Studenti care contin in email", new FiltruStudentEmail());
        filtre.addCommand("3. Filtrare Studenti care au cadrul didactic", new FiltruStudentCadru());
        filtre.addCommand("4. Filtrare Teme pana la deadline", new FiltruTemaDeadline());
        filtre.addCommand("5. Filtrare Teme care contin in enunt", new FiltruTemaText());
        filtre.addCommand("6. Filtrare Teme cu ID-uri intre", new FiltruTemaID());
        filtre.addCommand("7. Filtrare Nota predata inainte de saptamana",new FiltruNotaSaptamana());
        filtre.addCommand("8. Filtrare Nota intre",new FiltruNotaIntre());
        filtre.addCommand("9. Filtrare Nota care au fost predate la timp", new FiltruNotaPredata());

        mainMenu.addCommand("0 Exit" ,()-> {System.exit(0);});
        mainMenu.addCommand("1 Crud Student", crudStudent);
        mainMenu.addCommand("2 Crud Tema de laborator", crudTema);
        mainMenu.addCommand("3 Crud Nota", crudNota);
        mainMenu.addCommand("4 Filtre", filtre);


        //mainMenu.addCommand("3 Exit" , new ExitCommand());
    }

    public void runMenu() {
        createMenu();
        MenuCommand crtMenu = mainMenu;
        while (true) {
            try {
                System.out.println(crtMenu.getMenuName());
                System.out.println("-----------------------");
                crtMenu.execute();
                System.out.println("Optiunea d-voastra >>");
                int actionNumber = scanner.nextInt();
                if (actionNumber > -1 && actionNumber <= crtMenu.getCommands().size()) {
                    Command selectedCommand = crtMenu.getCommands().get(actionNumber);
                    if (selectedCommand instanceof MenuCommand)
                        crtMenu = (MenuCommand) selectedCommand;
                    else selectedCommand.execute();
                } else System.out.println("Optiunea nu este valida!");
            }catch (InputMismatchException ex)
            {
                System.out.println("Datele au fost introduse eronat");
                scanner.next();

            }
        }
    }





    public Console(){}
}
