package pt.joao.Libsoft.graph;

import pt.joao.Libsoft.exceptions.BoundaryViolationException;
import pt.joao.Libsoft.exceptions.InvalidPositionException;

import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TreeLinked<E> implements Tree<E>{

    private TreeNode root;

    public TreeLinked(){
        this.root = null;
    }

    public TreeLinked(E root){
        this.root = new TreeNode(root);
    }


    @Override
    public int size() {
        if (isEmpty()) return 0;
        else{
            return size(this.root);
        }
    }


    public int size(TreeNode root){
        if (root.children == null) return 1;
        else{
            int size = 1;
            for (TreeNode child : root.children){
                size += size(child);
            }
            return size;
        }
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    @Override
    public Iterable<Position<E>> positions() {
        ArrayList<Position<E>> list = new ArrayList<>();
        if (!isEmpty()){
            positions(root,list);
        }
        return list;
    }

    @Override
    public Iterable<E> elements() {
        ArrayList<E> list = new ArrayList<>();
        if (!isEmpty()){
            elements(root,list);
        }
        return list;
    }

    private void elements(Position<E> root, ArrayList<E> list) {
        list.add(list.size(),root.element());
        for (Position<E> child : children(root)){
            elements(child,list);
        }
    }

    private void positions(Position<E> position, ArrayList<Position<E>> list){
        for (Position<E> child : children(position)){
            positions(child,list);
        }
        list.add(list.size(),position);
    }

    @Override
    public E replace(Position<E> position, E element) throws InvalidPositionException {
        TreeNode node = checkPosition(position);
        E oldElem = node.element;
        node.element = element;
        return oldElem;
    }

    @Override
    public Position<E> parent(Position<E> position) throws InvalidPositionException, BoundaryViolationException {
        TreeNode node = checkPosition(position);
        return node.parent;
    }

    @Override
    public Iterable<Position<E>> children(Position<E> position) throws InvalidPositionException {
        TreeNode node = checkPosition(position);
        ArrayList<Position<E>> list = new ArrayList<>();
        for (Position<E> child :  node.children){
            list.add(child);
        }
        return list;
    }

    @Override
    public boolean isInternal(Position<E> position) throws InvalidPositionException {
        TreeNode node = checkPosition(position);
        return !node.children.isEmpty() && node != this.root;
    }

    @Override
    public boolean isExternal(Position<E> position) throws InvalidPositionException {
        TreeNode node = checkPosition(position);
        return node.children.isEmpty();
    }

    @Override
    public boolean isRoot(Position<E> position) throws InvalidPositionException {
        TreeNode node = checkPosition(position);
        return node == this.root;
    }

    @Override
    public Position<E> insert(Position<E> parent, E elem, int order) throws InvalidPositionException, BoundaryViolationException {
        if (isEmpty()){
            if (parent != null) throw new InvalidPositionException();
            if (order != 0) throw new BoundaryViolationException();
            this.root = new TreeNode(elem);
            return this.root;
        }
        TreeNode parentNode = checkPosition(parent);
        if (order < 0 || order > parentNode.children.size()) throw new BoundaryViolationException();
        TreeNode node = new TreeNode(elem,parentNode);
        node.parent = parentNode;
        parentNode.children.add(order,node);
        return node;
    }

    @Override
    public Position<E> insert(Position<E> parent, E elem) throws InvalidPositionException {
        if (isEmpty()){
            if (parent != null) throw new InvalidPositionException();
            this.root = new TreeNode(elem);
            return root;
        }
        TreeNode parentNode = checkPosition(parent);
        TreeNode node = new TreeNode(elem);
        node.parent = parentNode;
        parentNode.children.add(node);
        return node;
    }

    @Override
    public E remove(Position<E> position) throws InvalidPositionException {
        TreeNode node = checkPosition(position);
        E element = node.element;
        if (node == root){
            root = null;
            return element;
        }
        node.parent.children.remove(node);
        return element;

    }

    private int height(TreeNode treeRoot){
        if (treeRoot == null) return -1;
        if (treeRoot.children.isEmpty()) return 0;

        int childrenMaxHeight = Integer.MIN_VALUE;

        for (TreeNode children : treeRoot.children){
            if (height(children) > childrenMaxHeight) childrenMaxHeight = height(children);
        }
        return 1 + childrenMaxHeight;
    }


    @Override
    public int height() {
        return 0;
    }

    public String toString() {
        String str = "";
        if (!isEmpty()) {
            str = toStringPreOrderLevels(root, 1);
        }
        return str;
    }

    private String toStringPreOrderLevels(Position<E> position, int level) {
        StringBuilder sb = new StringBuilder(position.element().toString()); // visit (position)

        for (Position<E> w : children(position)) {
            sb.append("\n").append(printLevel(level)).append(toStringPreOrderLevels(w, level + 1));
        }
        return sb.toString();
    }

    private String printLevel(int level) {
        return Collections.nCopies(level,"  ") + "-";
    }

    private TreeNode checkPosition(Position<E> position) throws InvalidPositionException{
        if (position == null) throw new InvalidPositionException();
        try{
            TreeNode treeNode = (TreeNode) position;
            if (treeNode.children == null){
                throw new InvalidPositionException();
            }
            return treeNode;
        }catch(ClassCastException ex){
            throw new InvalidPositionException();
        }
    }

    private class TreeNode implements Position<E>{
        private E element;
        private TreeNode parent;
        private List<TreeNode> children;

        TreeNode(E element){
            this.element = element;
            parent = null;
            children = new ArrayList<>();
        }

        TreeNode (E element,TreeNode parent){
            this.element = element;
            this.parent = parent;
            this.children = new ArrayList<>();
        }

        public E element(){
            if (element == null)
                    throw new InvalidPositionException();
            return element;
        }

    }

}
