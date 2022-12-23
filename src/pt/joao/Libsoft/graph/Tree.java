package pt.joao.Libsoft.graph;
import pt.joao.Libsoft.exceptions.*;
public interface Tree<E>{

    int size();

    boolean isEmpty();
    Iterable<Position<E>> positions();

    Iterable<E> elements();

    E replace(Position<E> position,E element)
            throws InvalidPositionException;

    Position<E> parent(Position<E> position)
            throws InvalidPositionException, BoundaryViolationException;

    Iterable<Position<E>> children(Position<E> position)
            throws InvalidPositionException;

    boolean isInternal(Position<E> position)
            throws InvalidPositionException;

    boolean isExternal(Position<E> position)
            throws InvalidPositionException;

    boolean isRoot(Position<E> position)   throws InvalidPositionException;
    Position<E> insert(Position<E> parent, E elem, int order)throws InvalidPositionException,BoundaryViolationException;
    Position<E> insert(Position<E> parent, E elem) throws InvalidPositionException;
    E remove(Position<E> position)throws InvalidPositionException;
    int height();

}
