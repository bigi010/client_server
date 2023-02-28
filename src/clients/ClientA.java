package clients;

import java.io.*;
import java.net.*;

public class ClientA {
    public static void main(String[] args) throws IOException{
        Socket socket = null;
        BufferedReader bufferedReader = null;
        BufferedReader brInputStream = null;
        PrintWriter writer = null;
        String line = null;

        try{
            socket = new Socket("localhost", 4252);
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            brInputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            writer.flush();
        }catch (Exception e){
            System.out.println("Error encountered");
        }
        System.out.println("Enter your command (or Enter 'Exit' to end):");
        String response = null;
        try{
            line = bufferedReader.readLine();
            while(line.compareTo("Exit")!=0){
                writer.println(line);
                writer.flush();
                response = brInputStream.readLine();
                System.out.println("Response from Server.Server:" +response);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            brInputStream.close();
            writer.close();
            socket.close();

            System.out.println("The connection has been closed!");
        }catch(Exception e){
            System.out.println("Error has occured!");
        }
    }
}
