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

    private Socket socket;
    private ServerSocket serverSocket;
    private DataInputStream bufferDeEntrada = null;
    private DataOutputStream bufferDeSalida = null;
    Scanner escaner = new Scanner(System.in);
    final String COMANDO_TERMINACION = "salir()";

    public void levantarConexion(int puerto) {
        try {
            serverSocket = new ServerSocket(puerto);
            mostrarTexto("Esperando conexión entrante en el puerto  " + String.valueOf(puerto) + "... habilitado para el chat ");
            socket = serverSocket.accept();
            mostrarTexto("Conexión establecida con: " + socket.getInetAddress().getHostName() + "\n\n\n");
        } catch (Exception e) {
            mostrarTexto("Error en levantarConexion(): " + e.getMessage());
            System.exit(0);
        }
    }
    public void flujos() {
        try {
            bufferDeEntrada = new DataInputStream(socket.getInputStream());
            bufferDeSalida = new DataOutputStream(socket.getOutputStream());
            bufferDeSalida.flush();
        } catch (IOException e) {
            mostrarTexto("Error en la apertura de flujos");
        }
    }

    public void recibirDatos() {
        String st = "";
        try {
            do {
                st = (String) bufferDeEntrada.readUTF();
                mostrarTexto("\n[Cliente]: " + st);
                System.out.print("\n[Servidor]: ");
            } while (!st.equals(COMANDO_TERMINACION));
        } catch (IOException e) {
            cerrarConexion();
        }
    }


    public void enviar(String s) {
        try {
            bufferDeSalida.writeUTF(s);
            bufferDeSalida.flush();
        } catch (IOException e) {
            mostrarTexto("Error en enviar(): " + e.getMessage());
        }
    }

    public static void mostrarTexto(String s) {
        System.out.print(s);
    }

    public void escribirDatos() {
        String entrada1 = "";
        while (true) {
            entrada1 = JOptionPane.showInputDialog("Escribe mensaje al cliente: ");
            enviar(entrada1);   
        }
    }

    public void cerrarConexion() {
        try {
            bufferDeEntrada.close();
            bufferDeSalida.close();
            socket.close();
        } catch (IOException e) {
          mostrarTexto("Excepción en cerrarConexion(): " + e.getMessage());
        } finally {
            mostrarTexto("Conversación finalizada....");
            System.exit(0);

        }
    }

    public void ejecutarConexion(int puerto) {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        levantarConexion(puerto);
                        flujos();
                        recibirDatos();
                    } finally {
                        cerrarConexion();
                    }
                }
            }
        });
        hilo.start();
    }
    
    
    
    public static void main(String[] args){
        
        Main servidor = new Main();           
        Scanner sc = new Scanner(System.in);
        mostrarTexto("Habilita puerto para el CHAT distinto a 6666 : ");
        String puerto = sc.nextLine();
        
        
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
           
           

           
            int opcion1 = is2.readInt(); 
            int[] mensaje = new int [2];
            
            //seleccionamos la opción
            
            if(opcion1==1){
            
            
            for (int i = 0; i<2; i++){
             mensaje[i]= is2.readInt();
            }
         
            
            
        
            int mensaje2 = is2.readInt();
            int opcion = mensaje2;
            int resultado = 0;
            switch (opcion){
                case 1:
                    for(int i = 0; i<2; i++){
                    resultado = resultado+mensaje[i];   
                    }
                    os2.writeInt(resultado);
                    break;
                case 2:
                    //resta no operativa ya que se partiria de 0
                    for(int i = 0; i<2; i++){
                    resultado = resultado-mensaje[i];   
                    }
                    os2.writeInt(resultado);
                    break;
                case 3:
                    resultado=1;
                    for(int i = 0; i<2; i++){
                    resultado = resultado*mensaje[i];   
                    }
                    os2.writeInt(resultado);
                    break;
                  
                case 4:
                      //division no operativa y que el divisor sería 0
                    for(int i = 0; i<2; i++){
                    resultado = resultado/mensaje[i];   
                    }
                    os2.writeInt(resultado);
                    break;
            }
            
           }  else{
               
                 
        
         servidor.ejecutarConexion(Integer.parseInt(puerto));
         servidor.escribirDatos();
                
                
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



