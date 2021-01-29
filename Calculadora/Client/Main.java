package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
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
           
            /*
            InputStream is = clienteSocket.getInputStream();
            OutputStream os = clienteSocket.getOutputStream();
            */
            
            // utilizamos la clase DataInputStream para trabajar directamente con los tipos de datos primitivos (documentación en acceso a datos)
           
            DataInputStream is = new DataInputStream(clienteSocket.getInputStream());
            DataOutputStream os = new DataOutputStream(clienteSocket.getOutputStream());
          

            System.out.println("Enviando mensaje");
            
            ArrayList<Integer>list = new ArrayList<>();
            System.out.println("Introduce numero1");
            int numero1=sc.nextInt();
            System.out.println("Introduce numero2");
            int numero2=sc.nextInt();
            System.out.println("Introduce numero3");
            int numero3=sc.nextInt();
            System.out.println("Introduce numero4");
            int numero4=sc.nextInt();
            System.out.println("Introduce numero5");
            int numero5=sc.nextInt();
            
            list.add(numero1);
            list.add(numero2);
            list.add(numero3);
            list.add(numero4);
            list.add(numero5);
            
            // cada vez que usemos un writeInt vamos a tener que usar un readInt
            for(int i=0;i<list.size();i++){
                os.writeInt(list.get(i));
            }
            
           // int mensaje=is.readInt();
           // System.out.println("Mensaje recibido: "+mensaje);
            
            
         
            System.out.println("Teclee Nº \n1 Suma  \n2 Resta \n3 Multicplicación \n4 División");
            int opcion =sc.nextInt();
            os.writeInt(opcion);
            
            int mensaje2 =is.readInt();
            System.out.println("Resultado operación: "+mensaje2);
            System.out.println("Mensaje enviado");

            System.out.println("Cerrando el socket cliente");

            clienteSocket.close();

            System.out.println("Terminado");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}