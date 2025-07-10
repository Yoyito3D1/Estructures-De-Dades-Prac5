package cat.urv.deim.models;

public class Node<E> {
    private final E element;
    private Node<E> anterior;
    private Node<E> seguent;

    public Node(E element) { this.element = element; }

    public E getElement() { return element; }

    public Node<E> getAnterior() { return anterior; }

    public void setAnterior(Node<E> anterior) { this.anterior = anterior; }

    public Node<E> getSeguent() { return seguent; }

    public void setSeguent(Node<E> seguent) { this.seguent = seguent; }
}