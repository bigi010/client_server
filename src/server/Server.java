package server;

import java.net.*;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(4252);
        while(true){
            try{
                Socket socket = serverSocket.accept();
                System.out.println("Connection has been made");
                ServerThread serverThread = new ServerThread(socket);
                serverThread.start();
            }catch (Exception e){
                System.out.println("Connection Error");
            }
        }
    }
}


