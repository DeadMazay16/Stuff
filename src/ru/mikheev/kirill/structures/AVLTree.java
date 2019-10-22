package ru.mikheev.kirill.structures;

/**
 * The AVLTree class is designed for for storing and quickly retrieving Comparable data.
 *
 * @version 1.0  22 oct 2019
 * @author Kirill Mikheev
 * @param <T> type of Comparable data, that will be kept in tree
 */

public class AVLTree<T extends Comparable<T> > {

    /**
     * inner class which is a single node of a tree
     */
    private class TreeNode {

        private T data;
        private TreeNode left;
        private TreeNode right;
        private Integer height;

        private TreeNode(T data){
            this.data = data;
            height = 1;
        }

        private void setLeft(TreeNode left){
            this.left = left;
        }

        private void setRight(TreeNode right){
            this.right = right;
        }

        private void setHeight(Integer height){
            this.height = height;
        }

        private T getData(){
            return data;
        }

        private TreeNode getLeft() {
            return left;
        }

        private TreeNode getRight() {
            return right;
        }

        private Integer getHeight(){
            return height;
        }
    }

    /**
     * root element of tree
     */
    private TreeNode root;

    public AVLTree(){}

    /**
     * @param data data, that must be added in tree (if doesn't already exist)
     */
    public void add(T data){
        if(!contain(data)){
            root = insert(root, data);
        }
    }

    /**
     * @param data data, that must be removed
     * @return null if data doesn't exist in this tree, otherwise data
     */
    public T remove(T data){
        if(!contain(data)){
            return null;
        }
        root = delete(root, data);
        return data;
    }

    /**
     * @param data data for search
     * @return true if tree contain this data
     */
    public boolean contain(T data){
        TreeNode next = root;
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

    /**
     * @return number of members in whole tree
     */
    public Integer getSize(){
        return countTreeMembers(root);
    }

    /**
     * Inner function, which recursively search for suitable place for new data and add it (if doesn't already exist)
     * @param next root of subtree, in which a suitable place is searched
     * @param data data, that must be added
     * @return new root of subtree
     */
    private TreeNode insert(TreeNode next, T data) {
        if (next == null){
            return new TreeNode(data);
        }
        if(next.getData().compareTo(data) > 0){
            next.setLeft(insert(next.getLeft(), data));
        }else{
            next.setRight(insert(next.getRight(), data));
        }
        return balance(next);
    }

    /**
     * Inner function, which recursively search and delete TreeMember with transferred data
     * @param next root of subtree, in which carried out search
     * @param data data, that must be deleted
     * @return new root of subtree
     */
    private TreeNode delete(TreeNode next, T data){
        if( next == null ) return null;
        if( next.getData().compareTo(data) > 0 ) {
            next.setLeft( delete(next.getLeft(), data) );
        }else{
            if( next.getData().compareTo(data) < 0 ) {
                next.setRight( delete(next.getRight(), data) );
            }else{
                TreeNode l = next.getLeft();
                TreeNode r = next.getRight();
                next.setRight(null);
                next.setLeft(null);
                if( r == null ){
                    return l;
                }
                TreeNode min = findMin(r);
                min.setRight(removeMin(r));
                min.setLeft(l);
                return balance(min);
            }
        }
        return balance(next);
    }

    /**
     * Inner function, which needs for counting bfactor
     * @return height of member
     */
    private Integer height(TreeNode member){
        return member == null ? 0 : member.getHeight();
    }

    /**
     * Inner function, which count difference between right and left subtree
     * @param member subtree root
     * @return difference between right and left subtree
     */
    private Integer bFactor(TreeNode member){
        return height(member.getRight()) - height(member.getLeft()) ;
    }

    /**
     * Inner function, which recount height of TreeMember
     * @param member TreeMember,that needs in recount
     */
    private void resetHeight(TreeNode member){
        Integer hl = height(member.getLeft()) + 1;
        Integer hr = height(member.getRight()) + 1;
        member.setHeight( hl > hr  ? hl : hr );
    }

    /**
     * Inner function, which make right rotation of subtree around point
     * @param point subtree root
     * @return new subtree root
     */
    private TreeNode rotateRight(TreeNode point){
        TreeNode oLeft = point.getLeft();
        point.setLeft(oLeft.getRight());
        oLeft.setRight(point);
        resetHeight(point);
        resetHeight(oLeft);
        return oLeft;
    }

    /**
     * Inner function, which make left rotation of subtree around point
     * @param point subtree root
     * @return new subtree root
     */
    private TreeNode rotateLeft(TreeNode point){
        TreeNode oRight = point.getRight();
        point.setRight(oRight.getLeft());
        oRight.setLeft(point);
        resetHeight(point);
        resetHeight(oRight);
        return oRight;
    }

    /**
     * Inner function, which recursively balance tree according to AVL-rules
     * @param next subtree root
     * @return new subtree root
     */
    private TreeNode balance(TreeNode next){
        resetHeight(next);
        if( bFactor(next) == 2 ) {
            if( bFactor( next.getRight() ) < 0 )
                next.setRight( rotateRight( next.getRight() ) );
            return rotateLeft(next);
        }

        if( bFactor(next) == -2 ) {
            if( bFactor(next.getLeft()) > 0  )
                next.setLeft( rotateLeft(next.getLeft() ) );
            return rotateRight(next);
        }
        return next;
    }

    /**
     * Inner function, which count number of elements in subtree
     * @param next subtree root
     * @return number of elements in subtree
     */
    private Integer countTreeMembers(TreeNode next){
        if(next == null) {
            return 0;
        }
        return 1 + countTreeMembers(next.getLeft()) + countTreeMembers(next.getRight());
    }

    /**
     * Inner function, which recursively remove minimal element in subtree
     * @param next subtree root
     * @return new root after balance
     */
    private TreeNode removeMin(TreeNode next){
        if( next.getLeft() == null ) {
            TreeNode rr = next.getRight();
            next.setRight(null);
            return rr;
        }
        next.setLeft(removeMin(next.getLeft()));
        return balance(next);
    }

    /**
     * Inner function, which recursively search for minimal element in subtree
     * @param next subtree root
     * @return minimal element of subtree
     */
    private TreeNode findMin(TreeNode next) {
        return next.getLeft() != null ? findMin(next.getLeft()) : next;
    }
}
