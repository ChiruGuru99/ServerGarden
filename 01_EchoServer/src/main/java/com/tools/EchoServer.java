package com.tools;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class EchoServer{

    public static void main(String[] args){

        try(ServerSocket server = new ServerSocket(7)){
            Socket client = server.accept();
            while(true){               
                InputStreamReader i = new InputStreamReader(client.getInputStream());
                BufferedReader in = new BufferedReader(i);
                String msg =in.readLine();
                System.out.println(msg);
                client.getOutputStream().write(msg.getBytes());

                
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}