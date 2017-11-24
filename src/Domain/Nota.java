package Domain;

public class Nota implements HasID<PairID<Integer,Integer>>{
    private PairID<Integer, Integer> id;
    private int saptPreluare;
    private int nota;

    public Nota(PairID<Integer, Integer> id, int nota) {
        this.id = id;
        //this.saptPreluare = saptPreluare;
        this.nota = nota;
    }

    public Nota(PairID<Integer, Integer> id , int nota, int saptPreluare) {
        this.id = id;
        this.saptPreluare = saptPreluare;
        this.nota = nota;
    }
    @Override
    public PairID<Integer, Integer> getId() {
        return id;
    }
    @Override
    public void setId(PairID<Integer, Integer> id) {
        this.id = id;
    }

    public int getSaptPreluare() {
        return saptPreluare;
    }

    public void setSaptPreluare(int saptPreluare) {
        this.saptPreluare = saptPreluare;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Nota nota1 = (Nota) o;

        if (saptPreluare != nota1.saptPreluare) return false;
        if (nota != nota1.nota) return false;
        return id != null ? id.equals(nota1.id) : nota1.id == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + saptPreluare;
        result = 31 * result + nota;
        return result;
    }

    @Override
    public String toString() {
        return "Nota{" +
                "idNota=" +  id.getKey() + "," + id.getValue() +
                ", saptPreluare=" + saptPreluare  +
                ", nota=" + nota +
                '}';
    }
}