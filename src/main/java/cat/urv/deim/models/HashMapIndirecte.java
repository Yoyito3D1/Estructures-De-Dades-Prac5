package cat.urv.deim.models;

import cat.urv.deim.exceptions.ElementNoTrobat;

import java.util.*;
import java.util.function.Function;

public class HashMapIndirecte<K, V> implements IHashMap<K, V> {
    private int mida;
    private NodeHash[] taula;

    public HashMapIndirecte() {
        this.mida = 16;
        taula = new NodeHash[mida];
    }

    @Override
    public void inserir(K clau, V valor) {
        int index = obtenirIndex(clau);
        if (taula[index] == null) {
            taula[index] = new NodeHash<>(clau, valor);
        } else {
            NodeHash node = taula[index];
            while (node.getNext() != null && !node.getKey().equals(clau)) {
                node = node.getNext();
            }
            if (node.getKey().equals(clau)) {
                node.setValue(valor);
            } else {
                node.setNext(new NodeHash<>(clau, valor));
            }
        }

        // Comprovar el factor de càrrega
        float factorCarrega = factorCarrega();
        if (factorCarrega > 0.75) {
            // Redimensionar la taula
            int novaMida = mida * 2;
            NodeHash[] novaTaula = new NodeHash[novaMida];

            // Passar els elements existents a la nova taula
            for (int i = 0; i < mida; i++) {
                NodeHash<K, V> node = taula[i];
                while (node != null) {
                    K nodeClau = node.getKey();
                    V nodeValor = node.getValue();
                    int nouIndex = Math.abs(nodeClau.hashCode()) % novaMida;
                    if (novaTaula[nouIndex] == null) {
                        novaTaula[nouIndex] = new NodeHash<>(nodeClau, nodeValor);
                    } else {
                        NodeHash nodeAux = novaTaula[nouIndex];
                        while (nodeAux.getNext() != null) {
                            nodeAux = nodeAux.getNext();
                        }
                        nodeAux.setNext(new NodeHash<>(nodeClau, nodeValor));
                    }
                    node = node.getNext();
                }
            }
            // Actualitzar la taula i la mida
            taula = novaTaula;
            mida = novaMida;
        }
    }

    @Override
    public void esborrar(K key) throws ElementNoTrobat {
        int index = obtenirIndex(key);
        if (taula[index] == null) {
            throw new ElementNoTrobat("Element no trobat");
        } else {
            NodeHash<K, V> node = taula[index];
            if (node.getKey().equals(key)) {
                taula[index] = node.getNext();
            } else {
                while (node.getNext() != null && !node.getNext().getKey().equals(key)) {
                    node = node.getNext();
                }
                if (node.getNext() == null) {
                    throw new ElementNoTrobat("Element no trobat");
                } else {
                    node.setNext(node.getNext().getNext());
                }
            }
        }
    }

    @Override
    public boolean buscar(K key) {
        int index = obtenirIndex(key);
        NodeHash<K, V> node = taula[index];
        while (node != null) {
            if (node.getKey().equals(key)) {
                return true;
            }
            node = node.getNext();
        }
        return false;
    }

    @Override
    public boolean esBuida() {
        return mida == 0;
    }

    @Override
    public int longitud() {
        int count = 0;
        for (int i = 0; i < mida; i++) {
            NodeHash<K, V> node = taula[i];
            while (node != null) {
                count++;
                node = node.getNext();
            }
        }
        return count;
    }

    @Override
    public Object[] obtenirClaus() {
        ArrayList<K> claus = new ArrayList<>();
        for (int i = 0; i < mida; i++) {
            NodeHash<K, V> node = taula[i];
            while (node != null) {
                claus.add(node.getKey());
                node = node.getNext();
            }
        }
        return claus.toArray();
    }

    @Override
    public V element(K key) {
        try {
            int index = obtenirIndex(key);
            if (taula[index] == null) {
                throw new ElementNoTrobat("Element no tobat");
            } else {
                NodeHash<K, V> node = taula[index];
                while (node != null && !node.getKey().equals(key)) {
                    node = node.getNext();
                }
                if (node == null) {
                    throw new ElementNoTrobat("Element no trobat");
                } else {
                    return node.getValue();
                }
            }
        } catch (ElementNoTrobat e) {
            return null; // Exemple: Retornar un valor predeterminat
        }
    }


    @Override
    public Iterator<V> iterator() {
        return new HashMapIterator<>(taula);
    }

    @Override
    public float factorCarrega() {
        return (float) longitud() / mida;
    }

    @Override
    public int midaTaula() {
        return mida;
    }

    private int obtenirIndex(K key) {
        return Math.abs(key.hashCode()) % mida;
    }

    public V getOrDefault(K key, V defaultValue) {
        int index = obtenirIndex(key);
        NodeHash<K, V> node = taula[index];
        while (node != null) {
            if (node.getKey().equals(key)) {
                return node.getValue();
            }
            node = node.getNext();
        }
        return defaultValue;
    }
}
