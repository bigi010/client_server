

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.*;
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

                /*InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String command = bufferedReader.readLine();
                System.out.println("Command received from Client: "+ command);*/
            }catch (Exception e){
                System.out.println("Connection Error");
            }
        }
    }
}

class ServerThread extends Thread{
    String line = null;
    BufferedReader bufferedReader = null;
    PrintWriter writer = null;
    Socket socket = null;
    public ServerThread(Socket socket){
        this.socket = socket;
    }
    public void run(){
        try{
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            line = bufferedReader.readLine();
            while (line.compareTo("Exit")!=0){
                writer.println(line);
                writer.flush();
                System.out.println("Command from Client:"  +line);

                String path = "C:\\Users\\BIGI\\Desktop\\Java_Intern\\" ;
                String database;
                String table;
                int startIndex;
                int endIndex;
                String column;
                String value;

                String[] command = line.split(" ");
                if (command[0].equalsIgnoreCase("create")){
                    if(command[1].equalsIgnoreCase("database")){
                        database = command[2];
                        File file = new File(path+database);
                        if(file.mkdir()==true){
                            System.out.println("A folder with the name "+database+" has been created");
                        }else{
                            System.out.println("An error has occured while creating the folder");
                        }
                    }else{
                        table = command[2];
                        FileWriter fileWriter = new FileWriter(path+table+".json");
                        System.out.println("A file with the name "+table+".json has been created");
                        fileWriter.close();
                    }
                } else if (command[0].equalsIgnoreCase("insert")) {
                    table = command[2];
                    startIndex = line.indexOf("(")+1;
                    endIndex = line.indexOf(")");
                    column =line.substring(startIndex,endIndex);
                    String[] columnNames = column.split(",");

                    startIndex = line.indexOf("values(")+7;
                    endIndex = line.length()-1;
                    value = line.substring(startIndex,endIndex);
                    String[] valueNames = value.split(",");

                    JsonArray jsonArray = new JsonArray();
                    JsonObject jsonObject = new JsonObject();

                    for (int i =0; i<columnNames.length; i++){
                        jsonObject.addProperty(columnNames[i],valueNames[i]);
//                        System.out.println(columnNames[i]+":"+valueNames[i]);
                    }
                    jsonArray.add(jsonObject);
                    FileWriter fileWriter = new FileWriter(path+table+".json",true);
                    fileWriter.write(jsonArray.toString());
                    fileWriter.flush();
                } else if(command[0].equalsIgnoreCase("select")){

                }
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            writer.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Error Occured!!");;
        }
    }
}