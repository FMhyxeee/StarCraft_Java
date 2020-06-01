package net.util;

import java.util.LinkedList;

public class Stack {

    private LinkedList<Integer> removeList = new LinkedList<>();

    private LinkedList<Integer> itemlist = new LinkedList<Integer>(){
        @Override
        public boolean add(Integer e) {
            for(int i=0;i<size();++i){
                if(e.compareTo(get(i))<0){
                    add(i, e);
                    return true;
                }
            }
            addLast(e);
            return true;
        }
    };

    public Stack(int size){
        for (int i = 0; i < size; ++i){
            itemlist.add(i);
        }
    }

    public void use(Integer n){
        itemlist.remove(n);
        removeList.add(n);
    }

    public void restore(Integer n){
        if (removeList.contains(n)){
            itemlist.add(n);
        }
    }

    public int next(){
        return itemlist.isEmpty() ? -1 : itemlist.removeFirst();
    }


}
