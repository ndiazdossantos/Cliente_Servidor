package tbr_servidor_cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Main {

    
    
    public static void main(String[] args) throws IOException{
       
            System.out.println("Creando socket servidor");

            ServerSocket serverSocket=new ServerSocket();

            System.out.println("Realizando el bind");

            InetSocketAddress addr=new InetSocketAddress("192.168.56.2",6666);
            
            serverSocket.bind(addr);

        System.out.println("Aceptando conexiones");
        while (true) {
        Socket newSocket = serverSocket.accept();
        System.out.println("Conexión recibida");
       
        new Hilos(newSocket).start();

}
            
            
            
            
        }
}



