package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args){
        try{
            System.out.println("Creando socket servidor");

            ServerSocket serverSocket=new ServerSocket();

            System.out.println("Realizando el bind");

            InetSocketAddress addr=new InetSocketAddress("192.168.56.2",6666);
            serverSocket.bind(addr);

            System.out.println("Aceptando conexiones");

            Socket newSocket= serverSocket.accept();

            System.out.println("Conexión recibida");
    
    // se mantiene porque es la entrada y salida del socket que se utiliza en bytes
            
            InputStream is=newSocket.getInputStream();
            OutputStream os=newSocket.getOutputStream();
   
            // para la leer la entrada y la salida de los datos
            
           DataInputStream is2=new DataInputStream(newSocket.getInputStream());
           DataOutputStream os2=new DataOutputStream(newSocket.getOutputStream());

         //   byte[] mensaje=new byte[5];
           
            int[] mensaje = new int [5];
            
            //int suma=0;
            for (int i = 0; i<5; i++){
             mensaje[i]= is2.readInt();
            }
            
           // int num = mensaje[0];
            /*
           System.out.println("Mensaje recibido: "+num);
            suma = suma+num;
            }
            String resultado = "Resultado de la suma: "+suma;
            os.write(resultado.getBytes());
            
            */
            
            
        
            int mensaje2 = is2.readInt();
            int opcion = mensaje2;
            int resultado = 0;
            switch (opcion){
                case 1:
                    for(int i = 0; i<5; i++){
                    resultado = resultado+mensaje[i];   
                    }
                    os2.writeInt(resultado);
                    break;
                case 2:
                    //resta no operativa ya que se partiria de 0
                    for(int i = 0; i<5; i++){
                    resultado = resultado-mensaje[i];   
                    }
                    os2.writeInt(resultado);
                    break;
                case 3:
                    resultado=1;
                    for(int i = 0; i<5; i++){
                    resultado = resultado*mensaje[i];   
                    }
                    os2.writeInt(resultado);
                    break;
                  
                case 4:
                      //division no operativa y que el divisor sería 0
                    for(int i = 0; i<5; i++){
                    resultado = resultado/mensaje[i];   
                    }
                    os2.writeInt(resultado);
                    break;
            }
            
            
            
            
          
            System.out.println("Cerrando el nuevo socket");

            newSocket.close();

            System.out.println("Cerrando el socket servidor");

            serverSocket.close();

            System.out.println("Terminado");

        }catch (IOException e) {
        }
    }
}

