package tbr_cliente_servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Main {

    private Socket socket;
    private DataInputStream bufferDeEntrada = null;
    private DataOutputStream bufferDeSalida = null;
    Scanner teclado = new Scanner(System.in);
    final String COMANDO_TERMINACION = "exit()";

    public void levantarConexion(String ip, int puerto) {
        try {
            socket = new Socket(ip, puerto);
            mostrarTexto("Conectado a :" + socket.getInetAddress().getHostName());
        } catch (Exception e) {
            mostrarTexto("Excepción al levantar conexión: " + e.getMessage());
            System.exit(0);
        }
    }

    public static void mostrarTexto(String s) {
        System.out.println(s);
    }

    public void abrirFlujos() {
        try {
            bufferDeEntrada = new DataInputStream(socket.getInputStream());
            bufferDeSalida = new DataOutputStream(socket.getOutputStream());
            bufferDeSalida.flush();
        } catch (IOException e) {
            mostrarTexto("Error en la apertura de flujos");
        }
    }

    public void enviar(String s) {
        try {
            bufferDeSalida.writeUTF(s);
            bufferDeSalida.flush();
        } catch (IOException e) {
            mostrarTexto("IOException on enviar");
        }
    }

    public void cerrarConexion() {
        try {
            bufferDeEntrada.close();
            bufferDeSalida.close();
            socket.close();
            mostrarTexto("Conexión terminada");
        } catch (IOException e) {
            mostrarTexto("IOException on cerrarConexion()");
        } finally {
            System.exit(0);
        }
    }

    public void ejecutarConexion(String ip, int puerto) {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    levantarConexion(ip, puerto);
                    abrirFlujos();
                    recibirDatos();
                } finally {
                    cerrarConexion();
                }
            }
        });
        hilo.start();
    }

    public void recibirDatos() {
        String st = "";
        try {
            do {
                st = (String) bufferDeEntrada.readUTF();
                mostrarTexto("\n[Servidor]: " + st);
            } while (!st.equals(COMANDO_TERMINACION));
        } catch (IOException e) {
        }
    }

    public void escribirDatos() {
        String entrada = "";
        while (true) {
             entrada = JOptionPane.showInputDialog("Escribe mensaje al servidor: ");
            if (entrada.length() > 0) {
                enviar(entrada);
            }
        }
    }

    public static void main(String[] args) {
       
        try {

            Scanner sc = new Scanner(System.in);

            System.out.println("Creando socket cliente");
            Socket clienteSocket = new Socket();
            System.out.println("Estableciendo la conexi�n");

            InetSocketAddress addr = new InetSocketAddress("192.168.56.2", 6666);
            clienteSocket.connect(addr);

         
            DataInputStream is = new DataInputStream(clienteSocket.getInputStream());
            DataOutputStream os = new DataOutputStream(clienteSocket.getOutputStream());
           
            int opcion1;
            int opcion;
            int numero1;
            int numero2;
            int numero3;
            int numero4;
            int numero5;
            
            do{
                   
            opcion1 = Integer.parseInt(JOptionPane.showInputDialog("Introduce que deseas realizar: "+
                    "\n1 Operaciones "+ 
                    "\n2 Hablar " +
                    "\n3 Cerrar Conexión"));
       
            os.writeInt(opcion1);
  
            
                
            
            
            switch (opcion1) {
                
                case 1:
                    
                System.out.println("Enviando mensaje");

                ArrayList<Integer> list = new ArrayList<>();
                numero1= Integer.parseInt(JOptionPane.showInputDialog("1er Operando: "));
                numero2= Integer.parseInt(JOptionPane.showInputDialog("2do Operand: "));
         

                list.add(numero1);
                list.add(numero2);
         
                // cada vez que usemos un writeInt vamos a tener que usar un readInt
                for (int i = 0; i < list.size(); i++) {
                    os.writeInt(list.get(i));
                }

             opcion = Integer.parseInt(JOptionPane.showInputDialog("Teclee Nº: "+
                     "\n1 Suma "+  
                     "\n2 Resta "+
                     "\n3 Multicplicación "+ 
                     "\n4 División "));
               
                os.writeInt(opcion);

                int mensaje2 = is.readInt();
                System.out.println("Resultado operación: " + mensaje2);
           
               System.out.println("Mensaje enviado");
               
               break;
              
                case 2:
                    
                Main cliente = new Main();
                
                Scanner escaner = new Scanner(System.in);
                mostrarTexto("Ingrese Puerto: ");
                String puerto = escaner.nextLine();
               
                
                cliente.ejecutarConexion("192.168.56.2",  Integer.parseInt(puerto));
                cliente.escribirDatos();
            
                break;
                
                case 3:
                    
            System.out.println("Cerrando el socket cliente");

            clienteSocket.close();

            System.out.println("Terminado");
            
            break;
             
            }
            
        }while(opcion1!=3);
            
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }
}
