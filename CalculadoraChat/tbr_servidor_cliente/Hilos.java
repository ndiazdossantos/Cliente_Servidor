/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tbr_servidor_cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author oracle
 */
public class Hilos extends Thread{
    
    static Socket socket;
    static ServerSocket serverSocket;
    static DataInputStream bufferDeEntrada = null;
    static DataOutputStream bufferDeSalida = null;
    Scanner escaner = new Scanner(System.in);
    static String COMANDO_TERMINACION = "salir()";
    private Socket newSocket;

    public static void levantarConexion(int puerto) {
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
    public static void flujos() {
        try {
            bufferDeEntrada = new DataInputStream(socket.getInputStream());
            bufferDeSalida = new DataOutputStream(socket.getOutputStream());
            bufferDeSalida.flush();
        } catch (IOException e) {
            mostrarTexto("Error en la apertura de flujos");
        }
    }

    public static void recibirDatos() {
        String st = "";
        try {
            do {
                st = (String) bufferDeEntrada.readUTF();
                mostrarTexto("\n[Cliente]: " + st);
            } while (!st.equals(COMANDO_TERMINACION));
        } catch (IOException e) {
            cerrarConexion();
        }
    }


    public static void enviar(String s) {
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

    public static void escribirDatos() {
        String entrada1 = "";
        while (true) {
            entrada1 = JOptionPane.showInputDialog("Escribe mensaje al cliente: ");
            enviar(entrada1);   
        }
    }

    public static void cerrarConexion() {
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

    public static void ejecutarConexion(int puerto) {
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
    
  

   public Hilos(Socket newSocket) {
        this.newSocket = newSocket;
    }
    
    
    public void run(){
            
        Scanner sc = new Scanner(System.in);
        mostrarTexto("Habilita puerto para el CHAT distinto a 6666 : ");
        String puerto = sc.nextLine();
                  
            
            InputStream is=null;
        try {
            // se mantiene porque es la entrada y salida del socket que se utiliza en bytes
            is = newSocket.getInputStream();
            OutputStream os=newSocket.getOutputStream();
            // para la leer la entrada y la salida de los datos
            
            DataInputStream is2=new DataInputStream(newSocket.getInputStream());
            DataOutputStream os2=new DataOutputStream(newSocket.getOutputStream());
            int opcion1;
            do{
                
                
                
                opcion1 = is2.readInt();
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
                            for(int i = 0; i<2; i++){
                                resultado = mensaje[0]-mensaje[1];
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
                            for(int i = 0; i<2; i++){
                                resultado = mensaje[0]/mensaje[1];
                            }
                            os2.writeInt(resultado);
                            break;
                    }
                    
                }  if(opcion1==2){
                    
                    
                    
                    ejecutarConexion(5050);
                    escribirDatos();
                    
                    
                }
                
            }while(opcion1!=3);
            
            System.out.println("Cerrando el nuevo socket");
            newSocket.close();
            System.out.println("Cerrando el socket servidor");
            serverSocket.close();
            System.out.println("Terminado");
            
        } catch (IOException ex) {
            Logger.getLogger(Hilos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(Hilos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

     
        }
    }
            
    
    

