package cat.urv.deim.models;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LlistaDoblementEncadenadaIterator<E extends Comparable<E>> implements Iterator<E> {
    private Node<E> actual;
    private Node<E> ultimRetornat;

    public LlistaDoblementEncadenadaIterator(Node<E> primer) {
        this.actual = primer;
        this.ultimRetornat = null;
    }

    @Override
    public boolean hasNext() { return actual != null; }

    @Override
    public E next() {
        if (!hasNext()) { throw new NoSuchElementException(); }
        ultimRetornat = actual;
        actual = actual.getSeguent();
        return ultimRetornat.getElement();
    }
}