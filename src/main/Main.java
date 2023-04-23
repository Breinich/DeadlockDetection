package main;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    private static final LinkedList<Task> tasks = new LinkedList<>();
    private static final LinkedList<Resource> resources = new LinkedList<>();

    public static Resource getRes(String n) {
        for(Resource r : resources){
            if(r.getName().equals(n))
                return r;
        }

        return null;
    }

    private static boolean checkCircle(){
        for(Task t: tasks){
            if(!t.isFinished())
                if(circle(t, tasks.size()+1))
                    return true;
        }
        return false;
    }

    private static boolean circle(Task t, int depth){
        depth--;
        if(t.getWaitingFor() == null)
            return false;
        else if(depth == 0)
            return  true;
        else{
            if(t.getWaitingFor().getActual() != null)
                return circle(t.getWaitingFor().getActual(), depth);
            else
                return false;
        }
    }

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        String line;

        while(sc.hasNextLine()){
            line = sc.nextLine();
            String[] tmp = line.split(",");
            if(tmp.length == 1)
                break;
            Task t = new Task(tmp[0]);
            for( int i = 1; i < tmp.length; i++){
                if(tmp[i].charAt(0) != '0'){
                    Resource r = new Resource(tmp[i].substring(1));
                    if(!resources.contains(r))
                        resources.add(r);
                }
                t.addToDo(tmp[i]);
            }

            tasks.add(t);
        }

        sc.close();

        boolean exit = false;
        while(!exit){
            for (Task t : tasks) {
                if(!t.isFinished()) {
                    String s = t.step();
                    char op = s.charAt(0);
                    Resource res = getRes(s.substring(1));
                    if (op == '+' && res != null) {
                        if (!res.addWorker(t))
                            t.waitFor(res);
                        if (checkCircle()) {
                            System.out.println(t.getName() + "," + t.taskNumber() + "," + res.getName());
                            res.removeLast();
                            t.revertToDo(res);
                        }
                    } else if (op == '-' && res != null) {
                        res.free(t);
                    }
                }
                else{
                    for(Resource r: resources){
                        r.free(t);
                    }
                }
            }
            exit = true;
            for(Task t : tasks){
                if (!t.isFinished()) {
                    exit = false;
                    break;
                }
            }
        }
    }
}
