package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        try{
            
            Scanner sc = new Scanner(System.in);
            
            System.out.println("Creando socket servidor");

            ServerSocket serverSocket=new ServerSocket();

            System.out.println("Realizando el bind");

            InetSocketAddress addr=new InetSocketAddress("192.168.56.2",6666);
            serverSocket.bind(addr);

            System.out.println("Aceptando conexiones");

            Socket newSocket= serverSocket.accept();

            System.out.println("Conexi�n recibida");

            InputStream is=newSocket.getInputStream();
            OutputStream os=newSocket.getOutputStream();

            byte[] mensaje=new byte[25];
            byte[] mensaje2=new byte[25];
            byte[] mensaje3=new byte[25];
             
            is.read(mensaje);
            System.out.println("Mensaje recibido: "+new String(mensaje));
            
            
            System.out.println("Introduce Contestacion1");
            String contestacion1 = sc.nextLine();
            os.write(contestacion1.getBytes());
            
            is.read(mensaje2);
            System.out.println("Mensaje2 recibido: "+new String(mensaje2));
            
            System.out.println("Introduce Contestacion1");
            String contestacion2 = sc.nextLine();
            os.write(contestacion2.getBytes());
            
            is.read(mensaje3);
            System.out.println("Mensaje3 recibido: "+new String(mensaje3));
            
            System.out.println("Introduce Contestacion3");
            String contestacion3 = sc.nextLine();
            os.write(contestacion1.getBytes());
            


            System.out.println("Cerrando el nuevo socket");

            newSocket.close();

            System.out.println("Cerrando el socket servidor");

            serverSocket.close();

            System.out.println("Terminado");

        }catch (IOException e) {
        }
    }
}