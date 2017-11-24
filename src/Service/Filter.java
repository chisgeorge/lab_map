package Service;

import Domain.Nota;
import Domain.Student;
import Domain.Tema;
import repository.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Filter {
    public static Predicate<Student> StudentiIntreGrupele(int grupaInceput,int grupaSfarsit)
    {
        return student -> student.getGrupa()>=grupaInceput && student.getGrupa()<=grupaSfarsit;
    }
    public static Predicate<Student> StudentiContineEmail(String text)
    {
        return student -> student.getEmail().contains(text);
    }
    public static Predicate<Student> StudentiCareAuCadru(String cadru)
    {
        return student -> student.getCadruDidactic().equals(cadru);
    }
    public static Predicate<Tema> TemaPanaLaDeadline (int deadline)
    {
        return tema -> tema.getDeadline()<=deadline;
    }
    public static Predicate<Tema> TemaContineinText(String text)
    {
        return tema -> tema.getCerinta().contains(text);
    }
    public static Predicate<Tema> TemaCuIDintre(int IDInceput, int IDSfarsit)
    {
        return tema -> tema.getId()>=IDInceput && tema.getId()<=IDSfarsit;
    }
    public static Predicate<Nota> NotaPredataInainteDe (int saptamana)
    {
        return nota -> nota.getSaptPreluare()<=saptamana;
    }
    public static Predicate<Nota> NotaIntre (int notaInceput, int notaSfarsit )
    {
        return nota -> nota.getNota()>=notaInceput && nota.getNota()<=notaSfarsit;
    }
    public static Predicate<Nota> NotaPredataLaTimp (Repository<Tema,Integer> repository_t)
    {
        return nota-> nota.getSaptPreluare() < repository_t.findOne(nota.getId().getValue()).getDeadline();
    }
    //public static Comparator<Student> comparatorStudent = (studentA,studentB) -> studentA.getId().compareTo(studentB.getId());
    //public static Comparator<Tema> comparatorTema = (temaA,temaB) -> temaA.getId() < temaB.getId() ? -1 : temaA.getId() == temaB.getId() ? 0 : 1;
    //public static Comparator<Nota> comparatorNota = (notaA, notaB) -> notaA.getId() < notaB.getId() ? -1 : notaA.getId() == notaB.getId() ? 0 : 1;


    public static <E> List<E> filterAndSorter(List<E> lista, Predicate<E> predicate, Comparator<E> comparator)
    {
        List<E> filteredSorted;
        filteredSorted = lista.stream().filter(predicate).collect(Collectors.toList());
        filteredSorted.sort(comparator);
        return filteredSorted;
    }

}
