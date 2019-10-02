package ru.mikheev.kirill.somejunk;

public class SimpleQueue<T> {
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

    private QueueMember<T> root;

    public SimpleQueue(){}

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
