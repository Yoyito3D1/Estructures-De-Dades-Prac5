package cat.urv.deim.models;
import java.util.Iterator;

public class HashMapIterator<K, V> implements Iterator<V> {
    private int index;
    private NodeHash<K, V> node;

    private final NodeHash<K, V>[] taula;

    public HashMapIterator(NodeHash<K, V>[] taula) {
        this.taula = taula;
        this.index = 0;
        this.node = null;
    }

    @Override
    public boolean hasNext() {
        while (index < taula.length && taula[index] == null) { index++; }
        if (node == null || node.getNext() == null) { return (index < taula.length && taula[index] != null); }
        return true;
    }

    @Override
    public V next() {
        if (node == null || node.getNext() == null) { node = taula[index++];
        } else { node = node.getNext(); }
        return node.getValue();
    }
}
