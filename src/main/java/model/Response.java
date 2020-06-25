package model;

public class Response {
    //TODO slobodno mozete i trebate prosirivati i menjati po svojoj volji
    private Action action;
    private int numbOfWands;

    public Response() {
    }



    public void setNumbOfWands(int numbOfWands) {
        this.numbOfWands = numbOfWands;
    }

    public int getNumbOfWands() {
        return numbOfWands;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "Response{" +
                "action=" + action +
                ", numbOfWands=" + numbOfWands +
                '}';
    }
}
