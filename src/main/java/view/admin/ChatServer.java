package view.admin;

import util.AdminThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    public static List<MainThread> serverThreads = new ArrayList<>();
    public static boolean DEAD_CODE = false;
    public ServerSocket serverSocket = null;

    public ChatServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println(serverSocket.getLocalSocketAddress());

        } catch (IOException e) {
            e.printStackTrace();
        }

        AdminThread.threadExecutorPool.execute(() -> {
            while (true) {
                try {

                    if (serverSocket == null) {
                        DEAD_CODE = true;
                        return;
                    }

                    MainThread nuevoST = new MainThread(serverSocket.accept());
                    nuevoST.start();
                    serverThreads.add(nuevoST);
                } catch (IOException ex) {
                    DEAD_CODE = true;
                    System.out.println(ex.getMessage());
                    System.out.println("Ha ocurrido un error al enviar el mensaje" + " Error al enviar");
                    return;
                }
            }
        });
    }

    public static void broadcast(String msg) {
        for (MainThread mainThread : serverThreads) {
            if (msg.compareTo("") != 0) {
                System.out.println(mainThread.toString());
                mainThread.sendMessage(msg);
            }
        }
    }

    public void closeServer() {
        for (MainThread serverThread : serverThreads) {
            serverThread.closeConnexion();
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
