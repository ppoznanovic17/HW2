package server;

import client.ClientMain;
import com.google.gson.Gson;
import model.Action;
import model.Request;
import model.Response;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.*;

public class ServerThread implements Runnable {

    private static int round = 0;
    private static int wand;
    private  static int seat = 0;
    private static int pom;

    private static Player currentPlayer;

    private static int barr = ClientMain.MAX_PLAYERS;
    private static CyclicBarrier cyclicBarrier;
    private static CyclicBarrier cyclicBarrier2;
    private static CyclicBarrier cyclicBarrier3;
    private static CyclicBarrier cyclicBarrier4;
    private static CyclicBarrier cyclicBarrier5;

    private static boolean reset = false;

    private static int responses=0;
    private static boolean draw = false;

    private static int MAX_ROUNDS = 10;

    private static ArrayList<Player> allPlayers;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private Gson gson;
    private Table table;
    private boolean inGame;

    public ServerThread(Socket socket, Table table) throws IOException {
        this.table = table;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        allPlayers = new ArrayList<>();
        gson = new Gson();
        pom = ClientMain.MAX_PLAYERS;
        cyclicBarrier = new CyclicBarrier(pom);
        cyclicBarrier2 = new CyclicBarrier(pom);
        cyclicBarrier3 = new CyclicBarrier(pom);
        cyclicBarrier4 = new CyclicBarrier(pom);
        cyclicBarrier5 = new CyclicBarrier(pom);

    }

    @Override
    public void run() {
        try {

            //2. citam zahtev za stolicu
            Request request = receiveRequest();

            Player p = new Player(request.getId(), request.getName());
            currentPlayer = p;

            //3. odgovaram na zahtev
            Response response = new Response();
            int seat = table.giveSeat(p);
            if(seat == -1){
                table.getOnHold().add(p);
                response.setAction(Action.WAIT);
            }else {
                response.setAction(Action.SIT);
            }
            sendResponse(response);

            while (true) {

                cyclicBarrier.await();
                response = new Response();
                //5. saljem sta da rade
                if(p.getSeat() == (getRound()%6)){//ako je on taj koji bira
                        response.setAction(Action.DRAW);
                        response.setNumbOfWands(table.getWands().length);
                }else if(p.getSeat()!=-1){
                   // while (!isDraw()){ }
                    response.setAction(Action.CHOOSE_WAND_LENGTH);
                }else{
                    //while (!isDraw()){ }
                    //System.out.println(p.getName() + " ceka");
                    response.setAction(Action.WAIT);
                }
                sendResponse(response);

                cyclicBarrier2.await();
                //8. citam odgovore igraca
                request = receiveRequest();
                //System.out.println(request);
                if(request.getAction() == Action.DRAW){

                    int wandCurr = table.getWand(request.getWand());
                    for (int i = 0; i < table.getWands().length; i++) {
                        System.out.print(table.getWand(i) + " | ");
                    }
                    setWand(wandCurr);

                    System.out.println("");
                    System.out.println("---------- Round:" + round + "-------");
                    System.out.println("WAND = " + wand);
                    System.out.println("wand poss = " + request.getWand());
                    System.out.println("wand = " + wandCurr);

                    if (getWand() == 1) {
                        System.out.println(request.getName() + " je izabrao duzi stapic" );
                        table.setWands();
                        setDraw(true);
                        setRound(getRound() + 1);
                    } else {
                        System.out.println(request.getName() + " je izabrao kraci stapic i napustio igru" );
                        System.out.println("IZBACUJEM GA");
                        Player newPlayer  = table.getOnHoldOne();
                        if(newPlayer!=null){
                            addSync(newPlayer);
                            table.swap(newPlayer, p);
                            table.resetWands();
                            System.out.println(newPlayer.getName() + "je SEO za sto na poziciju:" + round % 6);
                        }else{
                            System.out.println("nema vise ko da udje");
                        }
                        setDraw(true);
                        setRound(getRound() + 1);
                    }

                }
                if(request.getAction() == Action.CHOOSE_WAND_LENGTH){
                    while(!isDraw()){

                    }
                    int option = request.getOption();
                    if (option == 1) {
                        if (getWand() == 1) {
                            p.addPoint();
                            System.out.println(request.getName() + " je predvideo duzi stapic i pogodio" + p.getPoints());
                        } else {
                            System.out.println(request.getName() + " je predvideo duzi stapic i promasio" + p.getPoints() );
                        }
                    }
                    if (option == 0) {
                        if (getWand() == 0) {
                            System.out.println(request.getName() + " je predvideo kraci stapic i pogodio" + p.getPoints() );
                        } else {
                            System.out.println(request.getName() + " je predvideo kraci stapic i promasio" + p.getPoints() );
                        }
                    }
                }
                if(request.getAction() == Action.WAIT){
                    while(!isDraw()){

                    }
                    System.out.println(request + " player:" + p.getName() + " | chair:" + p.getSeat());
                }


                cyclicBarrier3.await();
                setDraw(false);
                Thread.sleep(2000);
                if(getRound()==MAX_ROUNDS || table.getOnHold().isEmpty()){
                    System.out.print(p.getName() + " poeni: " + p.getPoints() + "   |");
                    out.println("b");
                    break;
                }else {

                    out.println("a");
                }
            }



        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                out.close();
                in.close();
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Request receiveRequest() throws IOException {
        return gson.fromJson(in.readLine(), Request.class);
    }



    private void sendResponse(Response response) {
        String responseString = gson.toJson(response);
        out.println(responseString);
        out.flush();
    }

    private static synchronized void incNumPlayers(){
        seat++;
    }

    private static synchronized void incRes(){
        responses++;
    }

    private static synchronized void minusRes(){
        responses--;
    }
    public synchronized static void setDraw(boolean draw) {
        ServerThread.draw = draw;
    }

    public synchronized static boolean isDraw() {
        return draw;
    }

    public synchronized static void setResponses(int responses) {
        ServerThread.responses = responses;
    }

    public synchronized static int getResponses() {
        return responses;
    }

    public synchronized static boolean isReset() {
        return reset;
    }

    public synchronized static void setReset(boolean reset) {
        ServerThread.reset = reset;
    }

    public synchronized static int getRound() {
        return round;
    }

    public synchronized static void setRound(int round) {
        ServerThread.round = round;
    }

    public synchronized static int getMaxRounds() {
        return MAX_ROUNDS;
    }


    public synchronized static void setMaxRounds(int maxRounds) {
        MAX_ROUNDS = maxRounds;
    }

    public synchronized static void setWand(int wand) {
        ServerThread.wand = wand;
    }

    public synchronized static int getPom() {
        return pom;
    }

    public synchronized static void setPom(int pom) {
        ServerThread.pom = pom;
    }

    public synchronized static int getWand() {
        return wand;
    }

    public synchronized static void addSync(Player p){
        allPlayers.add(p);
    }

    public synchronized static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public synchronized static void setCurrentPlayer(Player currentPlayer) {
        ServerThread.currentPlayer = currentPlayer;
    }
}
