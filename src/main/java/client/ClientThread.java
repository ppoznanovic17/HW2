package client;

import com.google.gson.Gson;
import model.Action;
import model.Request;
import model.Response;

import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.UUID;

public class ClientThread implements Runnable {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private int name;

    private int wand;
    private int option;

    private Gson gson;

    public ClientThread(int name) throws IOException {
        socket = new Socket("localhost", 1111);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        this.name = name;
        gson = new Gson();
    }

    @Override
    public void run() {
        try {

            UUID id = UUID.randomUUID();

            //1. saljem zahtav za stolicu
            Request request = new Request();
            request.setAction(Action.REQUEST_CHAIR);
            request.setName(name);
            request.setId(id);
            String requestString = gson.toJson(request);
            out.println(requestString);
            out.flush();

            //4. citam odgovor za stolicu
            String responseString = in.readLine(); //1i
            Response response = gson.fromJson(responseString, Response.class);
           if(response.getAction() == Action.WAIT){
               System.out.println(name + " nije seo");
           }
           if(response.getAction() == Action.SIT){
               System.out.println(name + " seo");
           }

           do{

               //6. citam sta da radim
               responseString = in.readLine();
               response = gson.fromJson(responseString, Response.class);

               //7. saljem sta radim
               request = new Request();
               if(response.getAction() == Action.DRAW){
                   request.setAction(Action.DRAW);
                   request.setName(name);
                   request.setId(id);
                   Random rand = new Random();
                   int pom = rand.nextInt(response.getNumbOfWands());
                   request.setWand(pom);
                   System.out.println(name + " bira");
               }else if(response.getAction() == Action.CHOOSE_WAND_LENGTH){
                   request.setAction(Action.CHOOSE_WAND_LENGTH);
                   request.setName(name);
                   request.setId(id);
                   Random rand = new Random();
                   int pom = rand.nextInt(2);
                   request.setOption(pom);
                   System.out.println(name +  " pogadja");
               }else if(response.getAction() == Action.WAIT){
                   request.setName(name);
                   request.setId(id);
                   request.setAction(Action.WAIT);
                   System.out.println(name +  " ceka");
               }
               requestString = gson.toJson(request);
               out.println(requestString);
               out.flush();

                if(in.readLine() == "b"){
                    break;
                }

           }while (true);




        } catch (Exception e) {
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

    public void setWand(int wand) {
        this.wand = wand;
    }

    public int getWand() {
        return wand;
    }
}
