package com.tools;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

class EchoServer {

    public static void main(String[] args) {
        int port = 7;
                
        try (ServerSocket server = new ServerSocket(port)) {
            while (true) {
                try (Socket client = server.accept();
                     BufferedReader in = new BufferedReader(
                         new InputStreamReader(client.getInputStream()));
                     PrintWriter out = new PrintWriter(
                         client.getOutputStream(), true)) {
                    
                    System.out.println("Client connected: " + 
                        client.getInetAddress().getHostAddress());
                    
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println("Received: " + message);
                        out.println(message);
                    }
                    
                    System.out.println("Client disconnected");
                    
                } catch (IOException e) {
                    System.err.println("Error handling client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}