package Service;

import Domain.Nota;
import Domain.PairID;
import Domain.Student;
import Domain.Tema;
import Validator.ValidationException;
import repository.AbstractRepository;
import repository.Repository;
import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class NotaService{

    private int saptamanaCurenta;
    private Repository<Student,Integer> repository_s;
    private Repository<Tema,Integer> repository_t;

    private Repository<Nota, PairID<Integer, Integer>> repository;

    public NotaService(Repository<Nota, PairID<Integer, Integer>> repository, Repository<Student,Integer> repository_s, Repository<Tema,Integer> repository_t) {
        //try{System.out.println(new File("../UI").getCanonicalPath());}
        //catch (IOException ex){}
        saptamanaCurenta = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)-39;
        this.repository = repository;
        this.repository_s = repository_s;
        this.repository_t = repository_t;
    }
    // Add a given entry
    public void add(Nota nota, String observatie) throws ValidationException {
        if(repository_s.findOne(nota.getId().getKey()) == null)
            throw  new ValidationException("Studentul nu exista. \n");
        if(repository_t.findOne(nota.getId().getValue()) == null)
            throw new ValidationException("Tema nu exista. \n");
        if(repository.findOne(nota.getId()) != null)
            throw new ValidationException("Studentul are deja nota la laboratorul acesta. \n");

        Tema tema = repository_t.findOne(nota.getId().getValue());

        if(tema.getDeadline() - saptamanaCurenta < -2)
            nota.setNota(1);
        else
        if(tema.getDeadline() - saptamanaCurenta < 0)
            nota.setNota(nota.getNota() - (saptamanaCurenta - tema.getDeadline()) * 2);
        nota.setSaptPreluare(saptamanaCurenta);
        saveIdStudent(nota.getId().getKey(), tema.getId(), nota.getNota(), tema.getDeadline(), saptamanaCurenta, observatie);
        repository.save(nota);
    }

    // Return a set with all entries
    public Set<Nota> getAll(){
        Set<Nota> all = new HashSet<>();
        repository.getAll().forEach(all::add);
        return all;
    }

    // Delete an entry by a given id
    public void delete(PairID<Integer, Integer> id ) throws ValidationException{
        repository.delete(id);
    }

    // Update a given entry
    public void update(Nota nota, String observatie) throws ValidationException{
        if(repository_s.findOne(nota.getId().getKey()) == null)
            throw  new ValidationException("Studentul nu exista. \n");
        if(repository_t.findOne(nota.getId().getValue()) == null)
            throw new ValidationException("Tema nu exista. \n");
        if(repository.findOne(new PairID<Integer, Integer>(nota.getId().getKey(),nota.getId().getValue())) == null)
            throw new ValidationException("Studentul nu are nota la laboratorul acesta. \n");

        Nota notaCurenta = repository.findOne(nota.getId());
        Tema tema = repository_t.findOne(nota.getId().getValue());
        if(tema.getDeadline() - saptamanaCurenta < -2)
            nota.setNota(1);
        else
        if(tema.getDeadline() - saptamanaCurenta < 0)
            nota.setNota(nota.getNota() - (saptamanaCurenta - tema.getDeadline()) * 2);
        if(notaCurenta.getNota() < nota.getNota()){
            updateIdStudent(nota.getId().getKey(), tema.getId(), nota.getNota(), tema.getDeadline(), saptamanaCurenta, observatie);
            repository.update(nota);
        }
    }
    public void saveIdStudent(int idStudent, int numarTema,int nota, int deadline, int saptamanaPredarii, String observatii) {
        try {
            File path = new File("./Students/", "" + idStudent + ".txt");
            //File file = new File(path.getCanonicalPath());
            //System.out.println(file.getCanonicalPath());
            if (!path.exists())
            {
            //path.getParentFile().mkdirs();
            if(!path.createNewFile())
            //nu a fost creat fisierul
                ;

            }

            try(BufferedWriter out = new BufferedWriter(new FileWriter(path, true))){
                out.write("Adaugare nota, " + " Numar tema: " +
                        String.valueOf(numarTema) + ", nota:" +
                        String.valueOf(nota) + ", deadline:" +
                        String.valueOf(deadline) + ", saptamana predarii:" +
                        String.valueOf(saptamanaPredarii) + ", " +
                        observatii + "\n");
            }catch (IOException e) {
                e.printStackTrace();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void updateIdStudent(int idStudent, int numarTema,int nota, int deadline, int saptamanaPredarii, String observatii) {
        try {
            File path = new File("./Students/", "" + idStudent + ".txt");
            //File file = new File(path.getCanonicalPath());
            //System.out.println(file.getCanonicalPath());
            if (!path.exists())
            {
                //path.getParentFile().mkdirs();
                if(!path.createNewFile())
                    //
                    ;
            }

            try(BufferedWriter out = new BufferedWriter(new FileWriter(path, true))){
                out.write("Modificare nota, "+ " Numar tema: " +
                        String.valueOf(numarTema) + ", nota:" +
                        String.valueOf(nota) + ", deadline:" +
                        String.valueOf(deadline) + ", saptamana predarii:" +
                        String.valueOf(saptamanaPredarii) + ", " +
                        observatii + "\n");
            }catch (IOException e) {
                e.printStackTrace();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
   public List<Nota> filtruNotaPredataInainteDe(int saptamana)
   {
       List<Nota> all = new ArrayList<>();
       all.addAll((Collection<? extends Nota>) repository.getAll());
       return Filter.filterAndSorter(all,Filter.NotaPredataInainteDe(saptamana),Comparator.comparing(Nota::getId));
   }
   public List<Nota> filtruNotaIntre (int notaInceput, int notaSfarsit )
   {
       List<Nota> all = new ArrayList<>();
       all.addAll((Collection<? extends Nota>) repository.getAll());
       return Filter.filterAndSorter(all,Filter.NotaIntre(notaInceput,notaSfarsit),Comparator.comparing(Nota::getId));
   }
    public List<Nota> filtruNotaPredataLaTimp ()
    {
        List<Nota> all = new ArrayList<>();
        all.addAll((Collection<? extends Nota>) repository.getAll());
        return Filter.filterAndSorter(all,Filter.NotaPredataLaTimp(repository_t),Comparator.comparing(Nota::getId));
    }
}
