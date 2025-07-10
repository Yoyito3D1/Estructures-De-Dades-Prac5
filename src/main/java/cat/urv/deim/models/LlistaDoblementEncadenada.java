package cat.urv.deim.models;

import cat.urv.deim.exceptions.ElementNoTrobat;

import java.util.Iterator;

public class LlistaDoblementEncadenada<E extends Comparable<E>> implements ILlistaGenerica<E> {
    private Node<E> primer;
    private Node<E> ultim;
    private int elements;

    //Mètode per inserir un element a la llista. No importa la posició on s'afegeix l'element
    public void inserir(E e) {
        Node<E> node = new Node<>(e);
        if (primer == null) { primer = node;
        } else {
            ultim.setSeguent(node);
            node.setAnterior(ultim);
        }
        ultim = node;
        elements++;
    }

    //Mètode per a esborrar un element de la llista
    public void esborrar(E e) throws ElementNoTrobat {
        Node<E> node = cercarNode(e);
        if (node == null) { throw new ElementNoTrobat("Error: no hi ha cap node."); }
        if (node == primer) { primer = node.getSeguent(); }
        if (node == ultim) { ultim = node.getAnterior(); }
        if (node.getAnterior() != null) { node.getAnterior().setSeguent(node.getSeguent()); }
        if (node.getSeguent() != null) { node.getSeguent().setAnterior(node.getAnterior()); }
        elements--;
    }

    //Mètode per a comprovar si un element està a la llista
    public boolean buscar(E e) { return cercarNode(e) != null; }

    //Mètode per a comprovar si la llista té elements
    public boolean esBuida() { return elements == 0; }

    //Mètode per a obtenir el nombre d'elements de la llista
    public int longitud() { return elements; }

    @Override
    public Iterator<E> iterator() { return new LlistaDoblementEncadenadaIterator<>(primer); }

    //Mètode per a obtenir un array amb tots els elements
    public Object[] elements() {
        Object[] array = new Object[elements];
        int i = 0;
        Node<E> node = primer;
        while (node != null) {
            array[i] = node.getElement();
            node = node.getSeguent();
            i++;
        }
        return array;
    }

    private Node<E> cercarNode(E e) {
        Node<E> node = primer;
        while (node != null) {
            if (node.getElement().equals(e)) { return node; }
            node = node.getSeguent();
        }
        return null;
    }
}