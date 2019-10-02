package ru.mikheev.kirill.somejunk;

/**
 * The SimpleQueue class is designed for the orderly transfer of parameters between objects.
 *
 * @version 1.0  02 oct 2019
 * @author Kirill Mikheev
 * @param <T> type to be queued
 */

public class SimpleQueue<T> {

    /**
     * This class is a simple queue unit that stores data.
     * @param <Temp> type that contained by one unit
     */

    private class QueueMember<Temp>{

        private Temp data;
        private QueueMember<Temp> next;

        QueueMember(Temp data){
            this.data = data;
        }

        QueueMember<Temp> getNext() {
            return next;
        }

        private Temp getData(){
            return data;
        }

        private void setNext(QueueMember<Temp> next) {
            this.next = next;
        }

        private boolean hasNext(){
            return next != null;
        }
    }

    /** The first element of queue.*/
    private QueueMember<T> root;

    public SimpleQueue(){}

    /**
     * Add new element at the end of queue.
     * @param data data that new member should contain
     */
    public void push(T data){
        if(root == null){
            root = new QueueMember<>(data);
        }else {
            QueueMember<T> tmp = root;
            while (tmp.hasNext()){
                tmp = tmp.getNext();
            }
            tmp.setNext(new QueueMember<>(data));
        }
    }

    /**
     * Extract data of first element and delete it from queue.
     * @return data of <T>-type or null if there is no members in queue
     */
    public T pop(){
        if(root == null){
            return null;
        }
        if(!root.hasNext()){
            T ret = root.getData();
            root = null;
            return ret;
        }
        T ret = root.getData();
        root = root.getNext();
        return ret;
    }
}
