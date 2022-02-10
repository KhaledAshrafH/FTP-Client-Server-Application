
//This program needs its files to work properly.

package ftp_server;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class FTP_Server {

    public static void main(String[] args) {

        try
        {
            ServerSocket server=new ServerSocket(33222); //Port to pass control information.
            ServerSocket server2=new ServerSocket(33223); //Port to send the data files to client.
            System.out.println("Server is running."); // to Know that Server is run.
            
            while(true)
            {
                Socket client=server.accept(); //Accept connection on port(33222) to control information.
                System.out.println("A new client "+client+" connected to the server."); //print information that client is connected.
                ClientConnection clientconnection=new ClientConnection(client,server2); //obj of class that threading clients and run parallel.
                clientconnection.start(); //calling thread.
            }
        }
        catch(IOException e)
        {
            System.out.println("Problem with Server Socket.");
        }
    }
    //Begin of thread.
    static class ClientConnection extends Thread
    {
        private Socket client;
        private ServerSocket server2; 
        ClientConnection(Socket client,ServerSocket server2) //Parameterized Constructor.
        {
            this.client=client;
            this.server2 = server2;
        }

        @Override
        public void run()
        {
            try
            {
                DataInputStream serverRead=new DataInputStream(client.getInputStream()); //to read information from client.
                DataOutputStream serverWrite=new DataOutputStream(client.getOutputStream()); //to send information to client.
           
            while(true){
            String checkUsers = serverRead.readUTF(); //recieve username from client.
            File file = new File("UserNames\\"+checkUsers+"\\"+checkUsers+".txt");
            boolean checkState; //check state of connection.
            //check username and password and sending file.
            if(file.exists()) {
                checkState=true; 
                serverWrite.writeBoolean(checkState); //send check of state to client.
                System.out.println("Username OK, password required.");
                Scanner checkPassword = new Scanner(file); //control the text of password file.
                String pass = "";
                pass = serverRead.readUTF(); //read password from client.
                String Password=checkPassword.nextLine().trim(); //check password that entered from client.
                
                //check password and sending file.
                
                //if client entered right password.
                if (pass.equals(Password)) {
                    serverWrite.writeUTF("Login Successfuly.\n");
                    System.out.println("Login Successfuly.");
                    //identifying file choose from client and sending it.
                    while(true){
                         String dir=serverRead.readUTF(); 
                         System.out.println("Client Said : "+dir); //print the command client(Show Direction).
                         serverWrite.writeUTF("the available dirs.(Movies, Music, Docs, …)."); //sending the available directories. 
                         String clientCheckChoose=serverRead.readUTF(); //read dierection from client.
                         String clientChoose; //to store "sending the available files to client".
                         String chooseFile; //to store "name of required file".
                         String extension; // to store extension of file.
                        //if client choose Movies.
                        if(clientCheckChoose.equalsIgnoreCase("Movies")){
                            clientChoose="the available files into this dir.(Joker, WAR, Free Fire,…).";
                            serverWrite.writeUTF(clientChoose);
                            chooseFile=serverRead.readUTF(); //read name of required file.
                            extension=".mp4";
                            serverWrite.writeUTF(extension); //send extension of file.
                        }
                        //if client choose Music.
                        else if(clientCheckChoose.equalsIgnoreCase("Music")){
                            clientChoose="the available files into this dir.(Amr Diab, Hamaki, Tamer Hossny,…).";
                            serverWrite.writeUTF(clientChoose);
                            chooseFile=serverRead.readUTF(); //read name of required file.
                            extension=".MP3";
                            serverWrite.writeUTF(extension); //send extension of file.
                        }
                        //if client choose Docs.
                        else if(clientCheckChoose.equalsIgnoreCase("Docs")){
                            
                            clientChoose="the available files into this dir.(File 1, File 2, File 3,…).";
                            serverWrite.writeUTF(clientChoose);
                            chooseFile=serverRead.readUTF(); //read name of required file.
                            extension=".doc";
                            serverWrite.writeUTF(extension); //send extension of file.
                        }
                        // if client entered wrong input.
                        else{
                            clientChoose="This dir not available!";
                            serverWrite.writeUTF(clientChoose);
                        }
                        Socket client2=server2.accept(); //accept connection on Port(33223).
                        
                        // Sending file..
                        
                        ObjectInputStream obj_input_stream=null; //read request from client.
                        String fileWanted=""; //store required file.
                        obj_input_stream = new ObjectInputStream(client2.getInputStream()); // receive the request.
                        try {
                            fileWanted = (String) obj_input_stream.readObject(); //read name of request.
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                        System.out.println("the requested file is "+ fileWanted); //print path of file required.
                        File f=new File(fileWanted);
                        byte [] bytesArray  = new byte [(int)f.length()]; //store size of required file.
                        FileInputStream file_input = new FileInputStream(f); //to read required file.
                        BufferedInputStream buffered = new BufferedInputStream(file_input); //store file.
                        buffered.read(bytesArray,0,bytesArray.length); //read size of file.
                        OutputStream output_stream = client2.getOutputStream(); //to send required file.
                        System.out.println("Sending File....");
                        output_stream.write(bytesArray,0,bytesArray.length); //send required file and its size.
                        //close connection on port 2 and remain on the first port.
                        output_stream.flush();
                        client2.close();
                        }
                        
                    }
                //if client entered wrong password.
                else {
                    serverWrite.writeUTF("Login Failed and the connection will terminate");
                    client.close();
                }
                
            }
            //if client entered wrong username.
            else if(!file.exists()){
                checkState=false;
                serverWrite.writeBoolean(checkState);
                
                //register if client entered wrong username.
              /*  
                System.out.println("Please enter a User name to register : ");
                String NewUser;
                NewUser=input.nextLine();
                File file2 = new File("UserNames\\"+NewUser+".txt");
                try (FileWriter file_writer = new FileWriter(file2)) {
                System.out.println("Enter your Password to create your account : ");
                String Password = input.nextLine();
                file_writer.write(Password);
                System.out.println("your Acount is created");
                }
              */      
            }
            
        } //ending while.
            
        } //ending try.
            catch (IOException ex) {
            System.out.println("Connection is terminated");
            }
            
            } //ending class ClientConnection.
        
        }
    
    }