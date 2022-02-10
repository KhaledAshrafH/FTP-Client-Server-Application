
package ftp_client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class FTP_Client {
        public static void main(String[] args) throws IOException {
            try {
                Socket server=new Socket(InetAddress.getLocalHost(),33222); //connect on server with port(33222).
                Scanner input=new Scanner(System.in);
                DataInputStream clientRead = new DataInputStream(server.getInputStream()); //to read information from server.
                DataOutputStream clientWrite = new DataOutputStream(server.getOutputStream()); //to send information to server.
                System.out.println("Enter your UserName : ");
                String userName=input.nextLine();
                clientWrite.writeUTF(userName); //send username to server.
                boolean checkState=clientRead.readBoolean(); //read state of connection from server.
                //check username and password and recieving file.
                if(checkState){
                    String Password;
                    System.out.println("This Username exist exactly");
                    System.out.println("Please enter your password : ");
                    Password=input.nextLine();
                    clientWrite.writeUTF(Password); //send password to server.
                    String resultCheck=clientRead.readUTF(); //read result of check matching username and password.
                    //if client entered wrong username or password.
                    if("Login Failed and the connection will terminate".equals(resultCheck)) {
                        System.out.println(resultCheck);
                        clientRead.close();
                        clientWrite.close();
                        server.close();
                    }
                    //if client entered right information
                    else{
                        System.out.println(resultCheck);
                        //identifying file choose from client and recieving it.
                        while(true){
                        String close;
                        System.out.println("Do you want close connection?(Y/N)");
                        close=input.nextLine();
                        if(close.equalsIgnoreCase("y")) return; //if client want to close connection.
                        //continue connection...
                        System.out.println("What do you want");
                        clientWrite.writeUTF("Show my directories"); //send this command to server.
                        String direction=clientRead.readUTF(); //recieve the available Directories.
                        System.out.println(direction);
                        String myDirection=input.nextLine();
                        clientWrite.writeUTF(myDirection); //send My wanted Direction to server.
                        String choose=clientRead.readUTF(); //recieve the available files.
                        System.out.println(choose);
                        String mychoose=input.nextLine();
                        clientWrite.writeUTF(mychoose); //send name of my wanted file.
                        String Extension=clientRead.readUTF(); //recieve extension of file.
                        
                        
                        Socket server2=new Socket(InetAddress.getLocalHost(),33223); //connect on server with port(33223).
                        
                        //Receiving File ...
                        
                        System.out.println("Receiving File ...");
                        int sizeOfFile=600000000;
                        int sizeOfArray;
                        int current_size = 0;

                        // Request the file
                        ObjectOutputStream obj_output_stream= new ObjectOutputStream(server2.getOutputStream()); //to write obj.
                        obj_output_stream.writeObject("Directories\\"+myDirection+"\\"+mychoose+Extension); //path of File wanted.
                        obj_output_stream.flush(); 
                        // receive file
                        byte [] bytesArray  = new byte [sizeOfFile]; //create array with temporary size.
                        InputStream input_stream = server2.getInputStream(); //read obj.
                        FileOutputStream file_input = new FileOutputStream("UserNames\\"+userName+"\\"+myDirection+"\\"+mychoose+Extension);//path to downloud in it
                        BufferedOutputStream Buffered = new BufferedOutputStream(file_input); //store file.
                        sizeOfArray = input_stream.read(bytesArray,0,bytesArray.length); //store size of file.
                        current_size = sizeOfArray;
                        
                        while(sizeOfArray > -1) {
                            sizeOfArray = input_stream.read(bytesArray, current_size, (bytesArray.length-current_size)); 
                            if(sizeOfArray >= 0) current_size += sizeOfArray;
                        } 
                        Buffered.write(bytesArray, 0 , current_size); //write request file.

                        Buffered.flush();
                        Buffered.close();
                        server2.close();

                        }
                    }
                        
                }
                //if client entered wrong username.
                else{
                    System.out.println("This Username doesn't exist!");
                    System.out.println("Login Failed and the connection will terminate");
                    clientRead.close();
                    clientWrite.close();
                    server.close();
                }
                
            } catch (IOException ex) {
                System.out.println(ex);
              }
            
        }
        
}
                 


