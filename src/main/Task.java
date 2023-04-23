package main;

import java.util.LinkedList;


public class Task {
    private final String name;
    private final LinkedList<String> todo;

    private Resource waitingFor;
    private boolean finished = false;

    private int taskCt = 0;

    public Task(String n){
        name = n;
        todo = new LinkedList<>();
        waitingFor = null;
    }

    public void addToDo(String s){
        todo.add(s);
        taskCt++;
    }

    public String step(){

        if (getWaitingFor() == null) {
            if(todo.size() == 1)
                finished = true;
            return todo.removeFirst();

        } else {
            String ret = "+" + getWaitingFor().getName();
            waitingFor = null;
            if(todo.isEmpty())
                finished = true;
            return ret;
        }
    }

    public void waitFor(Resource r){
        waitingFor = r;
        finished = false;
    }

    public void revertToDo(Resource r){
        if(getWaitingFor() == r){
            waitingFor = null;
            if(todo.isEmpty())
                finished = true;
        }
    }

    public int taskNumber(){
            return taskCt - todo.size();
    }

    public String getName() {
        return name;
    }

    public Resource getWaitingFor() {
        return waitingFor;
    }

    public boolean isFinished() {
        return finished;
    }
}
