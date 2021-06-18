/*
 * Created by JFormDesigner on Tue Jun 15 19:48:59 CEST 2021
 */

package view.admin;

import view.client.Alert;
import viewmodel.ViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * @author unknown
 */
public class ChatAdmin extends JFrame {
    private static ChatServer chatServer;
    private final ViewModel viewModel = new ViewModel();
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JLabel jlTitleAdmin;
    private JLabel jlHistory;
    private JScrollPane scrollPane1;
    private JTextArea taHistory;
    private JButton btDeleteAll;
    private JButton btCloseConexions;
    private JButton btOpenConnexions;
    private JButton btReload;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public ChatAdmin() {
        initComponents();

        taHistory.registerKeyboardAction(this::reloadConnexionsEvent, KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        btReload.addActionListener(this::reloadConnexionsEvent);
        btDeleteAll.addActionListener(this::onClickBTDeleteAll);
        btCloseConexions.addActionListener(this::onClickCloseConnexions);
        btOpenConnexions.addActionListener(this::onClickOpenConnexions);
    }

    public static void main(String[] args) {
        chatServer = new ChatServer(5000);

        ChatAdmin chatAdmin = new ChatAdmin();
        chatAdmin.setVisible(true);
        chatAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void reloadConnexionsEvent(ActionEvent e) {
        viewModel.getAllMessages();
        taHistory.setText(viewModel.getGlobalMessageList().toString());
    }

    private void onClickBTDeleteAll(ActionEvent e) {
        viewModel.deleteAll();
    }

    private void onClickCloseConnexions(ActionEvent e) {
        String[] options = {"Sí cerrar conexiones", "Cancelar"};
        int opt = JOptionPane.showOptionDialog(this,
                "¿Se guro que quieres cerrar todas las conexiones?",
                "Cerrrar conexiones", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
                options, options[1]);

        if (opt == 0) {
            ChatServer.broadcast("\n\n¡El servidor se ha cerrado, inténtelo de nuevo más tarde!\n\n");

            System.out.println("Conexiones al servidor cerradas");
            chatServer.closeServer();
            btCloseConexions.setEnabled(false);
            btOpenConnexions.setEnabled(true);
        }
    }

    private void onClickOpenConnexions(ActionEvent e) {
        if (!ChatServer.DEAD_CODE) {
            btCloseConexions.setEnabled(true);
            btOpenConnexions.setEnabled(false);
            Alert.show(this, "Las conexiones están abiertas", "Conexiones abiertas");
            return;
        }

        btCloseConexions.setEnabled(true);
        btOpenConnexions.setEnabled(false);

        chatServer = new ChatServer(5000);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        jlTitleAdmin = new JLabel();
        jlHistory = new JLabel();
        scrollPane1 = new JScrollPane();
        taHistory = new JTextArea();
        btDeleteAll = new JButton();
        btCloseConexions = new JButton();
        btOpenConnexions = new JButton();
        btReload = new JButton();

        //======== this ========
        setTitle("Administrador");
        Container contentPane = getContentPane();

        //---- jlTitleAdmin ----
        jlTitleAdmin.setText("Bienvenido ADMINISTRADOR");
        jlTitleAdmin.setHorizontalAlignment(SwingConstants.CENTER);
        jlTitleAdmin.setFont(new Font("Segoe UI", Font.PLAIN, 20));

        //---- jlHistory ----
        jlHistory.setText("Historial de mensajes - Presione el espacio, \u00a1para actualizar!");
        jlHistory.setHorizontalAlignment(SwingConstants.CENTER);

        //======== scrollPane1 ========
        {

            //---- taHistory ----
            taHistory.setEditable(false);
            scrollPane1.setViewportView(taHistory);
        }

        //---- btDeleteAll ----
        btDeleteAll.setText("ELIMINAR MENSAJES -> INCLUIDO BD");

        //---- btCloseConexions ----
        btCloseConexions.setText("CERRAR CONEXIONES");

        //---- btOpenConnexions ----
        btOpenConnexions.setText("ABRIR CONEXIONES");
        btOpenConnexions.setEnabled(false);

        //---- btReload ----
        btReload.setText("Recargar");

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addComponent(scrollPane1, GroupLayout.Alignment.TRAILING)
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addComponent(btDeleteAll)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btCloseConexions, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btOpenConnexions, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jlTitleAdmin, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                                                        .addComponent(jlHistory, GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btReload)))
                                .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addComponent(jlTitleAdmin, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jlHistory, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(btReload, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addComponent(btDeleteAll, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                                        .addComponent(btOpenConnexions, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                                        .addComponent(btCloseConexions, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
                                .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
}
