package model;

import java.util.UUID;

public class Request {
    //TODO slobodno mozete i trebate prosirivati i menjati po svojoj volji
    private UUID id;
    private Action action;
    private int name;
    private int wand;
    private int option;

    public Request() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getName() {
        return name;
    }

    public void setWand(int wand) {
        this.wand = wand;
    }

    public int getWand() {
        return wand;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }
        @Override
    public String toString() {
       return "Request{" +
               "name=" + name +
               ", action=" + action +
               '}';
    }
}
