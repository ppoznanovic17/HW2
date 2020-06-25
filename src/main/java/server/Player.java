package server;

import java.util.Objects;
import java.util.UUID;

public class Player {
    //TODO slobodno mozete i trebate prosirivati i menjati po svojoj volji
    private UUID id;
    private int points;
    private int name;
    private boolean wait;
    private int seat;

    public Player(UUID id, int name) {
        this.id = id;
        points = 0;
        this.name = name;
        wait = false;
        seat= -1;

    }


    public int getPoints() {
        return points;
    }

    public void addPoint() {
        points++;
    }

    public int getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", points=" + points +
                ", name=" + name +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if(o != null && o instanceof Player){
            if(((Player) o).id == this.id){
                return true;
            }
        }
        return false;
    }

    public synchronized void setWait(boolean wait) {
        this.wait = wait;
    }

    public synchronized boolean isWait() {
        return wait;
    }

    public synchronized int getSeat() {
        return seat;
    }

    public synchronized void setSeat(int seat) {
        this.seat = seat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
