package Domain;

import javafx.util.Pair;

public class PairID<K extends Comparable<? super K>,V extends Comparable<? super V>> implements Comparable<PairID<K,V>>{
    private K key;
    private V value;

    public PairID(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PairID<?, ?> pairID = (PairID<?, ?>) o;

        if (key != null ? !key.equals(pairID.key) : pairID.key != null) return false;
        return value != null ? value.equals(pairID.value) : pairID.value == null;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    public int compareTo(PairID<K,V> o)
    {
        /*if( this.key.compareTo(o.getKey()) > 0)
            return 1;
        if( this.key.compareTo(o.getKey()) < 0)
            return -1;
        return 0;*/
        return Integer.compare(this.key.compareTo(o.getKey()), 0) != 0 ? Integer.compare(this.key.compareTo(o.getKey()), 0) : Integer.compare(this.value.compareTo(o.getValue()), 0);
    }

}
