package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1111);
        ExecutorService executorService = Executors.newCachedThreadPool();

        Table table = new Table();
        while (true){
            Socket socket = serverSocket.accept();
            executorService.submit(new ServerThread(socket, table));

        }
    }
}
