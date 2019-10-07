package ru.mikheev.kirill.somejunk;

public class AVLTree<T extends Comparable<T> > {

    private class TreeMember{

        private T data;
        private TreeMember left;
        private TreeMember right;
        private TreeMember parent;

        TreeMember(T data){
            this.data = data;
        }

        private void setData(T data){
            this.data = data;
        }

        private void setLeft(TreeMember left){
            this.left = left;
        }

        private void setRight(TreeMember right){
            this.right = right;
        }

        private void setParent(TreeMember parent){
            this.parent = parent;
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

        private TreeMember getParent() {
            return parent;
        }

        private boolean isLeaf(){
            return right == null && left == null;
        }

        private boolean hasLeft(){
            return left != null;
        }

        private boolean hasRight(){
            return right != null;
        }

        private boolean hasParent(){
            return parent == null;
        }

        private boolean isRoot(){
            return  hasParent();
        }

        private boolean contain(T data){
            if(this.data.compareTo(data) == 0){
                return true;
            }
            if(this.data.compareTo(data) > 0 && this.hasLeft()){
                return left.contain(data);
            }
            if(this.data.compareTo(data) < 0 && this.hasRight()){
                return right.contain(data);
            }
            return false;
        }

        private void addChild(TreeMember child){
            if(this.data.compareTo(child.getData()) > 0){
                if(!this.hasLeft()){
                    this.setLeft(child);
                    child.setParent(this);
                }else{
                    left.addChild(child);
                }
            }else{
                if(!this.hasRight()){
                    this.setRight(child);
                    child.setParent(this);
                }else{
                    right.addChild(child);
                }
            }
        }

        private int subTreeSize(){
            if(isLeaf()){
                return 1;
            }
            int answ = 1;
            if(hasLeft()){
                answ += left.subTreeSize();
            }
            if(hasRight()){
                answ += right.subTreeSize();
            }
            return answ;
        }
    }

    private TreeMember root;

    public AVLTree(){}

    public boolean add(T data){
        if(root.contain(data)){
            return false;
        }
        TreeMember tmp = new TreeMember(data);
        root.addChild(tmp);
        update();
        return true;
    }

    public T remove(TreeMember tmp){
        if(!root.contain(tmp.data)){
            return null;
        }

    }

    private void update(){}
}
