package server;

import java.util.*;

public class Table {

    private Integer wands[];
    private ArrayList<Player> players;
    private Stack<Player> onHold;

    public Table() {
        this.players = new ArrayList<>(6);
        for(int i=0; i<6; i++){
            this.players.add(i, null);
        }

        this.onHold = new Stack<>();
        onHold.add(null);

        wands = new Integer[]{1, 1, 1, 1, 1, 0};

        List<Integer> intList = Arrays.asList(wands);

        Collections.shuffle(intList);

        intList.toArray(wands);
    }

    public synchronized int giveSeat(Player player){
        for (int i = 0; i < 6; i++) {
            if(players.get(i) == null){
                player.setSeat(i);
                player.setWait(false);
                players.add(i, player);
                return i;
            }
        }
        player.setSeat(-1);
        player.setWait(true);
        return -1;
    }

    public synchronized void releaseSeat(int i){
        Player p = players.get(i);
        if(p == null){

        }else {
            p.setSeat(-1);
            players.remove(i);
            players.add(i, null);
        }


    }

    public synchronized void swap(Player in, Player out){
        int index = out.getSeat();
        in.setSeat(index);
        players.remove(index);
        players.add(index, in);
        out.setSeat(-1);

    }

    public synchronized int seatPosition(Player p){
        if(players.contains(p)){
            int index = players.indexOf(p);
            return index;
        }
        return -1;
    }


    public synchronized Integer getWand(int i) {
        return wands[i];
    }

    public synchronized Integer[] getWands() {
        return wands;
    }


    public synchronized void addOnHold(Player p){
        this.onHold.push(p);
    }

    public synchronized Player getOnHoldOne() {
        return this.onHold.pop();
    }

    public synchronized Stack<Player> getOnHold() {
        return onHold;
    }

    public synchronized ArrayList<Player> getPlayers() {
        return players;
    }

    public synchronized boolean onHoldIsEmpty(){
        return this.onHold.isEmpty();
    }

    public synchronized void setWands(){
        //System.out.println("usao");
        if(wands.length==9){
            wands[0]=0;
            wands[1]=1;

        }else{
            int a = wands.length-1;
            wands = new Integer[a];
            for (int i = 0; i < a-1; i++) {
                wands[i] = 1;
            }
            wands[a-1] = 0;

            List<Integer> intList = Arrays.asList(wands);

            Collections.shuffle(intList);

            wands = intList.toArray(wands);
        }


    }


    public synchronized void resetWands(){
        //System.out.println("usao");
        int a = 6;
        wands = new Integer[a];
        for (int i = 0; i < a-1; i++) {
            wands[i] = 1;
        }
        wands[a-1] = 0;

        List<Integer> intList = Arrays.asList(wands);

        Collections.shuffle(intList);

        wands = intList.toArray(wands);
    }

    public synchronized int numOfSeats(){
        int c=0;
        for(Player p: this.players){
            if(p != null){
                c++;
            }
        }
        return c;
    }


}
