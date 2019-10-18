package ru.mikheev.kirill.somejunk;

import java.util.TreeSet;

public class AVLTree<T extends Comparable<T> > {

    private class TreeMember{

        private T data;
        private TreeMember left;
        private TreeMember right;
        private Integer height;

        TreeMember(T data){
            this.data = data;
            height = 1;
        }

        private void setLeft(TreeMember left){
            this.left = left;
        }

        private void setRight(TreeMember right){
            this.right = right;
        }

        private void setHeight(Integer height){
            this.height = height;
        }

        private T getData(){
            return data;
        }

        private TreeMember getLeft() {
            return left;
        }

        private TreeMember getRight() {
            return right;
        }

        private Integer getHeight(){
            return height;
        }
    }

    private TreeMember root;

    public AVLTree(){}

    public void add(T data){
        if(!contain(data)){
            root = insert(root, data);
        }
    }

    public T remove(TreeMember reference){
        if(!contain(reference)){
            return null;
        }
        TreeMember removed = deleteByReference(root, reference);
        return removed.getData();
    }

    public T remove(T data){
        if(!contain(data)){
            return null;
        }
        TreeMember removed = deleteByData(root, data);
        return removed.getData();
    }

    public boolean contain(T data){
        TreeMember next = root;
        while (next != null){
            if(next.getData().compareTo(data) == 0){
                return true;
            }
            if(next.getData().compareTo(data) > 0){
                next = next.getLeft();
            }else{
                next = next.getRight();
            }
        }

        return false;
    }

    public boolean contain(TreeMember tmp){
        return contain(tmp.getData());
    }

    public Integer getSize(){
        return countTreeMembers(root);
    }

    @Override
    public String toString() {
        return test(root);
    }

    private String test(TreeMember next){
        if(next == null) return "";
        return "" + next.getData() + test(next.getRight()) + test(next.getLeft());
    }

    private TreeMember insert(TreeMember next, T data) {
        if (next == null){
            return new TreeMember(data);
        }
        if(next.getData().compareTo(data) > 0){
            next.setLeft(insert(next.getLeft(), data));
        }else{
            next.setRight(insert(next.getRight(), data));
        }
        return balance(next);
    }

    private TreeMember deleteByData(TreeMember next, T data){
        if( next == null ) return null;
        if( next.getData().compareTo(data) > 0 ) {
            next.setLeft( deleteByData(next.getLeft(), data) );
        }else{
            if( next.getData().compareTo(data) < 0 ) {
                next.setRight( deleteByData(next.getRight(), data) );
            }else{
                TreeMember l = next.getLeft();
                TreeMember r = next.getRight();
                next.setRight(null);
                next.setLeft(null);
                if( r == null ){
                    return l;
                }
                TreeMember min = findMin(r);
                min.setRight(removeMin(r));
                min.setLeft(l);
                return balance(min);
            }
        }
        return balance(next);
    }

    private TreeMember deleteByReference(TreeMember next, TreeMember reference){
        if( next == null ) return null;
        if(next == reference) {
            TreeMember l = next.getLeft();
            TreeMember r = next.getRight();
            next.setRight(null);
            next.setLeft(null);
            if (r == null) {
                return l;
            }
            TreeMember min = findMin(r);
            min.setRight(removeMin(r));
            min.setLeft(l);
            return balance(min);
        }
        next.setLeft(deleteByReference(next.getLeft(), reference));
        next.setRight(deleteByReference(next.getRight(), reference));
        return balance(next);
    }

    private Integer height(TreeMember root){
        return root == null ? 0 : root.getHeight();
    }

    private Integer bFactor(TreeMember root){
        return height(root.getRight()) - height(root.getLeft()) ;
    }

    private void resetHeight(TreeMember root){
        Integer hl = height(root.getLeft()) + 1;
        Integer hr = height(root.getRight()) + 1;
        root.setHeight( hl > hr  ? hl : hr );
    }

    private TreeMember rotateRight(TreeMember root){
        TreeMember oLeft = root.getLeft();
        root.setLeft(oLeft.getRight());
        oLeft.setRight(root);
        resetHeight(root);
        resetHeight(oLeft);
        return oLeft;
    }

    private TreeMember rotateLeft(TreeMember root){
        TreeMember oRight = root.getRight();
        root.setRight(oRight.getLeft());
        oRight.setLeft(root);
        resetHeight(root);
        resetHeight(oRight);
        return oRight;
    }

    private TreeMember balance(TreeMember root){
        resetHeight(root);
        if( bFactor(root) == 2 ) {
            if( bFactor( root.getRight() ) < 0 )
                root.setRight( rotateRight( root.getRight() ) );
            return rotateLeft(root);
        }

        if( bFactor(root) == -2 ) {
            if( bFactor(root.getLeft()) > 0  )
                root.setLeft( rotateLeft(root.getLeft() ) );
            return rotateRight(root);
        }

        return root;
    }

    private Integer countTreeMembers(TreeMember root){
        if(root == null) {
            return 0;
        }
        return 1 + countTreeMembers(root.getLeft()) + countTreeMembers(root.getRight());
    }

    private TreeMember removeMin(TreeMember next){
        if( next.getLeft() == null )
            return next.getRight();
        next.setLeft(removeMin(next.getLeft()));
        return balance(next);
    }

    private TreeMember findMin(TreeMember next) {
        return next.getLeft() != null ? findMin(next.getLeft()) : next;
    }
}
