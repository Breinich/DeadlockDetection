package main;

import java.util.LinkedList;

public class Resource {
    private String name;
    private Task actual = null;
    private final LinkedList<Task> waiters;

    public Resource(String n){
        setName(n);
        waiters = new LinkedList<>();
    }

    public boolean addWorker(Task t){
        if(getActual() == null && waiters.isEmpty()) {
            setActual(t);
            return true;
        }
        else if(getActual() == null && waiters.get(0).equals(t)){
            setActual(waiters.removeFirst());
            return true;
        }
        else{
            if(!waiters.contains(t))
                waiters.add(t);
            return false;
        }
    }

    public void removeLast(){
        if(getActual() != null && waiters.isEmpty())
            setActual(null);
        else
            waiters.removeLast();
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj != null) {
            result = this.getName().equals(((Resource) obj).getName());
        }
        return result;
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    public void free(Task t){
        if(getActual() != null) {
            if (getActual().equals(t)) {
                if(waiters.isEmpty())
                    setActual(null);
                else{
                    setActual(waiters.removeFirst());
                    getActual().step();
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Task getActual() {
        return actual;
    }

    public void setActual(Task actual) {
        this.actual = actual;
    }
}
