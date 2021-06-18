package view.admin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainThread extends Thread {

    private final Socket socket;
    public DataInputStream flujoE;
    public DataOutputStream flujoS;

    public MainThread(Socket s) {
        this.socket = s;

        try {
            flujoE = new DataInputStream(s.getInputStream());
            flujoS = new DataOutputStream(s.getOutputStream());
            flujoS.writeUTF("Conectado Correctamente");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Ha ocurrido un error al conectarse" + "Error al conectarse\n" + ex.getLocalizedMessage());
        }
    }

    @Override
    public void run() {
        String valor;
        while (true) {
            try {
                valor = flujoE.readUTF();
                System.out.println(valor);
                ChatServer.broadcast(valor);
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Ha ocurrido un problema al iniciar el hilo" + "Error en el hilo\n" + ex.getLocalizedMessage());
                return;
            }
        }
    }

    public void closeConnexion() {
        try {
            flujoE.close();
            flujoS.close();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Ha habido un error al cerrar las conexiones");
            System.out.println("Ha ocurrido un error al cerrar la conexión" + "Error al cerrar la conexión\n" + ex.getLocalizedMessage());
        }
    }

    public void sendMessage(String msg) {
        try {
            flujoS.writeUTF(msg);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Ha ocurrido un error al enviar el mensaje" + " Error al enviar\n" + ex.getLocalizedMessage());
        }
    }
}
