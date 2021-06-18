/*
 * Created by JFormDesigner on Mon Jun 14 05:15:59 CEST 2021
 */

package view.client;

import model.rest.pojo.Message;
import util.AdminThread;
import util.DateTransform;
import viewmodel.ViewModel;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * @author unknown
 */
public class ChatClient extends JFrame {
    private static ChatClient chatClient;
    private final ViewModel viewModel = new ViewModel();
    private Socket client;
    private DataInputStream flujoE;
    private DataOutputStream flujoS;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JTextField tfUserName;
    private JScrollPane spMessage;
    private JTextArea taMessage;
    private JLabel jlMessagHistory;
    private JLabel jlUserName;
    private JTextField tfSendMessage;
    private JLabel jlSendMessage;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public ChatClient() {
        initComponents();

        DefaultCaret caret = (DefaultCaret) taMessage.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        taMessage.setVisible(false);
        jlSendMessage.setVisible(false);
        tfSendMessage.setVisible(false);

        tfUserName.addActionListener(this::onKeyPressedUserName);
        tfSendMessage.addActionListener(this::onKeyPressedSendMessage);
    }

    public static void main(String[] args) {
        getConnexion();
    }

    public static void getConnexion() {
        chatClient = new ChatClient();
        chatClient.setVisible(true);
        chatClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatClient.startClient("127.0.0.1", 5000);
    }

    private void onKeyPressedSendMessage(ActionEvent event) {
        taMessage.setVisible(true);

        tfUserName.setVisible(false);
        jlUserName.setVisible(false);

        String userName = tfUserName.getText();
        String message = tfSendMessage.getText();

        if (message.isEmpty()) {
            Alert.show(this, "Inserte un mensaje", "Error campo vacío");
            return;
        }

        if (chatClient == null) {
            newSessions();
        }

        System.out.println("Usuario que envía el texto: " + userName);

        try {
            if (taMessage.getText().contains("Conectado Correctamente")) {
                viewModel.getAllMessages();
                taMessage.setText(viewModel.getGlobalMessageList().toString());
            }
            flujoS.writeUTF(userName + ": " + message);
            viewModel.insertMessage(new Message(message, userName, null, DateTransform.dateSQLHHMMSS(new Date().getTime())));
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());

            if (ex.getLocalizedMessage().contains("error")) {
                Alert.show(this, "El servidor que aloja las conexiones ha cerrado", "Error al conectar");
                newSessions();
            }
        } finally {
            tfSendMessage.setText("");
        }
    }

    public void newSessions() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        chatClient.dispose();

        chatClient = new ChatClient();
        chatClient.setVisible(true);
        chatClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatClient.startClient("127.0.0.1", 5000);
    }

    private void onKeyPressedUserName(ActionEvent e) {
        String userName = tfUserName.getText();

        if (userName.isEmpty()) {
            Alert.show(this, "Inserte un nombre", "Error campo vacío");
            return;
        }

        try {
            viewModel.getAllMessages();
            if (flujoS == null) {

                Alert.show(this, "El servidor que aloja las conexiones ha cerrado", "Error al conectar");
                newSessions();
                return;
            }
            flujoS.writeUTF("\n" + userName + ", ha entrado a la sala.\n ");
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        jlUserName.setVisible(false);
        tfUserName.setVisible(false);
        jlSendMessage.setVisible(true);
        tfSendMessage.setVisible(true);

        taMessage.setEnabled(true);

        tfSendMessage.requestFocus();
    }

    public void startClient(String host, int port) {
        try {
            client = new Socket(host, port);
            flujoE = new DataInputStream(client.getInputStream());
            flujoS = new DataOutputStream(client.getOutputStream());
            AdminThread.threadExecutorPool.execute(() -> {
                String text;
                while (true) {
                    try {
                        text = flujoE.readUTF();
                        taMessage.append(text + "\n");
                    } catch (IOException ex) {
                        System.out.println(ex.getLocalizedMessage());
                        return;
                    }
                }
            });
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        tfUserName = new JTextField();
        spMessage = new JScrollPane();
        taMessage = new JTextArea();
        jlMessagHistory = new JLabel();
        jlUserName = new JLabel();
        tfSendMessage = new JTextField();
        jlSendMessage = new JLabel();

        //======== this ========
        setTitle("Cliente");
        Container contentPane = getContentPane();

        //======== spMessage ========
        {

            //---- taMessage ----
            taMessage.setEditable(false);
            spMessage.setViewportView(taMessage);
        }

        //---- jlMessagHistory ----
        jlMessagHistory.setText("HISTORIAL DE MENSAJES");
        jlMessagHistory.setHorizontalAlignment(SwingConstants.CENTER);

        //---- jlUserName ----
        jlUserName.setText("INGRESE SU NOMBRE DE USUARIO");
        jlUserName.setHorizontalAlignment(SwingConstants.CENTER);

        //---- jlSendMessage ----
        jlSendMessage.setText("Ahora escriba su mensaje y presione ENTER");
        jlSendMessage.setHorizontalAlignment(SwingConstants.CENTER);

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(jlMessagHistory, GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                                        .addComponent(tfUserName, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                                        .addComponent(jlSendMessage, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                                        .addComponent(jlUserName, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                                        .addComponent(spMessage, GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                                        .addComponent(tfSendMessage, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE))
                                .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addComponent(jlUserName, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfUserName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlSendMessage, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfSendMessage, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlMessagHistory, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spMessage, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                                .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
}
