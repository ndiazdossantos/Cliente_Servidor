package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            
            Scanner sc = new Scanner(System.in);
            
            System.out.println("Creando socket cliente");
            Socket clienteSocket = new Socket();
            System.out.println("Estableciendo la conexi�n");

            InetSocketAddress addr = new InetSocketAddress("192.168.56.2", 6666);
            clienteSocket.connect(addr);

            InputStream is = clienteSocket.getInputStream();
            OutputStream os = clienteSocket.getOutputStream();

            System.out.println("Enviando mensaje");
            System.out.println("Introduce mensaje1");
            String mensaje = sc.nextLine();
            os.write(mensaje.getBytes());
            
             System.out.println("Introduce mensaje2");
            String mensaje2 = sc.nextLine();
            os.write(mensaje2.getBytes());
            
            System.out.println("Introduce mensaje13");
            String mensaje3 = sc.nextLine();
            os.write(mensaje3.getBytes());

            System.out.println("Mensaje enviado");

            System.out.println("Cerrando el socket cliente");

            clienteSocket.close();

            System.out.println("Terminado");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}