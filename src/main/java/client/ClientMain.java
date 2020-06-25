package client;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientMain {

    public static final int MAX_PLAYERS = 9;

    public static void main(String[] args) throws IOException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        Random r = new Random();

        for (int i = 0; i < MAX_PLAYERS; i++) {
            int pom = r.nextInt(1000)+1;
            scheduledExecutorService.schedule(new ClientThread(i), pom, TimeUnit.MILLISECONDS);
            System.out.println("P"+ i + "   " + pom);
        }

        scheduledExecutorService.shutdown();
    }
}
